package com.birdie.asterismarcanum.effect;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.particle.DelayedFirstPulseParticleOptions;
import com.birdie.asterismarcanum.particle.DelayedSecondPulseParticleOptions;
import com.birdie.asterismarcanum.particle.PulseParticleOptions;
import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import com.birdie.asterismarcanum.registries.ASARMobEffectRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

//referencing EclipsedEffect from Fire's Ender Expansion
public class LunarChannelingEffect extends MagicMobEffect {
    public LunarChannelingEffect() { super(MobEffectCategory.BENEFICIAL, 3353638); }

    public static final float POWER_PER_LEVEL = 0.05f;
    float startTicking = 0;
    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration % 5 == 0;
    }

    @Override
    public void onEffectStarted(LivingEntity pLivingEntity, int pAmplifier) {
        startTicking = Utils.getServerTick();
        super.onEffectStarted(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        float serverTicking;
        serverTicking = (Utils.getServerTick() + 1) - startTicking;
        float ticks;
        int lvl = pLivingEntity.getEffect(ASARMobEffectRegistry.LUNAR_CHANNELING).getAmplifier() + 1;

        ticks = Mth.sqrt(serverTicking) / (10);

        float j = Mth.square(Mth.sin(ticks) * 4);

        float multiplier;

        multiplier = 0.05f + lvl * LunarChannelingEffect.POWER_PER_LEVEL * j;

        AttributeInstance instanceBasePower = pLivingEntity.getAttributes().getInstance(ASARAttributeRegistry.ASTRAL_SPELL_POWER);
        instanceBasePower.addOrUpdateTransientModifier(new AttributeModifier(AsterismArcanum.namespacePath("base.power"), multiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        super.applyEffectTick(pLivingEntity, pAmplifier);
        return true;
    }

    @Override
    public void onEffectRemoved(LivingEntity pLivingEntity, int pAmplifier) {
        AttributeInstance instanceBasePower = pLivingEntity.getAttributes().getInstance(ASARAttributeRegistry.ASTRAL_SPELL_POWER);
        instanceBasePower.removeModifier(AsterismArcanum.namespacePath("base.power"));

        super.onEffectRemoved(pLivingEntity, pAmplifier);
    }
}
