package com.birdie.asterismarcanum.entity.spells.starfire;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

//based on Magic Missile, first spell made
public class StarfireProjectile extends AbstractMagicProjectile {
    public StarfireProjectile(EntityType<? extends StarfireProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public StarfireProjectile(EntityType<? extends StarfireProjectile> entityType, Level levelIn, LivingEntity shooter) {
        this(entityType, levelIn);
        setOwner(shooter);
    }

    public StarfireProjectile(Level levelIn, LivingEntity shooter) {
        this(ASAREntityRegistry.STARFIRE_PROJECTILE.get(), levelIn, shooter);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(this.level(), ParticleHelper.FIREFLY, x, y, z, 25, 0, 0, 0, .18, true);
    }

    @Override
    public float getSpeed() {
        return 2.5f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.empty();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        DamageSources.applyDamage(entityHitResult.getEntity(), damage, SpellRegistries.STARFIRE.get().getDamageSource(this, getOwner()));
        pierceOrDiscard();
    }

    @Override
    public void trailParticles() {
        var vec = getDeltaMovement();
        var length = vec.length();
        int count = (int) Math.min(20, Math.round(length) * 3) +1;
        float f = (float) length / count;
        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(0.02);
            Vec3 p = vec.scale(f * i);
            level().addParticle(ParticleHelper.FIREFLY, this.getX() + random.x + p.x, this.getY() + random.y + p.y, this.getZ() + random.z + p.z, random.x, random.y, random.z);
        }
    }
}
