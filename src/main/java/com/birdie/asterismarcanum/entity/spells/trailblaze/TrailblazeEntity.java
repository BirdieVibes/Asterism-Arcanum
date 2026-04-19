package com.birdie.asterismarcanum.entity.spells.trailblaze;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARSpellRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class TrailblazeEntity extends AoeEntity implements AntiMagicSusceptible {

    @Nullable
    LivingEntity target;

    public TrailblazeEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setRadius((float) (this.getBoundingBox().getXsize() * .5f));
        this.setNoGravity(true);
    }

    public TrailblazeEntity(Level level) {
        this(ASAREntityRegistry.TRAILBLAZE_ENTITY.get(), level);
    }


    public static final int WARMUP_TIME = 30;

    @Override
    public void tick() {
        this.setOldPosAndRot();

        if (tickCount == WARMUP_TIME) {
            if (!level().isClientSide) {
                checkHits();
                MagicManager.spawnParticles(level(), ParticleTypes.FLASH, getX(), getY()+0.06, getZ(), 1, 0, 0, 0, 0, true);
                level().playSound(null, this.blockPosition(), SoundRegistry.SUNBEAM_IMPACT.get(), SoundSource.NEUTRAL, 1.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .1f);
            }
        }

        if (this.tickCount > WARMUP_TIME) {
            discard();
        }
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    protected boolean canHitTargetForGroundContext(LivingEntity target) {
        return true;
    }

    @Override
    public void applyEffect(LivingEntity target) {
        DamageSources.applyDamage(target, getDamage(), ASARSpellRegistry.TRAILBLAZE.get().getDamageSource(this, getOwner()));
    }

    @Override
    protected Vec3 getInflation() {
        return new Vec3(2, 2, 2);
    }

    @Override
    public float getParticleCount() {
        return 0f;
    }

    @Override
    public void refreshDimensions() {
        return;
    }

    @Override
    public void ambientParticles() {
        return;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    @Override
    public void onAntiMagic(MagicData magicData) {
        discard();
    }
}
