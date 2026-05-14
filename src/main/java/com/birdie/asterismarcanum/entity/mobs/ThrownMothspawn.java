package com.birdie.asterismarcanum.entity.mobs;

import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownMothspawn extends ThrowableItemProjectile {
    public ThrownMothspawn(EntityType<? extends ThrownMothspawn> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownMothspawn(Level level, LivingEntity livingEntity) {
        super(ASAREntityRegistry.THROWN_MOTHSPAWN.get(), livingEntity, level);
    }

    public ThrownMothspawn(Level level, double x, double y, double z) {
        super(ASAREntityRegistry.THROWN_MOTHSPAWN.get(), x, y, z, level);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for(int j = 0; j < i; ++j) {
                    LunarMothEntity moth = ASAREntityRegistry.LUNAR_MOTH.get().create(level());
                    if (moth != null) {
                        moth.setPos(position());
                        level().addFreshEntity(moth);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
        super.onHit(hitResult);
        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
        if (!level().isClientSide && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for(int j = 0; j < i; ++j) {
                    LunarMothEntity moth = ASAREntityRegistry.LUNAR_MOTH.get().create(level());
                    if (moth != null) {
                        moth.setPos(position());
                        level().addFreshEntity(moth);
                    }
                }
            }
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4*20, 1));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 4*20, 1));
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ASARItemsRegistry.MOTHSPAWN.get();
    }
}
