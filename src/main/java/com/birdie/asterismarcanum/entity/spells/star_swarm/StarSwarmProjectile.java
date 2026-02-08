package com.birdie.asterismarcanum.entity.spells.star_swarm;

import com.birdie.asterismarcanum.entity.spells.SpellUtils;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

import static java.math.RoundingMode.DOWN;
import static java.math.RoundingMode.UP;
import static javax.swing.SwingConstants.*;

public class StarSwarmProjectile extends AbstractMagicProjectile implements AntiMagicSusceptible {
    public StarSwarmProjectile(Level levelIn, LivingEntity shooter) {
        this(ASAREntityRegistry.STAR_SWARM_PROJECTILE.get(), levelIn, shooter);
    }

    public StarSwarmProjectile(EntityType<? extends StarSwarmProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        this.setInfinitePiercing();
    }

    public StarSwarmProjectile(EntityType<? extends StarSwarmProjectile> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        setOwner(shooter);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        switch (pResult.getDirection()) {
            case UP, DOWN ->
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, this.isNoGravity() ? -1 : -.8f, 1));
            case EAST, WEST -> this.setDeltaMovement(this.getDeltaMovement().multiply(-1, 1, 1));
            case NORTH, SOUTH -> this.setDeltaMovement(this.getDeltaMovement().multiply(1, 1, -1));
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        DamageSources.applyDamage(
                entityHitResult.getEntity(), getDamage(),
                SpellRegistries.STAR_SWARM.get().getDamageSource(this, getOwner())
        );

        pierceOrDiscard();
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(
                level(), ParticleHelper.WISP,
                x, y, z, 25, 0, 0, 0, .18, true
        );
    }

    @Override
    public void trailParticles() {
        var vec = getDeltaMovement();
        var length = vec.length();
        int count = (int) Math.min(20, Math.round(length) * 0.1) + 1;

        Level level = level();
        Vec3 position = position();

        float f = (float) length / count;

        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(0.2);
            Vec3 p = vec.scale(f * i);

            SpellUtils.addParticle(level, ASARParticleRegistry.STARDUST_PARTICLE.get(), position, p, random);
            SpellUtils.addParticle(level, ASARParticleRegistry.STARS_PARTICLE.get(), position, p, random);
            SpellUtils.addParticle(level, ASARParticleRegistry.STARS_PARTICLE.get(), position, p, random);
            SpellUtils.addParticle(level, ParticleTypes.ELECTRIC_SPARK, position, p, random);
            SpellUtils.addParticle(level, ParticleTypes.END_ROD, position, p, random);
            SpellUtils.addParticle(level, ParticleTypes.GLOW, position, p, random);
            SpellUtils.addParticle(level, ParticleTypes.END_ROD, position, p, random);
            SpellUtils.addParticle(level, ParticleTypes.END_ROD, position, p, random);
        }
    }

    @Override
    public float getSpeed() {
        return 0.8f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.empty();
    }
}
