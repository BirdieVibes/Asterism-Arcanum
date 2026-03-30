package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.effects.StarburnEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ASARMobEffectsRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, AsterismArcanum.MOD_ID);

    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }

    // Mouse's concept
    public static final DeferredHolder<MobEffect, MobEffect> STARBURN = MOB_EFFECT_DEFERRED_REGISTER.register("starburn",
            ()-> new StarburnEffect(MobEffectCategory.HARMFUL,12851990 ));
}
