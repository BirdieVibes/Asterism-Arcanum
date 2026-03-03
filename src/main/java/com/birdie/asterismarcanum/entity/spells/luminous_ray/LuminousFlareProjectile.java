package com.birdie.asterismarcanum.entity.spells.luminous_ray;

import com.birdie.asterismarcanum.entity.spells.AbstractBeamProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class LuminousFlareProjectile extends AbstractBeamProjectile {
    public LuminousFlareProjectile(EntityType<? extends AbstractBeamProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LuminousFlareProjectile(Level level, LivingEntity entity) {
        super(ASAREntityRegistry.LUMINOUS_FLARE_PROJECTILE.get(), level, entity);
    }


    @Override
    public void spawnParticles() {
        var owner = getOwner();
        if (!level().isClientSide || owner == null) {
            return;
        }
        Vec3 rotation = owner.getLookAngle().normalize();
        var pos = owner.position().add(rotation.scale(1.5));
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        DamageSources.applyDamage(entity, damage, SpellRegistries.LUMINOUS_FLARE.get().getDamageSource(this, getOwner()));
    }
}
