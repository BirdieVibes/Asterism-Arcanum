package com.birdie.asterismarcanum.entity.spells.luminous_ray;

import com.birdie.asterismarcanum.entity.spells.AbstractBeamProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class LuminousFlareProjectile extends AbstractBeamProjectile {
    public LuminousFlareProjectile(EntityType<? extends AbstractBeamProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LuminousFlareProjectile(Level level, LivingEntity entity) {
        super(ASAREntityRegistry.LUMINOUS_FLARE_PROJECTILE.get(), level, entity);
    }


    @Override
    public void spawnParticles() {
        var owner = getOwner();
        if (!level().isClientSide || owner == null) {
            return;
        }
        Vec3 rotation = owner.getLookAngle().normalize();
        var pos = owner.position().add(rotation.scale(1.5));


//        double x = pos.x;
//        double y = pos.y + owner.getEyeHeight() * .5f;
//        double z = pos.z;

/// can be optionally turned on by hand but is off by default
//        for (int i = 0; i < 5; i++) {
//            double offset = .2;
//            double ox = Math.random() * 2 * offset - offset;
//            double oy = Math.random() * 2 * offset - offset;
//            double oz = Math.random() * 2 * offset - offset;
//            double speed = random.nextDouble() * 1 + 1;
//
//            double angularness = 0;
//            Vec3 randomVec = new Vec3(Math.random() * 2 * angularness - angularness, Math.random() * 2 * angularness - angularness,
//                    Math.random() * 2 * angularness - angularness).normalize();
//            Vec3 result = (rotation.scale(1.5).add(randomVec)).normalize().scale(speed);
//            level().addParticle(Math.random()> .15 ? ASARParticleRegistry.ALTSIGNS_PARTICLE.get() : ASARParticleRegistry.SIGNS_PARTICLE.get(), x + ox, y + oy, z + oz, result.x, result.y, result.z);
//        }
//
//        for (int i = 0; i < 10; i++) {
//            double secOffset = .4;
//            double ox = Math.random() * 2 * secOffset - secOffset;
//            double oy = Math.random() * 2 * secOffset - secOffset;
//            double oz = Math.random() * 2 * secOffset - secOffset;
//            double speed = random.nextDouble() * 1 + 1;
//
//            double angularness = 0;
//            Vec3 randomVec = new Vec3(Math.random() * 2 * angularness - angularness, Math.random() * 2 * angularness - angularness,
//                    Math.random() * 2 * angularness - angularness).normalize();
//            Vec3 result = (rotation.scale(1.5).add(randomVec)).normalize().scale(speed);
//            level().addParticle(Math.random()> .15 ? ASARParticleRegistry.STARDUST_PARTICLE.get() : ASARParticleRegistry.STARS_PARTICLE.get(), x + ox, y + oy, z + oz, result.x, result.y, result.z);
//        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        DamageSources.applyDamage(entity, damage, SpellRegistries.LUMINOUS_FLARE.get().getDamageSource(this, getOwner()));
    }
}
