package com.birdie.asterismarcanum.entity.spells.star_swarm.star_swarm_projectile;

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

public class StarSwarmProjectile extends AbstractMagicProjectile {

    public StarSwarmProjectile(Level levelIn, LivingEntity shooter) {
        this(ASAREntityRegistry.STAR_SWARM_PROJECTILE.get(), levelIn, shooter);
    }

    public StarSwarmProjectile(EntityType<? extends StarSwarmProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public StarSwarmProjectile(EntityType<? extends StarSwarmProjectile> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        setOwner(shooter);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(
                level(), ASARParticleRegistry.STARDUST_PARTICLE.get(),
                x, y, z, 25, 0, 0, 0, .18, true
        );
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        pierceOrDiscard();
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

            SpellUtils.addParticle(level, ASARParticleRegistry.STARDUST_PARTICLE.get(), position, p, random);
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
