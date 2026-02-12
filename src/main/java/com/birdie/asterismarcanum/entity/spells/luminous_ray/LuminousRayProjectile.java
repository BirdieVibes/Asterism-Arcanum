package com.birdie.asterismarcanum.entity.spells.luminous_ray;

import com.birdie.asterismarcanum.entity.spells.AbstractBeamProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.particle.EnderSlashParticleOptions;
import io.redspace.ironsspellbooks.particle.TraceParticleOptions;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

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
        float distance = 24f;
        Vec3 end = Utils.raycastForBlock(level(), owner.getEyePosition(), owner.getEyePosition().add(rotation.scale(distance)),
                ClipContext.Fluid.NONE).getLocation();
        Vec3 rayVector = end.subtract(owner.getEyePosition());
        Vec3 impulse = rayVector.scale(1 / 6f).add(0, 0.1, 0);
        owner.setDeltaMovement(owner.getDeltaMovement().scale(0.2).add(impulse));

        rotation = impulse.normalize(); // recalculate forward as the direction we are actually moving
        Vec3 up = new Vec3(0, 1, 0);
        if (rotation.dot(up) > .999) {
            up = new Vec3(1, 0, 0);
        }
        Vec3 right = up.cross(rotation);
        Vec3 particlePos = end.subtract(rotation.scale(3)).add(right.scale(-0.3));
        MagicManager.spawnParticles(level(),
                new EnderSlashParticleOptions(
                        (float) rotation.x,
                        (float) rotation.y,
                        (float) rotation.z,
                        (float) right.x,
                        (float) right.y,
                        (float) right.z,
                        1f),
                particlePos.x, particlePos.y + .3, particlePos.z, 1, 0, 0, 0, 0, true);

        double speed = rayVector.length() / 12.0 * .75;
        for (int i = 0; i < 5; i++) {
            Vec3 particleStart = owner.getBoundingBox().getCenter().add(Utils.getRandomVec3(1 + owner.getBbWidth()));
            Vec3 particleEnd = particleStart.add(rayVector);
            MagicManager.spawnParticles(level(), new TraceParticleOptions(Utils.v3f(particleEnd), new Vector3f(1f, .333f, 1f)),
                    particleStart.x, particleStart.y, particleStart.z, 1, 0, 0, 0, speed, false);
        }
    }

//    @Override
//    public void tick() {
//        super.tick();
//        var owner = getOwner();
//        if (!level().isClientSide || owner == null) {
//            return;
//        }
//        Vec3 rotation = owner.getLookAngle().normalize();
//        var pos = owner.position();
//        float distance = 24f;
//        Vec3 end = Utils.raycastForBlock(level(), owner.getEyePosition(), owner.getEyePosition().add(rotation.scale(distance)),
//                ClipContext.Fluid.NONE).getLocation();
//        Vec3 rayVector = end.subtract(owner.getEyePosition());
//        Vec3 impulse = rayVector.scale(1 / 6f).add(0, 0.1, 0);
//        owner.setDeltaMovement(owner.getDeltaMovement().scale(0.2).add(impulse));
//
//        rotation = impulse.normalize(); // recalculate forward as the direction we are actually moving
//        Vec3 up = new Vec3(0, 1, 0);
//        if (rotation.dot(up) > .999) {
//            up = new Vec3(1, 0, 0);
//        }
//        Vec3 right = up.cross(rotation);
//        Vec3 particlePos = end.subtract(rotation.scale(3)).add(right.scale(-0.3));
//        MagicManager.spawnParticles(level(),
//                new EnderSlashParticleOptions(
//                        (float) rotation.x,
//                        (float) rotation.y,
//                        (float) rotation.z,
//                        (float) right.x,
//                        (float) right.y,
//                        (float) right.z,
//                        1f),
//                particlePos.x, particlePos.y + .3, particlePos.z, 1, 0, 0, 0, 0, true);
//
//        double speed = rayVector.length() / 12.0 * .75;
//        for (int i = 0; i < 5; i++) {
//            Vec3 particleStart = owner.getBoundingBox().getCenter().add(Utils.getRandomVec3(1 + owner.getBbWidth()));
//            Vec3 particleEnd = particleStart.add(rayVector);
//            MagicManager.spawnParticles(level(), new TraceParticleOptions(Utils.v3f(particleEnd), new Vector3f(1f, .333f, 1f)),
//                    particleStart.x, particleStart.y, particleStart.z, 1, 0, 0, 0, speed, false);
//        }
//    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        DamageSources.applyDamage(entity, damage, SpellRegistries.LUMINOUS_RAY.get().getDamageSource(this, getOwner()));
    }
}
