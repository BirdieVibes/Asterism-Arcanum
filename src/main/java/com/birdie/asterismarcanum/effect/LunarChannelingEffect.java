package com.birdie.asterismarcanum.effect;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

//referencing EclipsedEffect from Fire's Ender Expansion
public class LunarChannelingEffect extends MagicMobEffect {
    public LunarChannelingEffect() { super(MobEffectCategory.BENEFICIAL, 3353638); }

    private int duration;

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.tickCount % 20 == 0) {
            AttributeInstance instanceLunPower = pLivingEntity.getAttributes().getInstance(ASARAttributeRegistry.ASTRAL_SPELL_POWER);
            assert instanceLunPower != null;
            instanceLunPower.addOrUpdateTransientModifier(new AttributeModifier(AsterismArcanum.namespacePath("lunar.power"), Mth.sin(this.duration) * 0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
