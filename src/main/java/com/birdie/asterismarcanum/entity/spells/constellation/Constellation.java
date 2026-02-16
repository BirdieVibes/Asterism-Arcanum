package com.birdie.asterismarcanum.entity.spells.constellation;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Constellation extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS =
            SynchedEntityData.defineId(Constellation.class, EntityDataSerializers.FLOAT);

    public static final int WARMUP_TIME = 2;

    private float damage = 0;
    private int duration = 100;

    public Constellation(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Constellation(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.CONSTELLATION.get(), pLevel);
        setOwner(owner);
    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) { }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_RADIUS.equals(pKey)) {
            this.refreshDimensions();
            if (getRadius() < .1f)
                this.discard();
        }

        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level();

        if (level.isClientSide) return;

        AABB boundingBox = this.getBoundingBox();
        float blastWaveRadius = (float) (boundingBox.getXsize());
        float totalRadius = this.getRadius();


        Vec3 position = this.position();
        BlockPos blockPos = this.blockPosition();

        if (tickCount == WARMUP_TIME) {
            MagicManager.spawnParticles(level,
                    new BlastwaveParticleOptions(.3f, .3f, 0.8f, 10f),
                    position.x, position.y + (blastWaveRadius / 2) + 0.06, position.z,
                    1, 0, 0, 0, 0, true
            );

            level.playSound(null, blockPos, SoundEvents.DRAGON_FIREBALL_EXPLODE,
                    SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f);
        }

        if (tickCount > duration) {
            MagicManager.spawnParticles(level,
                    new BlastwaveParticleOptions(.3f, .3f, 0.8f, 10f),
                    position.x, position.y + (blastWaveRadius / 2) + 0.06, position.z,
                    1, 0, 0, 0, 0, true
            );

            MagicManager.spawnParticles(level,
                    ASARParticleRegistry.STARS_PARTICLE.get(),
                    position.x, position.y + totalRadius, position.z,
                    100, 1, 1, 1, 0.5, true
            );

            MagicManager.spawnParticles(level, ASARParticleRegistry.STARDUST_PARTICLE.get(),
                    position.x, position.y + totalRadius, position.z,
                    100, 1, 1, 1, 0.5, true);

            level.playSound(null, blockPos, SoundEvents.DRAGON_FIREBALL_EXPLODE,
                    SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f);

            float explosionRadius = totalRadius * 5;
            var explosionRadiusSqr = explosionRadius * explosionRadius;
            var entities = level.getEntities(this, boundingBox.inflate(explosionRadius));
            Vec3 losPoint = Utils.raycastForBlock(level, position, position.add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();

            for (Entity entity : entities) {
                double distanceSqr = entity.distanceToSqr(boundingBox.getCenter());

                if (distanceSqr < explosionRadiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(level, losPoint, entity.getBoundingBox().getCenter(), true)) {
                    double p = (1 - distanceSqr / explosionRadiusSqr);
                    float damage = (float) (this.damage * p * 12);
                    DamageSources.applyDamage(entity, damage, SpellRegistries.CONSTELLATION.get().getDamageSource(this, getOwner()));
                }
            }

            this.discard();
        }
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        float radius = getRadius();

        return EntityDimensions.scalable(radius * 0.9F, radius * 0.9F);
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();

        super.refreshDimensions();

        this.setPos(d0, d1, d2);
    }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public float getDamage() { return damage; }

    public void setDamage(float damage) { this.damage = damage; }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_RADIUS, 5F);
    }

    public float getRadius() { return this.getEntityData().get(DATA_RADIUS); }

    public void setRadius(float pRadius) {
        if (level().isClientSide) return;

        this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 4));
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putFloat("Radius", this.getRadius());
        pCompound.putInt("Age", this.tickCount);
        pCompound.putInt("Duration", this.duration);

        super.addAdditionalSaveData(pCompound);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.tickCount = pCompound.getInt("Age");
        this.duration = pCompound.getInt("Duration");

        if (pCompound.getInt("Radius") > 0)
            this.setRadius(pCompound.getFloat("Radius"));

        super.readAdditionalSaveData(pCompound);
    }
}
