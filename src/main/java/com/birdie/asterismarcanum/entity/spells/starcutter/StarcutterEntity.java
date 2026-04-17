package com.birdie.asterismarcanum.entity.spells.starcutter;

import com.birdie.asterismarcanum.particle.StarCutParticleOptions;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.ASARSoundsRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.particle.FlameStrikeParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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

public class StarcutterEntity extends Projectile implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Float> DATA_RADIUS =
            SynchedEntityData.defineId(StarcutterEntity.class, EntityDataSerializers.FLOAT);

    public static final int WARMUP_TIME = 2;

    private float damage = 1;
    private int duration = 30;

    public StarcutterEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public StarcutterEntity(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.STARCUTTER_PROJECTILE.get(), pLevel);
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
                    ParticleTypes.FLASH, position.x, position.y + this.getBbHeight()/2, position.z, 1, 0, 0, 0, 0, false
            );

            level.playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_CHIME,
                    SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(2, 5) * .3f);
        }

        if (tickCount > duration) {
            MagicManager.spawnParticles(level,
                    ParticleTypes.FLASH, position.x, position.y + this.getBbHeight()/2, position.z, 1, 0, 0, 0, 0, false
            );

            for (int i = 0; i < 7; i++) {
                float xOff = (float) getRandomX(360) * getRandom().nextIntBetweenInclusive(-1, 1);
                float zOff = (float) getRandomZ(360) * getRandom().nextIntBetweenInclusive(-1, 1);
                float yOffer = (float) ((xOff - zOff) * (zOff + xOff));
                float yOff = (float) (yOffer * getRandom().nextIntBetweenInclusive(-1, 1));

                boolean mirror = random.nextBoolean();
                MagicManager.spawnParticles(level, new StarCutParticleOptions(
                                (float) position.x * xOff, (float) position.y * yOff, (float) position.z * zOff, mirror, false, 1f),
                        position.x, position.y + totalRadius, position.z,
                        1, 0, 0, 0, 0, true
                );
            }

            level.playSound(null, blockPos, ASARSoundsRegistry.ASTRAL_SHATTER_2.get(),
                    SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(2, 5) * .3f);

            float explosionRadius = totalRadius * 10;
            var explosionRadiusSqr = explosionRadius * explosionRadius;
            var entities = level.getEntities(this, boundingBox.inflate(explosionRadius));
            Vec3 losPoint = Utils.raycastForBlock(level, position, position.add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();

            for (Entity entity : entities) {
                double distanceSqr = entity.distanceToSqr(boundingBox.getCenter());

                if (distanceSqr < explosionRadiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(level, losPoint, entity.getBoundingBox().getCenter(), true)) {
                    double p = (1 - distanceSqr / explosionRadiusSqr);
                    float damage = (float) (this.damage * p);
                    DamageSources.applyDamage(entity, damage, SpellRegistries.STARCUTTER.get().getDamageSource(this, getOwner()));
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

        return EntityDimensions.scalable(radius * .8F, radius * .8F);
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();

        super.refreshDimensions();

        this.setPos(d0, d1, d2);
    }

    public float getDamage() { return damage; }

    public void setDamage(float damage) { this.damage = damage; }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_RADIUS, 1F);
    }

    public float getRadius() { return this.getEntityData().get(DATA_RADIUS); }

    public void setRadius(float pRadius) {
        if (level().isClientSide) return;

        this.getEntityData().set(DATA_RADIUS, Math.min(pRadius, 1));
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