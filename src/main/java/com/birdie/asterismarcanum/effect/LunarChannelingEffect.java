package com.birdie.asterismarcanum.effect;

import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class LunarChannelingEffect extends MagicMobEffect implements ISyncedMobEffect {
    public static final float ASTRAL_SPELL_POWER_PER_LEVEL = 0.05f;

    public LunarChannelingEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
}
