package com.birdie.asterismarcanum.entity.spells.celestial_tether;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.ice_spider.ICritablePartEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

//I've based a lot of this off of the ice tomb entity
public class CelestialTetherEntity extends Entity implements AntiMagicSusceptible, ICritablePartEntity {
    @Nullable
    private Entity cachedOwner;
    @Nullable
    private UUID ownerUUID;

    private int lifetime = -1;

    private float absorbedHitsRemaining;

    public CelestialTetherEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public CelestialTetherEntity(Level level, Entity owner) {
        super(ASAREntityRegistry.CELESTIAL_TETHER_ENTITY.get(), level);
        setOwner(owner);
    }

    @Override
    public boolean skipAttackInteraction(Entity entity) {
        return isPassengerOfSameVehicle(entity);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public void setOwner(@Nullable Entity owner) {
        if (owner != null) {
            this.ownerUUID = owner.getUUID();
            this.cachedOwner = owner;
        }
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public void setAbsorbedHitsRemaining(int absorbedHitsRemaining) {
        this.absorbedHitsRemaining = absorbedHitsRemaining;
    }

    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level() instanceof ServerLevel serverlevel) {
            this.cachedOwner = serverlevel.getEntity(this.ownerUUID);
            return this.cachedOwner;
        } else {
            return null;
        }
    }

    public void subtractAbsorbedHits() {
        absorbedHitsRemaining--;
    }

    @Override
    public void tick() {
        super.tick();
        if (lifetime >= 0 && tickCount > lifetime) {
            destroyTether();
        }

        if (absorbedHitsRemaining < 0) {
            destroyTether();
        }

        if (tickCount % 15 == 0 && (cachedOwner != null)) {
            for (int i = 0; i < (3*absorbedHitsRemaining); i++) {
                MagicManager.spawnParticles(cachedOwner.level(), ParticleHelper.CLEANSE_PARTICLE,
                        this.getX() + Mth.randomBetween(random, -1, 1),
                        this.getY() + Mth.randomBetween(random, -1, 1),
                        this.getZ() + Mth.randomBetween(random, -1, 1),
                        (int) 1, 0, 0, 0, 0, false);
            }

            MagicManager.spawnParticles(cachedOwner.level(), ParticleTypes.FLASH,
                    this.getX(),
                    this.getY() + .5,
                    this.getZ(),
                    1, 0, 0, 0, 0, false);
        }
    }

    @Override
    public void kill() {
        destroyTether();
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        refreshDimensions();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        destroyTether();
    }

    public void destroyTether() {
        if (!level().isClientSide) {
            this.ejectPassengers();
            this.playSound(SoundEvents.GLASS_BREAK, 2, 1);
            this.discard();
        }
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity pEntity) {
        return this.position();
    }

    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction p_19958_) {
        passenger.setPos(this.getX(), this.getY(), this.getZ());
    }


    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }
        compound.putInt("age", tickCount);
        compound.putInt("lifetime", lifetime);
        compound.putFloat("absorbed_hits_remaining", absorbedHitsRemaining);
    }

    @Override
    public boolean dismountsUnderwater() {
        return false;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
            this.cachedOwner = null;
        }
        this.tickCount = compound.getInt("age");
        this.lifetime = compound.getInt("lifetime");
        this.absorbedHitsRemaining = compound.getFloat("absorbed_hits_remaining");
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        var passengers = getPassengers();
        float hScale = 1f;
        float vScale = 1f;
        if (!passengers.isEmpty() && passengers.getFirst() instanceof LivingEntity livingEntity) {
            hScale = livingEntity.getBbWidth() + .4f;//* 1.66f; // ratio of our default hitbox to the players default hitbox
            vScale = livingEntity.getBbHeight() + .2f;//* 0.555f;  // ratio of our default hitbox to the players default hitbox
            vScale = (vScale + hScale)/2; // average fixed-scale to desired scale. no change for humanoids, but will stretch for more cuboid entities
        }
        return super.getDimensions(pPose).scale(hScale, vScale);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity pEntity) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public void push(@NotNull Entity pEntity) {

    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) {
        destroyTether();
    }
}
