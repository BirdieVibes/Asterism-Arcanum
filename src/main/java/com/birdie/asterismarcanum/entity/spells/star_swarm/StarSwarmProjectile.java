package com.birdie.asterismarcanum.entity.spells.star_swarm;

import com.birdie.asterismarcanum.entity.spells.AbstractGateProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

// this is only here because i was afraid to try to summon the abstractGate with the spell
public class StarSwarmProjectile extends AbstractGateProjectile {
    public StarSwarmProjectile(EntityType<? extends AbstractGateProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public StarSwarmProjectile(Level level, LivingEntity entity) {
        super(ASAREntityRegistry.STAR_SWARM_PROJECTILE.get(), level, entity);
    }


    @Override
    public void spawnParticles() {
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        DamageSources.applyDamage(entity, damage, SpellRegistries.STAR_SWARM.get().getDamageSource(this, getOwner()));
    }

}
