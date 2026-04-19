package com.birdie.asterismarcanum.entity.spells.trailblaze;

import com.birdie.asterismarcanum.entity.spells.piercing_light.PiercingLightProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static java.nio.file.Files.setOwner;

public class TrailblazeField extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(TrailblazeField.class, EntityDataSerializers.FLOAT);

    public TrailblazeField(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public TrailblazeField(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.TRAILBLAZE_FIELD.get(), pLevel);
        setOwner(owner);
    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) {
        this.discard();
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }


    private float damage;
    private int duration = 20 * 15 * 2;

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 1.0F);
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_RADIUS, 5F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_RADIUS.equals(pKey)) {
            this.refreshDimensions();
            if (getRadius() < 1f)
                this.discard();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public void setRadius(float pRadius) {
        if (!this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 48));
        }
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putFloat("Radius", this.getRadius());
        pCompound.putInt("Age", this.tickCount);
        pCompound.putFloat("Damage", this.getDamage());
        pCompound.putInt("Duration", this.duration);

        super.addAdditionalSaveData(pCompound);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.tickCount = pCompound.getInt("Age");
        this.damage = pCompound.getFloat("Damage");
        this.duration = pCompound.getInt("Duration");
        if (damage == 0)
            damage = 1;
        if (pCompound.getInt("Radius") > 0)
            this.setRadius(pCompound.getFloat("Radius"));

        super.readAdditionalSaveData(pCompound);
    }

    public void summonTrailblaze(Vec3 pos) {
        float radius = (float) (this.getBoundingBox().getXsize()/2);
        Vec3 origin = new Vec3(
                pos.x + Mth.randomBetween(random,-radius, radius),
                pos.y + 1,
                pos.z + Mth.randomBetween(random, -radius, radius));
        TrailblazeEntity trailblaze = new TrailblazeEntity(this.level());

        trailblaze.setPos(origin);
        trailblaze.setDamage(damage);
        trailblaze.setOwner(this.getOwner());
        this.level().addFreshEntity(trailblaze);
    }

    @Override
    public void tick() {
        super.tick();

        var bb = this.getBoundingBox();
        float radius = (float) (bb.getXsize()/2);
        Vec3 center = bb.getCenter();
        if (this.tickCount % 43 == 0 || this.tickCount % 21 == 0) {
            for (int i = 0; i < radius; i++) {
                summonTrailblaze(center);
            }
        }

        if (!level().isClientSide) {
            if (tickCount > duration) {
                this.discard();
                this.playSound(SoundRegistry.BLACK_HOLE_CAST.get(), getRadius() / 2f, 1);
            } else if ((tickCount - 1) % loopSoundDurationInTicks == 0 && (duration < loopSoundDurationInTicks || tickCount + loopSoundDurationInTicks < duration)) {
                this.playSound(SoundRegistry.BLACK_HOLE_LOOP.get(), getRadius() / 3f, .9f + Utils.random.nextFloat() * .2f);
            }
        }
    }

    private static final int loopSoundDurationInTicks = 40;

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}
