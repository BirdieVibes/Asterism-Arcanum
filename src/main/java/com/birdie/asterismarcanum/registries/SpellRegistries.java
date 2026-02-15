package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.ArcanumConfig;
import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.spells.*;
import com.mojang.datafixers.types.templates.Const;
import io.redspace.ironsspellbooks.api.config.IronConfigParameters;
import io.redspace.ironsspellbooks.api.config.ModifyDefaultConfigValuesEvent;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.spells.ender.StarfallSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SpellRegistries {
    public static final DeferredRegister<AbstractSpell> SPELLS =
            DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, AsterismArcanum.MOD_ID);

    public static final Supplier<AbstractSpell> STARFIRE =
            registerSpell(new StarfireSpell(ArcanumConfig.STAR_FIRE));
    public static final Supplier<AbstractSpell> MOONBEAMED =
            registerSpell(new MoonbeamedSpell(ArcanumConfig.MOON_BEAMED));
    public static final Supplier<AbstractSpell> DARK_FLOW =
        registerSpell(new DarkFlowSpell(ArcanumConfig.DARK_FLOW));
    public static final Supplier<AbstractSpell> NIGHT_VISION =
            registerSpell(new NightVisionSpell(ArcanumConfig.NIGHT_VISION));
    public static final Supplier<AbstractSpell> STAR_SWARM =
            registerSpell(new StarSwarmSpell(ArcanumConfig.STAR_SWARM));
    public static final Supplier<AbstractSpell> TIDAL_LOCK =
            registerSpell(new TidalLockSpell(ArcanumConfig.TIDAL_LOCK));
    public static final Supplier<AbstractSpell> CONSTELLATION =
            registerSpell(new ConstellationSpell(ArcanumConfig.CONSTELLATION));

    //TODO("Luminous Ray")

    public static void register(IEventBus eventBus) { SPELLS.register(eventBus); }

    public static Supplier<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
}