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
// RUNNING TOTAL ----------- 10 IMP, 13 PLANNED ----------------

    // STARFIRE SPELL (my concept)
    // A missile spell which pierces twice and ricochets to targets at a similar angle compared to the player's view
    public static final Supplier<AbstractSpell> STARFIRE =
            registerSpell(new StarfireSpell());

    // BRIGHTBURST SPELL (my concept)
    // Repels and damages nearby entities with repulsion scaled against knockback resistance
    public static final Supplier<AbstractSpell> BRIGHTBURST =
            registerSpell(new BrightburstSpell());

    // CELESTIAL TETHER SPELL (my concept)
    // nullifies a number of incoming attacks and suspends you midair until the spell ends
    public static final Supplier<AbstractSpell> CELESTIAL_TETHER =
            registerSpell(new CelestialTetherSpell());

    // STAR SWARM SPELL (my concept)
    // Summons 5 projectiles every 12 ticks aimed at your looking angle
    public static final Supplier<AbstractSpell> STAR_SWARM =
            registerSpell(new StarSwarmSpell());

    // NIGHT VISION SPELL (my concept)
    // Gives night vision :P
    public static final Supplier<AbstractSpell> NIGHT_VISION =
            registerSpell(new NightVisionSpell());

    // LUMINOUS BEAM SPELL (my concept)
    // Cone spell but long and skinny <3
    public static final Supplier<AbstractSpell> LUMINOUS_BEAM =
            registerSpell(new LuminousBeamSpell());

    // ASTRAL SEA SPELL (my concept)
    // Dimension Teleporting spell similar to Pocket Dimension
        public static final Supplier<AbstractSpell> ASTRAL_GATEWAY =
                registerSpell(new AstralGatewaySpell());

    // SUMMON LUNAR MOTHS SPELL (my concept)
    // A spell which summons a giant moth mount
    public static final Supplier<AbstractSpell> SUMMON_LUNAR_MOTHS =
            registerSpell(new SummonLunarMothsSpell());

    // WISHING STAR SPELL (my concept)
    // A multicast slow "dash" that levitates the caster
    public static final Supplier<AbstractSpell> WISHING_STAR =
            registerSpell(new WishingStarSpell());

    // PIERCING LIGHT SPELL (my concept)
    // Creates dozens of low damage short range projectiles around you to ward off enemies
    public static final Supplier<AbstractSpell> PIERCING_LIGHT =
            registerSpell(new PiercingLightSpell());

    //STARCUTTER SPELL (Mouse's concept)
    //Generate a small orb of astral magic before you, and then barrage that area with magical tears/slashes.
    public static final Supplier<AbstractSpell> STARCUTTER =
            registerSpell(new StarcutterSpell());


    public static void register(IEventBus eventBus) { SPELLS.register(eventBus); }

    public static DeferredHolder<AbstractSpell, AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
}