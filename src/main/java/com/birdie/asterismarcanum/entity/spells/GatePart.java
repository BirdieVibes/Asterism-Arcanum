package com.birdie.asterismarcanum.entity.spells;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.neoforged.neoforge.entity.PartEntity;

// this is based on the pieces of cone entities from ISS
public class GatePart extends PartEntity<AbstractGateProjectile> {

    public final AbstractGateProjectile parentEntity;
    public final String name;
    private final EntityDimensions size;

    public GatePart(AbstractGateProjectile gateProjectile, String name, float scaleX, float scaleY) {
        super(gateProjectile);
        this.size = EntityDimensions.scalable(scaleX, scaleY);
        this.refreshDimensions();
        this.parentEntity = gateProjectile;
        this.name = name;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public boolean is(Entity entity) {
        return this == entity || this.parentEntity == entity;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.size;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }
}
