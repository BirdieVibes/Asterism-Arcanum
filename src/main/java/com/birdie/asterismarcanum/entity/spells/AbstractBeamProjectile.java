package com.birdie.asterismarcanum.entity.spells;

import io.redspace.ironsspellbooks.api.entity.NoKnockbackProjectile;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//based on AbstractConeProjectile from Iron's Spells n' Spellbooks

public abstract class AbstractBeamProjectile extends Projectile implements NoKnockbackProjectile {
    protected static final int FAILSAFE_EXPIRE_TIME = 20 * 20;
    protected int age;
    protected float damage;
    protected boolean dealDamageActive = true;
    protected final BeamPart[] subEntities;

    public AbstractBeamProjectile(EntityType<? extends AbstractBeamProjectile> entityType, Level level, LivingEntity entity) {
        this(entityType, level);
        setOwner(entity);
    }

    public AbstractBeamProjectile(EntityType<? extends AbstractBeamProjectile> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.blocksBuilding = false;

        //I don't know off the top of my head any way to truncate this VVV but since it works I decided it would be okay

        this.subEntities = new BeamPart[]{
                new BeamPart(this, "part1", 1.5F, 1.5F),
                new BeamPart(this, "part2", 1.5F, 1.5F),
                new BeamPart(this, "part3", 1.5F, 1.5F),
                new BeamPart(this, "part4", 1.5F, 1.5F),
                new BeamPart(this, "part5", 1.5F, 1.5F),
                new BeamPart(this, "part6", 1.5F, 1.5F),
                new BeamPart(this, "part7", 1.5F, 1.5F),
                new BeamPart(this, "part8", 1.5F, 1.5F),
                new BeamPart(this, "part9", 1.5F, 1.5F),
                new BeamPart(this, "part10", 1.5F, 1.5F),
                new BeamPart(this, "part11", 1.5F, 1.5F),
                new BeamPart(this, "part12", 1.5F, 1.5F),
                new BeamPart(this, "part13", 1.5F, 1.5F),
                new BeamPart(this, "part14", 1.5F, 1.5F),
                new BeamPart(this, "part15", 1.5F, 1.5F),
                new BeamPart(this, "part16", 1.5F, 1.5F)
        };
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    public abstract void spawnParticles();

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected abstract void onHitEntity(EntityHitResult entityHitResult);

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.subEntities;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.subEntities.length; i++)
            this.subEntities[i].setId(id + i + 1);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    protected static Vec3 rayTrace(Entity owner) {
        float f = owner.getXRot();
        float f1 = owner.getYRot();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        return new Vec3(f6, f5, f7);
    }

    @Override
    public void tick() {
        super.tick();

        if (++age > FAILSAFE_EXPIRE_TIME) {
            discard();
        }

        var owner = this.getOwner();
        if (owner != null) {
            var rayTraceVector = rayTrace(owner);
            var ownerEyePos = owner.getEyePosition(1.0f).subtract(0, .8, 0);
            this.setPos(ownerEyePos);
            this.setXRot(owner.getXRot());
            this.setYRot(owner.getYRot());
            this.yRotO = getYRot();
            this.xRotO = getXRot();
            //setDeltaMovement(ownerEyePos);

            double scale = 1;

            for (int i = 0; i < subEntities.length; i++) {
                var subEntity = subEntities[i];

                double distance = 1 + (i * scale * subEntity.getDimensions(null).width() / 2);
                Vec3 newVector = ownerEyePos.add(rayTraceVector.multiply(distance, distance, distance));
                subEntity.setPos(newVector);
                subEntity.setDeltaMovement(newVector);
                var vec3 = new Vec3(subEntity.getX(), subEntity.getY(), subEntity.getZ());
                subEntity.xo = vec3.x;
                subEntity.yo = vec3.y;
                subEntity.zo = vec3.z;
                subEntity.xOld = vec3.x;
                subEntity.yOld = vec3.y;
                subEntity.zOld = vec3.z;
            }
        }

        if (!level().isClientSide) {
            if (dealDamageActive) {
                for (Entity entity : getSubEntityCollisions()) {
                    onHitEntity(new EntityHitResult(entity));
                }
                dealDamageActive = false;
            }
        } else {
            spawnParticles();
        }

    }

    public void setDealDamageActive() {
        this.dealDamageActive = true;
    }

    protected Set<Entity> getSubEntityCollisions() {
        List<Entity> collisions = new ArrayList<>();
        for (Entity beampart : subEntities) {
            collisions.addAll(level().getEntities(beampart, beampart.getBoundingBox()));
        }

        return collisions.stream().filter(target ->
                target != getOwner() && target instanceof LivingEntity && Utils.hasLineOfSight(level(), this, target, true)
        ).collect(Collectors.toSet());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("Damage", this.damage);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.damage = pCompound.getFloat("Damage");
    }
}
