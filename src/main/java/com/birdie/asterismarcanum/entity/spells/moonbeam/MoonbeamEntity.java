package com.birdie.asterismarcanum.entity.spells.moonbeam;

import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
    public static final int WARMUP_TIME = 15;

    // Unused variable?
    // @Nullable
    // LivingEntity target;

    // I recommend putting constructors with fewer parameters first
    public MoonbeamEntity(Level level) { this(ASAREntityRegistry.MOONBEAM.get(), level); }

    public MoonbeamEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setRadius((float) (this.getBoundingBox().getXsize() * .5f));
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        this.setOldPosAndRot();

        Level level = level();

//        if (tickCount < WARMUP_TIME) {
//            if (target != null) {
//                Vec3 delta = target.position().subtract(this.position());
//                delta = delta.normalize().scale(0.06f);
//                this.setPos(this.position().add(delta));
//            }
//        }

        // If you need this ServerSide no need to check an "if" inside "if" just put another condition :D
        if (tickCount == WARMUP_TIME && !level.isClientSide) {
            checkHits();

            // If you are going to use a method more than 2-3 times you may create a variable for it
            Vec3 position = this.position();
            float radius = this.getRadius();

            MagicManager.spawnParticles(level, ASARParticleRegistry.STARS_PARTICLE.get(),
                    position.x, position.y + 0.06, position.z,
                    50, radius * .7f, .2f, radius * .7f, 0.6f, true
            );

            MagicManager.spawnParticles(level, new BlastwaveParticleOptions(.4f, .85f, 1f, 7f),
                    position.x, position.y + 0.06, position.z,
                    1, 0, 0, 0, 0, true
            );

            level.playSound(
                    null, blockPosition(), SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM,
                    SoundSource.NEUTRAL, 3.5f, Utils.random.nextIntBetweenInclusive(9, 11) * .3f
            );
        }

        if (this.tickCount > WARMUP_TIME) discard();
    }

    @Override
    public void applyEffect(LivingEntity target) { }

    @Override
    protected Vec3 getInflation() { return new Vec3(2, 2, 2); }

    @Override
    public float getParticleCount() { return 0f; }

    @Override
    public void refreshDimensions() { }

    @Override
    public void ambientParticles() { }

    @Override
    public Optional<ParticleOptions> getParticle() { return Optional.empty(); }

    @Override
    public void onAntiMagic(MagicData magicData) { discard(); }
}

