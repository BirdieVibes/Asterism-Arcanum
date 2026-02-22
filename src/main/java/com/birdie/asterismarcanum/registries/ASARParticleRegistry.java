package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.particle.DelayedFirstPulseParticleOptions;
import com.birdie.asterismarcanum.particle.DelayedSecondPulseParticleOptions;
import com.birdie.asterismarcanum.particle.PulseParticleOptions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ASARParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, AsterismArcanum.MOD_ID);

    public static final Supplier<SimpleParticleType> NEBULOUS_DUST_PARTICLE_1 = PARTICLE_TYPES.register(
            "nebulous_dust_1",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> NEBULOUS_DUST_PARTICLE_2 = PARTICLE_TYPES.register(
            "nebulous_dust_2",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> NEBULOUS_DUST_PARTICLE_3 = PARTICLE_TYPES.register(
            "nebulous_dust_3",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> NEBULOUS_DUST_PARTICLE_4 = PARTICLE_TYPES.register(
            "nebulous_dust_4",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> NEBULOUS_DUST_PARTICLE_5 = PARTICLE_TYPES.register(
            "nebulous_dust_5",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> NEBULOUS_DUST_PARTICLE_6 = PARTICLE_TYPES.register(
            "nebulous_dust_6",
            () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> STARDUST_PARTICLE = PARTICLE_TYPES.register(
            "stardust",
            () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> STARS_PARTICLE = PARTICLE_TYPES.register(
            "stars",
            () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SIGNS_PARTICLE = PARTICLE_TYPES.register("signs",
            () -> new SimpleParticleType(true));


    public static final Supplier<SimpleParticleType> ALTSIGNS_PARTICLE = PARTICLE_TYPES.register("altsigns",
            () -> new SimpleParticleType(true));

    public static final Supplier<ParticleType<PulseParticleOptions>> PULSE_PARTICLE = PARTICLE_TYPES.register("pulse",
            () -> new ParticleType<>(true) {
        public MapCodec<PulseParticleOptions> codec() {
            return PulseParticleOptions.MAP_CODEC;
        }

        public StreamCodec<? super RegistryFriendlyByteBuf, PulseParticleOptions> streamCodec() {
            return PulseParticleOptions.STREAM_CODEC;
        }
    });

    public static final Supplier<ParticleType<DelayedFirstPulseParticleOptions>> DELAYED_FIRST_PULSE_PARTICLE = PARTICLE_TYPES.register("delayed_first_pulse",
            () -> new ParticleType<>(true) {
                public MapCodec<DelayedFirstPulseParticleOptions> codec() {
                    return DelayedFirstPulseParticleOptions.MAP_CODEC;
                }

                public StreamCodec<? super RegistryFriendlyByteBuf, DelayedFirstPulseParticleOptions> streamCodec() {
                    return DelayedFirstPulseParticleOptions.STREAM_CODEC;
                }
            });

    public static final Supplier<ParticleType<DelayedSecondPulseParticleOptions>> DELAYED_SECOND_PULSE_PARTICLE = PARTICLE_TYPES.register("delayed_second_pulse",
            () -> new ParticleType<>(true) {
                public MapCodec<DelayedSecondPulseParticleOptions> codec() {
                    return DelayedSecondPulseParticleOptions.MAP_CODEC;
                }

                public StreamCodec<? super RegistryFriendlyByteBuf, DelayedSecondPulseParticleOptions> streamCodec() {
                    return DelayedSecondPulseParticleOptions.STREAM_CODEC;
                }
            });

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
