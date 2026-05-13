package com.birdie.asterismarcanum.entity.mobs.astral_echo;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;


public class AstralEcho extends FrozenHumanoid implements AntiMagicSusceptible {

    public AstralEcho(Level level, LivingEntity entityToCopy) {
        super(level, entityToCopy);
        this.setNoGravity(true);
        this.setShatterDamage(0);
    }

    private int deathTimerOverride = -1;

    @Override
    public void tick() {
        super.tick();
        if (this.deathTimerOverride > 0) {
            MagicManager.spawnParticles(this.level(), ParticleTypes.SMALL_GUST, this.getX(), this.getY() + 1.16, this.getZ(), 1, .5, 1, .5, .5, false);
            deathTimerOverride --;
        }
        if (this.deathTimerOverride == 1) {
            this.discard();
        }
    }

    public void setDeathTimer(int timeInTicks) {
        this.deathTimerOverride = timeInTicks;
    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) {
        if (this.getSummoner() != null) {
            if (!this.getTags().contains("silvery_barbs_tag")) {
                this.getSummoner().setPos(this.position());
                MagicManager.spawnParticles(this.level(), ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY() + 1.16, this.getZ(), 7, .5, 1, .5, .5, false);
                this.discard();
            } else {
                MagicManager.spawnParticles(this.level(), ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY() + 1.16, this.getZ(), 7, .5, 1, .5, .5, false);
                this.discard();
            }
        } else {
            MagicManager.spawnParticles(this.level(), ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY() + 1.16, this.getZ(), 7, .5, 1, .5, .5, false);
            this.discard();
        }
    }
}
