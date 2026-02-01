package com.birdie.asterismarcanum.entity.spells.moonbeam;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class MoonbeamEntity extends AoeEntity implements AntiMagicSusceptible {

    @Nullable
    LivingEntity target;

    public MoonbeamEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setRadius((float) (this.getBoundingBox().getXsize() * .5f));
        this.setNoGravity(true);
    }

    public MoonbeamEntity(Level level) {
        this(ASAREntityRegistry.MOONBEAM.get(), level);
    }


    public static final int WARMUP_TIME = 15;

    @Override
    public void tick() {
        this.setOldPosAndRot();
//        if (tickCount < WARMUP_TIME) {
//            if (target != null) {
//                Vec3 delta = target.position().subtract(this.position());
//                delta = delta.normalize().scale(0.06f);
//                this.setPos(this.position().add(delta));
//            }
//        }
        if (tickCount == WARMUP_TIME) {
            if (!level().isClientSide) {
                checkHits();
                MagicManager.spawnParticles(level(), ParticleHelper.CLEANSE_PARTICLE, getX(), getY() + 0.06, getZ(), 50, getRadius() * .7f, .2f, getRadius() * .7f, 0.6f, true);
                MagicManager.spawnParticles(level(), new BlastwaveParticleOptions(.4f, .85f, 1f, 7f), getX(), getY() + 0.06, getZ(), 1, 0, 0, 0, 0, true);
                level().playSound(null, this.blockPosition(), SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM, SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f);
            }
        }

        if (this.tickCount > WARMUP_TIME) {
            discard();
        }
    }

    @Override
    public void applyEffect(LivingEntity target) {
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

