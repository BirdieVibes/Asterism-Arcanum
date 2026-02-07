package com.birdie.asterismarcanum.entity.spells.constellation;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class Constellation extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(Constellation.class, EntityDataSerializers.FLOAT);

    public Constellation(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Constellation(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.CONSTELLATION.get(), pLevel);
        setOwner(owner);
    }

    List<Entity> trackingEntities = new ArrayList<>();

    @Override
    public void onAntiMagic(MagicData playerMagicData) {
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }


    private float damage;
    private int duration = 100 ;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(this.getRadius() * 0.9F, this.getRadius() * 0.9F);
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_RADIUS, 5F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_RADIUS.equals(pKey)) {
            this.refreshDimensions();
            if (getRadius() < .1f)
                this.discard();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public void setRadius(float pRadius) {
        if (!this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 4));
        }
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
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

    public static final int WARMUP_TIME = 2;

    @Override
    public void tick() {
        super.tick();
        int update = Math.max((int) (getRadius() / 2), 2);
        //prevent lag from giagantic black holes
        if (tickCount % update == 0) {
            updateTrackingEntities();
        }
        var bb = this.getBoundingBox();
        float radius = (float) (bb.getXsize());
        boolean hitTick = this.tickCount % 10 == 0;
        Vec3 center = bb.getCenter();

        if (tickCount == WARMUP_TIME) {
            if (!level().isClientSide) {
                MagicManager.spawnParticles(level(), new BlastwaveParticleOptions(.3f, .3f, 0.8f,
                                10f), getX(), getY() + (radius / 2) + 0.06, getZ(),
                        1, 0, 0, 0, 0, true);
                level().playSound(null, this.blockPosition(), SoundEvents.DRAGON_FIREBALL_EXPLODE,
                        SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f);
            }
        }

        if (!level().isClientSide) {
            if (tickCount > duration) {
                MagicManager.spawnParticles(level(), new BlastwaveParticleOptions(.3f, .3f, 0.8f,
                                10f), getX(), getY() + (radius / 2) + 0.06, getZ(),
                        1, 0, 0, 0, 0, true);
                MagicManager.spawnParticles(level(), ASARParticleRegistry.STARS_PARTICLE.get(), getX(),
                        getY() + getRadius(), getZ(), 100, 1, 1, 1, 0.5, true);
                MagicManager.spawnParticles(level(), ASARParticleRegistry.STARDUST_PARTICLE.get(), getX(),
                        getY() + getRadius(), getZ(), 100, 1, 1, 1, 0.5, true);
                level().playSound(null, this.blockPosition(), SoundEvents.DRAGON_FIREBALL_EXPLODE,
                        SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f);
                float explosionRadius = getRadius() * 5;
                var explosionRadiusSqr = explosionRadius * explosionRadius;
                var entities = level().getEntities(this, this.getBoundingBox().inflate(explosionRadius));
                Vec3 losPoint = Utils.raycastForBlock(level(), this.position(), this.position().add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();
                for (Entity entity : entities) {
                    double distanceSqr = entity.distanceToSqr(bb.getCenter());
                    if (distanceSqr < explosionRadiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(level(), losPoint, entity.getBoundingBox().getCenter(), true)) {
                        double p = (1 - distanceSqr / explosionRadiusSqr);
                        float damage = (float) (this.damage * p * 12);
                        DamageSources.applyDamage(entity, damage, SpellRegistries.CONSTELLATION.get().getDamageSource(this, getOwner()));
                    }
                }
                this.discard();
            }
        }
    }

    private void updateTrackingEntities() {
        trackingEntities = level().getEntities(this, this.getBoundingBox().inflate(1));
    }

    private static final int loopSoundDurationInTicks = 40;

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}
