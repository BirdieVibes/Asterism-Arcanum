package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.spells.*;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SpellRegistries {
    public static final DeferredRegister<AbstractSpell> SPELLS =
            DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, AsterismArcanum.MOD_ID);

    // STARFIRE SPELL
    // A missile spell which pierces twice and ricochets to targets at a similar angle compared to the player's view
    public static final Supplier<AbstractSpell> STARFIRE =
            registerSpell(new StarfireSpell());

    // BRIGHTBURST SPELL
    // Repels nearby entities including projectiles
    public static final Supplier<AbstractSpell> BRIGHTBURST =
            registerSpell(new BrightburstSpell());

    // STAR SWARM SPELL
    // Summons 5 projectiles every 12 ticks aimed at your looking angle
    public static final Supplier<AbstractSpell> STAR_SWARM =
            registerSpell(new StarSwarmSpell());

    // NIGHT VISION SPELL
    // Gives night vision :P
    public static final Supplier<AbstractSpell> NIGHT_VISION =
            registerSpell(new NightVisionSpell());

    // TIDAL LOCK SPELL
    // Allows you to put yourself in 3D stasis, able to look around and aim, but stuck to a single location until you are freed, either by your or other's intervention
    public static final Supplier<AbstractSpell> TIDAL_LOCK =
            registerSpell(new TidalLockSpell());

    // CONSTELLATION SPELL
    // Summons stars at range which explode after some time
    public static final Supplier<AbstractSpell> CONSTELLATION =
            registerSpell(new ConstellationSpell());

    // LUMINOUS BEAM SPELL
    // Cone spell but long and skinny <3
    public static final Supplier<AbstractSpell> LUMINOUS_BEAM =
            registerSpell(new LuminousBeamSpell());

    // ASTRAL SEA SPELL
    // Dimension Teleporting spell similar to Pocket Dimension
        public static final Supplier<AbstractSpell> ASTRAL_GATEWAY =
                registerSpell(new AstralGatewaySpell());

    // MOONCALL SPELL
    // Moon projectiles orbiting the player which are sent out on second cast to deal damage
//    public static final Supplier<AbstractSpell> MOONCALL =
//            registerSpell(new MooncallSpell());

    // SUMMON LUNAR MOTHS SPELL
    // A spell which summons lunar moths that apply a mana rend debuff (aces spell utils) on hit
    public static final Supplier<AbstractSpell> SUMMON_LUNAR_MOTHS =
            registerSpell(new SummonLunarMothsSpell());

    // WISHING STAR SPELL
    // A multicast slow "dash" that levitates the caster
    public static final Supplier<AbstractSpell> WISHING_STAR =
            registerSpell(new WishingStarSpell());

    public static void register(IEventBus eventBus) { SPELLS.register(eventBus); }

    public static DeferredHolder<AbstractSpell, AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
}