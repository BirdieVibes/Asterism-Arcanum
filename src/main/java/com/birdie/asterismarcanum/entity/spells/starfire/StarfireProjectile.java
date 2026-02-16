package com.birdie.asterismarcanum.entity.spells.starfire;

import com.birdie.asterismarcanum.entity.spells.SpellUtils;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class StarfireProjectile extends AbstractMagicProjectile {
    private int bounces;

    public StarfireProjectile(Level levelIn, LivingEntity shooter) {
        this(ASAREntityRegistry.STARFIRE_PROJECTILE.get(), levelIn, shooter);
    }

    public StarfireProjectile(EntityType<? extends StarfireProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        this.setPierceLevel(2);
        this.setCanRicochet(true);
    }

    public void doRicochet(HitResult hitResult) {
        if (hitResult instanceof EntityHitResult entityHitResult) {
            Vec3 deltaMovement = getDeltaMovement();
            Vec3 vec = deltaMovement.normalize();
            Entity owner = getOwner();
            Entity hit = entityHitResult.getEntity();
            List<Entity> potentialTargets = level().getEntities(this, this.getBoundingBox().inflate(3).expandTowards(deltaMovement.scale(12)),
                    entity -> entity != hit && (
                            (owner == null || !Utils.shouldHealEntity(owner, entity))
                                    || entity.getClass() == hit.getClass()
                    ) && entity.getBoundingBox().getCenter().subtract(position()).normalize().dot(vec) > 0.6 && Utils.hasLineOfSight(level(),
                            this, entity, false));
            if (potentialTargets.isEmpty()) {
                return;
            }
            Entity target = potentialTargets.get(this.getId() % potentialTargets.size()); // use deterministic random to keep client and server in sync
            setDeltaMovement(target.getBoundingBox().getCenter().subtract(this.position()).normalize().scale(deltaMovement.length()));
        }
    }

    public StarfireProjectile(EntityType<? extends StarfireProjectile> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        setOwner(shooter);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(
                level(), ASARParticleRegistry.STARDUST_PARTICLE.get(),
                x, y, z, 25, 0, 0, 0, .18, true
        );
        MagicManager.spawnParticles(this.level(), ASARParticleRegistry.STARDUST_PARTICLE.get(), x, y, z, 25, 0, 0, 0, .18, true);

    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        switch (pResult.getDirection()) {
            case UP, DOWN ->
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, this.isNoGravity() ? -1 : -2.5f, 1));
            case EAST, WEST -> this.setDeltaMovement(this.getDeltaMovement().multiply(-1, 1, 1));
            case NORTH, SOUTH -> this.setDeltaMovement(this.getDeltaMovement().multiply(1, 1, -1));
        }
        if (++bounces >= 0) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        DamageSources.applyDamage(
                entityHitResult.getEntity(), getDamage(),
                SpellRegistries.STARFIRE.get().getDamageSource(this, getOwner())
        );

        pierceOrDiscard();
        if (canRicochet()) {
            doRicochet(entityHitResult);
        }
    }

    @Override
    public void trailParticles() {
        var vec = getDeltaMovement();
        var length = vec.length();

        int count = (int) Math.min(2, Math.round(length) * 3) + 1;
        float f = (float) length / count;

        Level level = level();
        Vec3 position = position();

        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(0.02 * i);
            Vec3 p = vec.scale(f * i);

            SpellUtils.addParticle(level, ASARParticleRegistry.STARS_PARTICLE.get(), position, p, random);
            SpellUtils.addParticle(level, ParticleTypes.ELECTRIC_SPARK, position, p, random);
        }
    }

    @Override
    public float getSpeed() {
        return 2.5f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.empty();
    }
}
