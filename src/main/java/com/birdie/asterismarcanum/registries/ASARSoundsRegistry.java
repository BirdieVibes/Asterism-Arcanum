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

    //GENERIC ASTRAL-----------------------------
    //---------------Minecraft Dungeons Enchanted Critical Hit Sounds
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_SOUND = registerSoundEvent("astral_sound");


    //ASTRAL ECHO-----------------------------------------------
    //--------------Minecraft Dungeons Cape Prism Sound
    public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_ECHO_CAST = registerSoundEvent("astral_echo_sound");

    //BRIGHTBURST--------------------------------------------------
    //--------------Minecraft Dungeons Orb Frenzy Flair Sound
    public static final DeferredHolder<SoundEvent, SoundEvent> BRIGHTBURST_SOUND = registerSoundEvent("brightburst_sound");

    //LUMINOUS BEAM-------------------------------------------
    //---------------Tar Prism Beam V1
    public static final DeferredHolder<SoundEvent, SoundEvent> LUMINOUS_BEAM_LOOP = registerSoundEvent("luminous_beam_loop");

    //CELESTIAL TETHER---------------------------------------------
    //-------------Minecraft Dungeons Void Quiver Charged Impact Sounds
    public static final DeferredHolder<SoundEvent, SoundEvent> CELESTIAL_TETHER_SHATTER = registerSoundEvent("celestial_tether_shatter");

    //PIERCING LIGHT--------------------------------------------
    //-----------MC Dungeons Rainbow Flair, MC Dungeons Rainbow Rings Flair, MC Dungeons Smelling Roses Flair, MC Dungeons Level up Glitter
    public static final DeferredHolder<SoundEvent, SoundEvent> PIERCING_LIGHT_CAST_SOUND = registerSoundEvent("piercing_light_cast_sound");

    //SUMMON LUNAR MOTH-----------------------------------------
    //-----------Tar Gale Recharge V2
    public static final DeferredHolder<SoundEvent, SoundEvent> SUMMON_MOTH_SOUND = registerSoundEvent("summon_moth_sound");

    public static final DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return ASAR_SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, name)));
    }

    public static void register (IEventBus eventBus){
        ASAR_SOUND_EVENTS.register(eventBus);
    }
}
