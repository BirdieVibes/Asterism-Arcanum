package com.birdie.asterismarcanum.entity.spells.luminous_ray;

import com.birdie.asterismarcanum.entity.spells.AbstractBeamProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class LuminousRayProjectile extends AbstractBeamProjectile {
    public LuminousRayProjectile(EntityType<? extends AbstractBeamProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LuminousRayProjectile(Level level, LivingEntity entity) {
        super(ASAREntityRegistry.LUMINOUS_RAY_PROJECTILE.get(), level, entity);
    }


    @Override
    public void spawnParticles() {
        var owner = getOwner();
        if (!level().isClientSide || owner == null) {
            return;
        }
        Vec3 rotation = owner.getLookAngle().normalize();
        var pos = owner.position();

        double x = pos.x;
        double y = pos.y + owner.getEyeHeight() * .9f;
        double z = pos.z;

        double speed = random.nextDouble() * .6 + .6;
        for (int i = 0; i < 1; i++) {
            double offset = .8;
            double ox = Math.random() * 2 * offset - offset;
            double oy = Math.random() * 2 * offset - offset;
            double oz = Math.random() * 2 * offset - offset;

            double angularness = 0;
            Vec3 randomVec = new Vec3(Math.random() * 2 * angularness - angularness, Math.random() * 2 * angularness - angularness, Math.random() * 2 * angularness - angularness).normalize();
            Vec3 result = (rotation.scale(3).add(randomVec)).normalize().scale(speed);
            level().addParticle(ASARParticleRegistry.ALTSIGNS_PARTICLE.get(), x + ox/3, y + oy/3, z + oz/3, result.x, result.y, result.z);
            level().addParticle(ASARParticleRegistry.STARS_PARTICLE.get(), x + ox/3, y + oy/3, z + oz/3, result.x, result.y, result.z);
            level().addParticle(ASARParticleRegistry.SIGNS_PARTICLE.get(), x + ox/3, y + oy/3, z + oz/3, result.x, result.y, result.z);
            level().addParticle(ASARParticleRegistry.STARDUST_PARTICLE.get(), x + ox/3, y + oy/3, z + oz/3, result.x, result.y, result.z);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        DamageSources.applyDamage(entity, damage, SpellRegistries.LUMINOUS_RAY.get().getDamageSource(this, getOwner()));
    }
}
