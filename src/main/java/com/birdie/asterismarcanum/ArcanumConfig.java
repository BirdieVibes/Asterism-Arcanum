package com.birdie.asterismarcanum;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;

public class ArcanumConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ConstellationConfig CONSTELLATION;
    public static final DarkFlowConfig DARK_FLOW;
    public static final MoonbeamedConfig MOON_BEAMED;
    public static final NebulousConfig NEBULOUS;
    public static final NightVisionConfig NIGHT_VISION;
    public static final StarFireConfig STAR_FIRE;
    public static final StarSwarmConfig STAR_SWARM;
    public static final TidalLockConfig TIDAL_LOCK;

    public static final ModConfigSpec SPEC;

    static {
        CONSTELLATION = new ConstellationConfig();
        DARK_FLOW = new DarkFlowConfig();
        MOON_BEAMED = new MoonbeamedConfig();
        NEBULOUS = new NebulousConfig();
        NIGHT_VISION = new NightVisionConfig();
        STAR_FIRE = new StarFireConfig();
        STAR_SWARM = new StarSwarmConfig();
        TIDAL_LOCK = new TidalLockConfig();

        SPEC = BUILDER.build();
    }

    public static ArcanumConfig INSTANCE;

    private ArcanumConfig(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, SPEC);
    }

    public static void register(ModContainer modContainer) {
        if (INSTANCE != null) return;

        INSTANCE = new ArcanumConfig(modContainer);
    }

    abstract static class BaseSpellConfig {
        public final ModConfigSpec spec;
        public final IntValue manaCostPerLevel;
        public final IntValue baseSpellPower;
        public final IntValue spellPowerPerLevel;
        public final IntValue castTime;
        public final IntValue baseManaCost;

        BaseSpellConfig(String path) {
            BUILDER.push(path);

            this.manaCostPerLevel = define(BUILDER, "manaCost", 30);
            this.baseSpellPower = define(BUILDER, "baseSpellPower", 1);
            this.spellPowerPerLevel = define(BUILDER, "spellPowerPerLevel", 0);
            this.castTime = define(BUILDER, "castTime", 0);
            this.baseManaCost = define(BUILDER, "baseManaCost", 100);

            BUILDER.pop();

            this.spec = BUILDER.build();
        }
    }

    public static class ConstellationConfig extends BaseSpellConfig {
        public ConstellationConfig() { super("constellation");}
    }

    public static class DarkFlowConfig extends BaseSpellConfig {
        public DarkFlowConfig() { super("darkflow"); }
    }

    public static class MoonbeamedConfig extends BaseSpellConfig {
        public MoonbeamedConfig() { super("moon_beamed"); }
    }

    public static class NebulousConfig extends BaseSpellConfig {
        public NebulousConfig() { super("nebulous"); }
    }

    public static class NightVisionConfig extends BaseSpellConfig {
        public NightVisionConfig() { super("night_vision"); }
    }

    public static class StarFireConfig extends BaseSpellConfig {
        public StarFireConfig() { super("star_fire"); }
    }

    public static class StarSwarmConfig extends BaseSpellConfig {
        public StarSwarmConfig() { super("star_swarm"); }
    }

    public static class TidalLockConfig extends BaseSpellConfig {
        public TidalLockConfig() { super("tidal_lock"); }
    }

    private static BooleanValue define(ModConfigSpec.Builder builder, String name, boolean defaultValue,
                                       String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static BooleanValue define(ModConfigSpec.Builder builder, String name, boolean defaultValue) {
        return builder.define(name, defaultValue);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue) {
        return define(builder, name, defaultValue, 0.0D, Double.MAX_VALUE);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue, double min,
                                                    double max, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue, min, max);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue, double min,
                                                    double max) {
        return builder.defineInRange(name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max,
                                                 String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max) {
        return builder.defineInRange(name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue) {
        return define(builder, name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
