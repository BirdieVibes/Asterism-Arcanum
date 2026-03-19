package com.birdie.asterismarcanum.entity.spells;

import com.birdie.asterismarcanum.entity.spells.starfire.StarfireProjectile;
import io.redspace.ironsspellbooks.api.entity.NoKnockbackProjectile;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
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
//also based on gate of ender from Fire's Ender Expansion

public abstract class AbstractGateProjectile extends Projectile implements NoKnockbackProjectile {
    protected static final int FAILSAFE_EXPIRE_TIME = 20 * 20;
    protected int age;
    protected float damage;
    protected boolean dealDamageActive = true;
    protected final GatePart[] subEntities;

    public AbstractGateProjectile(EntityType<? extends AbstractGateProjectile> entityType, Level level, LivingEntity entity) {
        this(entityType, level);
        setOwner(entity);
    }

    public AbstractGateProjectile(EntityType<? extends AbstractGateProjectile> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.blocksBuilding = false;


        this.subEntities = new GatePart[]{
                new GatePart(this, "part1", 1F, 1F),
                new GatePart(this, "part2", 1F, 1F),
                new GatePart(this, "part3", 1F, 1F),
                new GatePart(this, "part4", 1F, 1F),
                new GatePart(this, "part5", 1F, 1F)
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

    // declaring pos so that we can transfer the position of the entity from the tick function
    public void shootProjectile(Vec3 pos){
        Vec3 origin = pos;
        StarfireProjectile projectile;
        projectile = new StarfireProjectile(this.level(), (LivingEntity) this.getOwner());
        projectile.setPos(origin.subtract(0.0,this.getBbHeight()/2,0.0));
        projectile.setPos(origin.add(0,projectile.getBbHeight()/2,0));
        projectile.setDamage(damage);
        projectile.getSpeed();
        projectile.shoot(this.getOwner().getLookAngle());
        this.level().addFreshEntity(projectile);
    }

//    protected static Vec3 rayTrace(Entity owner) {
//        float f = owner.getXRot();
//        float f1 = owner.getYRot();
//        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
//        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
//        float f6 = f3 * f4;
//        float f7 = f2 * f4;
//        return new Vec3(f6, f5, f7);
//    }

    @Override
    public void tick() {
        super.tick();

        if (++age > FAILSAFE_EXPIRE_TIME) {
            discard();
        }

        var owner = this.getOwner();
        if (owner != null) {
            var ownerEyePos = owner.getEyePosition(1.0f).subtract(0, 1, 0);
            this.setPos(ownerEyePos);
            this.setXRot(owner.getXRot());
            this.setYRot(owner.getYRot());
            this.yRotO = getYRot();
            this.xRotO = getXRot();

            for (int i = 0; i < subEntities.length; i++) {
                var subEntity = subEntities[i];
                // for SOME reason that I don't understand, each i is equal to pi/4 radians (a 8th of a circle),
                // so keep that in mind when adapting this for your own use

                // i.e. if you want like 10 entities, and you want them in a rainbow configuration, set Deg at 0.4

                // if you wanted a circle of entities, you would need to add in a positive y value in newVector so that the entities dont clip into the floor
                double Deg = 0.8 * i;
                double xOffset = Math.cos(Deg) * 2;
                double yOffset = Math.sin(Deg) * 2;
                double cosPsi = Math.cos(Math.toRadians(this.getYRot()));
                double sinPsi = Math.sin(Math.toRadians(this.getYRot()));
                double cosTheta = Math.cos(Math.toRadians(this.getXRot()));
                double sinTheta = Math.sin(Math.toRadians(this.getXRot()));
                Vec3 newVector = this.position().add(xOffset* cosPsi- yOffset * sinTheta * sinPsi,yOffset * cosTheta,(xOffset * sinPsi + yOffset * sinTheta * cosPsi) + 0.5);

                subEntity.setPos(newVector);
                subEntity.setDeltaMovement(newVector);
                var vec3 = new Vec3(subEntity.getX(), subEntity.getY(), subEntity.getZ());
                subEntity.xo = vec3.x;
                subEntity.yo = vec3.y;
                subEntity.zo = vec3.z;
                subEntity.xOld = vec3.x;
                subEntity.yOld = vec3.y;
                subEntity.zOld = vec3.z;

                Vec3 pos;
                pos = newVector;

                // V affects the rate of firing, lower number == faster with min of 1
                if(tickCount %11 == 0){
                    shootProjectile(pos);
                }
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

    // this is the line that says "if you touch the entities you take damage" set it true if you want that
    public void setDealDamageActive() {
        this.dealDamageActive = false;
    }

    protected Set<Entity> getSubEntityCollisions() {
        List<Entity> collisions = new ArrayList<>();
        for (Entity gatepart : subEntities) {
            collisions.addAll(level().getEntities(gatepart, gatepart.getBoundingBox()));
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
