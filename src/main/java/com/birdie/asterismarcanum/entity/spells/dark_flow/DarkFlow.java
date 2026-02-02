package com.birdie.asterismarcanum.entity.spells.dark_flow;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class DarkFlow extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(DarkFlow.class, EntityDataSerializers.FLOAT);

    public DarkFlow(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DarkFlow(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.DARK_FLOW.get(), pLevel);
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
    private int duration = 20 ;

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
        return EntityDimensions.scalable(this.getRadius() * 2.0F, this.getRadius() * 2.0F);
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
            this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 48));
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
        for (Entity entity : trackingEntities) {
            if (entity != getOwner() && !DamageSources.isFriendlyFireBetween(getOwner(), entity) && !entity.isSpectator()) {
                float distance = (float) center.distanceTo(entity.position());
                if (distance > radius) {
                    continue;
                }
                float f = 1 - distance / radius;
                float scale = f * f * f * f * .25f;
                float resistance = entity instanceof LivingEntity livingEntity ? Mth.clamp(1 - (float) livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), .3f, 1f) : 1f;
                float bossResistance = entity.getType().is(Tags.EntityTypes.BOSSES) ? 0.5f : 1f;


                Vec3 diff = center.subtract(entity.position()).scale(scale * resistance * bossResistance);
                entity.push(-diff.x, -diff.y, -diff.z);
                entity.fallDistance = 0;
            }
        }

        if (!level().isClientSide) {
            if (tickCount > duration) {
                this.discard();
                this.playSound(SoundRegistry.BLACK_HOLE_CAST.get(), getRadius() / 2f, 1);
                MagicManager.spawnParticles(level(), ParticleHelper.UNSTABLE_ENDER, getX(), getY() + getRadius(), getZ(), 200, 1, 1, 1, 1, true);
                for (Entity entity : trackingEntities) {
                    if (entity.distanceToSqr(center) < 9) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(entity.position().subtract(center).normalize().scale(0.5f)));
                        entity.hurtMarked = true;
                    }
                }
            } else if ((tickCount - 1) % loopSoundDurationInTicks == 0 && (duration < loopSoundDurationInTicks || tickCount + loopSoundDurationInTicks < duration)) {
                this.playSound(SoundRegistry.BLACK_HOLE_LOOP.get(), getRadius() / 3f, .9f + Utils.random.nextFloat() * .2f);
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
