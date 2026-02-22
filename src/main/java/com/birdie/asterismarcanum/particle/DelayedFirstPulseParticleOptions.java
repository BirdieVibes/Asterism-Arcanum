package com.birdie.asterismarcanum.particle;

import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector3f;

public class DelayedFirstPulseParticleOptions implements ParticleOptions {
    public final Vector3f color, destination;

    public DelayedFirstPulseParticleOptions(Vector3f destination, Vector3f color) {
        this.color = color;
        this.destination = destination;
    }

    public DelayedFirstPulseParticleOptions(float x, float y, float z, float r, float g, float b) {
        this(new Vector3f(x, y, z), new Vector3f(r, g, b));
    }

    public static StreamCodec<? super ByteBuf, DelayedFirstPulseParticleOptions> STREAM_CODEC = StreamCodec.of(
            (buf, option) -> {
                buf.writeFloat(option.destination.x);
                buf.writeFloat(option.destination.y);
                buf.writeFloat(option.destination.z);
                buf.writeFloat(option.color.x);
                buf.writeFloat(option.color.y);
                buf.writeFloat(option.color.z);
            },
            (buf) -> {
                return new DelayedFirstPulseParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat(),buf.readFloat(), buf.readFloat(), buf.readFloat());
            }
    );

    public static MapCodec<DelayedFirstPulseParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(object ->
            object.group(
                    Codec.FLOAT.fieldOf("x").forGetter(p -> p.destination.x),
                    Codec.FLOAT.fieldOf("y").forGetter(p -> p.destination.y),
                    Codec.FLOAT.fieldOf("z").forGetter(p -> p.destination.z),
                    Codec.FLOAT.fieldOf("r").forGetter(p -> p.color.x),
                    Codec.FLOAT.fieldOf("g").forGetter(p -> p.color.y),
                    Codec.FLOAT.fieldOf("b").forGetter(p -> p.color.z)
            ).apply(object, DelayedFirstPulseParticleOptions::new
            ));

    @Override
    public ParticleType<?> getType() {
        return ASARParticleRegistry.DELAYED_FIRST_PULSE_PARTICLE.get();
    }
}
