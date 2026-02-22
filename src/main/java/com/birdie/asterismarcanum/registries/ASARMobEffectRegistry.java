package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.effect.LunarChannelingEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ASARMobEffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, AsterismArcanum.MOD_ID);

    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }

    public static final DeferredHolder<MobEffect, MobEffect> LUNAR_CHANNELING = MOB_EFFECT_DEFERRED_REGISTER.register("lunar_channeling", () -> new LunarChannelingEffect(MobEffectCategory.BENEFICIAL, 3311322)
            .addAttributeModifier(ASARAttributeRegistry.ASTRAL_SPELL_POWER, AsterismArcanum.namespacePath("mobeffect_lunar_channeling"), LunarChannelingEffect.ASTRAL_SPELL_POWER_PER_LEVEL, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
}
