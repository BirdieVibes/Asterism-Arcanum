package com.birdie.asterismarcanum.entity.spells.tidal_lock;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.ice_spider.ICritablePartEntity;
import io.redspace.ironsspellbooks.entity.spells.root.PreventDismount;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class TidalLockEntity extends Entity implements PreventDismount, AntiMagicSusceptible, ICritablePartEntity {
    private @Nullable Entity cachedOwner;

    private @Nullable UUID ownerUUID;

    private int lifetime = -1;

    public TidalLockEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public TidalLockEntity(Level level, Entity owner) {
        super(ASAREntityRegistry.TIDAL_LOCK.get(), level);
        setOwner(owner);
    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) { destroyTidalLock(); }

    @Override
    public boolean skipAttackInteraction(Entity entity) {
        return isPassengerOfSameVehicle(entity);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) { }

    @Override
    public void push(@NotNull Entity pEntity) { }

    @Override
    public void tick() {
        super.tick();
        this.move(MoverType.SELF, getDeltaMovement());

        if (onGround()) {
            this.setDeltaMovement(getDeltaMovement().scale(0));
        } else {
            this.setDeltaMovement(getDeltaMovement().multiply(0, 0, 0));
        }

        if (lifetime >= 0 && tickCount > lifetime) {
            destroyTidalLock();
        }

        Vec3 position = this.position();

        if (lifetime >= 0 && tickCount < lifetime) {
            if (tickCount % 25 == 0) {
                MagicManager.spawnParticles(level(), new BlastwaveParticleOptions(.4f, .85f, 1f, 3f),
                        position.x, position.y + 0.06, position.z,
                        1, 0, 0, 0, 0, true
                );
            }

            if (tickCount == 1) {
                MagicManager.spawnParticles(level(), new BlastwaveParticleOptions(.4f, .85f, 1f, 3f),
                        position.x, position.y + 0.06, position.z,
                        1, 0, 0, 0, 0, true
                );
            }
        }
    }

    @Override
    public void kill() {
        destroyTidalLock();
    }

    @Override
    public boolean canEntityDismount(Entity entity) {
        return entity.getUUID().equals(this.ownerUUID);
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        refreshDimensions();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        destroyTidalLock();
    }

    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction p_19958_) {
        passenger.setPos(this.getX(), this.getY(), this.getZ());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }
        compound.putInt("age", tickCount);
        compound.putInt("lifetime", lifetime);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
            this.cachedOwner = null;
        }
        this.tickCount = compound.getInt("age");
        this.lifetime = compound.getInt("lifetime");
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        var passengers = getPassengers();
        float hScale = 1f;
        float vScale = 1f;

        if (!passengers.isEmpty() && passengers.getFirst() instanceof LivingEntity livingEntity) {
            hScale = livingEntity.getBbWidth() + .4f;//* 1.66f; // ratio of our default hitbox to the players default hitbox
            vScale = (livingEntity.getBbHeight() + .2f) / 2;//* 0.555f;  // ratio of our default hitbox to the players default hitbox
            vScale = (vScale + hScale) * .5f; // average fixed-scale to desired scale. no change for humanoids, but will stretch for more cuboid entities
        }

        return super.getDimensions(pPose).scale(hScale * .9f, vScale * .9f);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity pEntity) { return false; }

    @Override
    public boolean canBeCollidedWith() { return false; }

    @Override
    public boolean isPickable() { return false; }

    @Override
    public boolean dismountsUnderwater() { return false; }

    @Override
    public boolean shouldRiderSit() { return false; }

    @Override
    public Vec3 getPassengerRidingPosition(Entity pEntity) { return position(); }

    @Override
    public boolean isPushedByFluid(FluidType type) { return false; }

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

    public void setOwner(@Nullable Entity owner) {
        if (owner != null) {
            this.ownerUUID = owner.getUUID();
            this.cachedOwner = owner;
        }
    }

    public void setLifetime(int lifetime) { this.lifetime = lifetime; }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void destroyTidalLock() {
        Level level = level();

        if (level.isClientSide) return;

        this.ejectPassengers();
        this.playSound(SoundEvents.GLASS_BREAK, 2, 1);

        MagicManager.spawnParticles(
                level, ParticleHelper.ELECTRICITY,
                getX(), getY() + 1, getZ(),
                50, 0.2, 0.2, 0.2, 0.2, false
        );

        this.discard();
    }
}
