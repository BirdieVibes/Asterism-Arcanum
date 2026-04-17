package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.particle.*;
import com.mojang.serialization.MapCodec;
import io.redspace.ironsspellbooks.particle.FlameStrikeParticleOptions;
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

    //basically a clone of flaming strike
    public static final Supplier<ParticleType<StarCutParticleOptions>> STAR_CUT_PARTICLE = PARTICLE_TYPES.register("star_cut", () -> new ParticleType<>(true) {
        public MapCodec<StarCutParticleOptions> codec() {
            return StarCutParticleOptions.MAP_CODEC;
        }
        public StreamCodec<? super RegistryFriendlyByteBuf, StarCutParticleOptions> streamCodec() {
            return StarCutParticleOptions.STREAM_CODEC;
        }
    });

    public static final Supplier<ParticleType<GateParticleOptions>> GATE_PARTICLE = PARTICLE_TYPES.register("gate", () -> new ParticleType<>(true) {
        public MapCodec<GateParticleOptions> codec() {
            return GateParticleOptions.MAP_CODEC;
        }
        public StreamCodec<? super RegistryFriendlyByteBuf, GateParticleOptions> streamCodec() {
            return GateParticleOptions.STREAM_CODEC;
        }
    });

//unused pulse particles, based on the trace particle in ISS
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
