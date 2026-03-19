package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ASARSoundsRegistry {

    public static final DeferredRegister<SoundEvent> ASAR_SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AsterismArcanum.MOD_ID);

    //power bow unique arrow whoosh (short hum jingly)
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_CAST = registerSoundEvent("astral_cast");
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_CAST_LOW = registerSoundEvent("astral_cast_low");
    //enchantment critical hit (echoey ting sound)
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_CAST_IMPACT = registerSoundEvent("astral_cast_impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_CAST_IMPACT_LOW = registerSoundEvent("astral_cast_impact_low");

    //death cap mushroom (slow high warble with jingly and hum)
    public static final DeferredHolder<SoundEvent, SoundEvent> LUMINOUS_RAY_LOOP = registerSoundEvent("luminous_ray_loop");

    //void quiver charge impact (hum shatter)
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_SHATTER_1 = registerSoundEvent("astral_shatter_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_SHATTER_2 = registerSoundEvent("astral_shatter_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_SHATTER_3 = registerSoundEvent("astral_shatter_3");

    //item claymore winter impact (sharp shatter)
    public static final DeferredHolder<SoundEvent, SoundEvent> STAR_SHATTER_1 = registerSoundEvent("star_shatter_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> STAR_SHATTER_2 = registerSoundEvent("star_shatter_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> STAR_SHATTER_3 = registerSoundEvent("star_shatter_3");

    //flair amethyst crystal rise (thudwhoosh amethyst chimes sound)
    public static final DeferredHolder<SoundEvent, SoundEvent> NIGHT_VISION_CAST = registerSoundEvent("night_vision_cast");

    //flair orb frenzy ---- dark flow cast (whoosh fast warble with chimes at end)
    public static final DeferredHolder<SoundEvent, SoundEvent> BRIGHTBURST_CAST = registerSoundEvent("brightburst_cast");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return ASAR_SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, name)));
    }

    public static void register (IEventBus eventBus){
        ASAR_SOUND_EVENTS.register(eventBus);
    }
}
