package com.birdie.asterismarcanum.entity.spells.dark_flow;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class DarkFlow extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS =
            SynchedEntityData.defineId(DarkFlow.class, EntityDataSerializers.FLOAT);

    public static final int WARMUP_TIME = 2;

    private static final int LOOP_SOUND_DURATION_IN_TICKS = 40;

    private float damage;
    private int duration = 20 ;

    private List<Entity> trackingEntities = List.of();

    public DarkFlow(EntityType<? extends Projectile> pEntityType, Level pLevel) { super(pEntityType, pLevel); }

    public DarkFlow(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.DARK_FLOW.get(), pLevel);
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

        float radius = this.getRadius();

        int update = Math.max((int) (this.getRadius() / 2), 2);

        //prevent lag from giagantic black holes
        if (tickCount % update == 0) updateTrackingEntities();

        Level level = level();
        AABB boundingBox = this.getBoundingBox();

        float boundingBoxRadiusX = (float) (boundingBox.getXsize());

        BlockPos blockPos = this.blockPosition();
        Vec3 position = this.position();
        Vec3 center = boundingBox.getCenter();

        // Unused variable?
        // boolean hitTick = this.tickCount % 10 == 0;

        for (Entity entity : trackingEntities) {
            if (entity != getOwner() && !DamageSources.isFriendlyFireBetween(getOwner(), entity) && !entity.isSpectator()) {
                float distance = (float) center.distanceTo(position);

                if (distance > boundingBoxRadiusX) {
                    continue;
                }

                float f = 1 - distance / boundingBoxRadiusX;
                float scale = f * f * f * f * .25f;
                float resistance = entity instanceof LivingEntity livingEntity
                        ? Mth.clamp(1 - (float) livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), .3f, 1f)
                        : 1f;

                float bossResistance = entity.getType().is(Tags.EntityTypes.BOSSES) ? 0.5f : 1f;

                Vec3 diff = center.subtract(position).scale(scale * resistance * bossResistance);
                entity.push(-diff.x, -diff.y, -diff.z);
                entity.fallDistance = 0;
            }
        }

        if (level.isClientSide) return;

        if (tickCount == WARMUP_TIME) {
            MagicManager.spawnParticles(level, new BlastwaveParticleOptions(.1f, .1f, 0.1f, 10f),
                    position.x, position.y + (boundingBoxRadiusX / 2) + 0.06, position.z,
                    1, 0, 0, 0, 0, true
            );

            MagicManager.spawnParticles(level, ASARParticleRegistry.STARS_PARTICLE.get(),
                    position.x, position.y + radius, position.z,
                    200, 1, 1, 1, 1, true
            );

            MagicManager.spawnParticles(level, ParticleTypes.EXPLOSION, position.x,
                    position.y + radius, position.z,
                    200, 1, 1, 1, 1, true);

            level.playSound(null, blockPos, SoundEvents.DRAGON_FIREBALL_EXPLODE,
                    SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f);
        }

        if (tickCount > duration) this.discard();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, this.getRadius() * 2.0F);
    }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public float getDamage() { return damage; }
    public void setDamage(float damage) { this.damage = damage; }

    public float getRadius() { return this.getEntityData().get(DATA_RADIUS); }

    public void setRadius(float pRadius) {
        if (level().isClientSide) return;

        this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 48));
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_RADIUS, 5F);
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

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    private void updateTrackingEntities() {
        trackingEntities = level().getEntities(this, this.getBoundingBox().inflate(1));
    }
}
