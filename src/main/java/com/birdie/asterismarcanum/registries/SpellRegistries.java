package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.spells.*;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SpellRegistries {
    public static final DeferredRegister<AbstractSpell> SPELLS =
            DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, AsterismArcanum.MOD_ID);


// A missile spell which pierces twice and ricochets to targets at a similar angle compared to the player's view
    public static final Supplier<AbstractSpell> STARFIRE =
            registerSpell(new StarfireSpell());
// A teleport spell which extends further than Teleport, but can only be used out of combat (Will be disabled eventually once better transportation shows up
    public static final Supplier<AbstractSpell> MOONBEAMED =
            registerSpell(new MoonbeamedSpell());
// Repels nearby entities including projectiles
    public static final Supplier<AbstractSpell> DARK_FLOW =
            registerSpell(new DarkFlowSpell());
// Gives night vision :P
    public static final Supplier<AbstractSpell> NIGHT_VISION =
            registerSpell(new NightVisionSpell());
// Let me be frank I hate this spell. Anyways. Summons a cursor-tracking infinitely piercing entity
    public static final Supplier<AbstractSpell> STAR_SWARM =
            registerSpell(new StarSwarmSpell());
// Allows you to put yourself in 3D stasis, able to look around and aim, but stuck to a single location until you are freed, either by your or other's intervention
    public static final Supplier<AbstractSpell> TIDAL_LOCK =
            registerSpell(new TidalLockSpell());
// Summons stars at range which explode after some time
    public static final Supplier<AbstractSpell> CONSTELLATION =
            registerSpell(new ConstellationSpell());
// Cone spell but long and skinny <3
    public static final Supplier<AbstractSpell> LUMINOUS_FLARE =
            registerSpell(new LuminousFlareSpell());
// Dimension Teleporting spell similar to Pocket Dimension
//    public static final Supplier<AbstractSpell> ASTRAL_SEA =
//            registerSpell(new AstralSeaSpell());
// Gradually builds astral spell power on the player before sharply dropping off and then lowering astral power
    public static final Supplier<AbstractSpell> LUNAR_CHANNELING =
            registerSpell(new LunarChannelingSpell());
// Moon projectiles orbiting the player which are sent out on second cast to deal damage
//    public static final Supplier<AbstractSpell> MOONCALL =
//            registerSpell(new MooncallSpell());
// A spell which summons lunar moths that apply a debuff in a short range around them, I think the debuff is gonna deal damage with enough stacks
//    public static final Supplier<AbstractSpell> SUMMON_LUNAR_MOTHS =
//            registerSpell(new SummonLunarMothsSpell());

    public static void register(IEventBus eventBus) { SPELLS.register(eventBus); }

    public static Supplier<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
}