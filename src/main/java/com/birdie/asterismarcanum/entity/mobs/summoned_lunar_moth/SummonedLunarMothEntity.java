package com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth;

import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.*;

import java.util.UUID;

public class SummonedLunarMothEntity extends LunarMothEntity implements IMagicSummon {
    protected UUID summonerUUID;

    public SummonedLunarMothEntity(EntityType<? extends LunarMothEntity> pEntityType, Level pLevel) {
        super(ASAREntityRegistry.SUMMONED_LUNAR_MOTH.get(), pLevel);
        xpReward = 0;
    }

    public SummonedLunarMothEntity(Level pLevel, LivingEntity owner) {
        this(ASAREntityRegistry.SUMMONED_LUNAR_MOTH.get(), pLevel);
        setSummoner(owner);
    }
    
    @Override
    public void registerGoals() {
        this.goalSelector.getAvailableGoals().removeIf(goal ->
                goal.getGoal() instanceof HurtByTargetGoal || goal.getGoal() instanceof NearestAttackableTargetGoal
        );
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, false) {
            protected boolean canPerformAttack(@NotNull LivingEntity entity) {
                return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0F));
        this.goalSelector.addGoal(5, new GenericFollowOwnerGoal(this, this::getSummoner, 1.0f, 24, 2, true, 50));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.5F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, (new GenericHurtByTargetGoal(this, (entity) -> entity == getSummoner())).setAlertOthers());
        super.registerGoals();
    }

    public void setSummoner(@Nullable LivingEntity owner) {
        if(owner == null) return;
        SummonManager.setOwner(this, owner);
    }

    @Override
    public boolean isAlliedTo(Entity entityIn) {
        return super.isAlliedTo(entityIn) || this.isAlliedHelper(entityIn);
    }

    // Attacks and Death
    @Override
    public void die(DamageSource pDamageSource) {
        this.onDeathHelper();
        super.die(pDamageSource);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {

        if (!super.doHurtTarget(entity)) {
            return false;
        } else {
            if (entity instanceof LivingEntity) {
                ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40), this);
            }
            return Utils.doMeleeAttack(this, entity, SpellRegistries.SUMMON_LUNAR_MOTHS.get().getDamageSource(this, getSummoner()));
        }
    }

    @Override
    public void onRemovedFromLevel() {
        this.onRemovedHelper(this);
        super.onRemovedFromLevel();
    }

    @Override
    public void onUnSummon() {
        if (!this.level().isClientSide)
        {
            MagicManager.spawnParticles(this.level(), ParticleTypes.END_ROD,
                    getX(), getY(), getZ(),
                    25, 0.4, 0.8, 0.4, 0.03, false);
            discard();
        }
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        return !this.shouldIgnoreDamage(pSource) && super.hurt(pSource, pAmount);
    }

    // NBT
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.summonerUUID = OwnerHelper.deserializeOwner(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        OwnerHelper.serializeOwner(pCompound, summonerUUID);
    }
}
