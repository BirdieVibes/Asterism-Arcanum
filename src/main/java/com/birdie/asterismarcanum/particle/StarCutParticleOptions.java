package com.birdie.asterismarcanum.particle;

import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class StarCutParticleOptions implements ParticleOptions {
    public final float scale;
    public final float xf;
    public final float yf;
    public final float zf;
    public final boolean mirror, vertical;

    public StarCutParticleOptions(float xf, float yf, float zf, boolean mirror, boolean vertical, float scale) {
        this.scale = scale;
        this.xf = xf;
        this.yf = yf;
        this.zf = zf;
        this.mirror = mirror;
        this.vertical = vertical;
    }

    public static StreamCodec<? super ByteBuf, StarCutParticleOptions> STREAM_CODEC = StreamCodec.of(
            (buf, option) -> {
                buf.writeFloat(option.xf);
                buf.writeFloat(option.yf);
                buf.writeFloat(option.zf);
                buf.writeBoolean(option.mirror);
                buf.writeBoolean(option.vertical);
                buf.writeFloat(option.scale);
            },
            (buf) -> new StarCutParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readBoolean(), buf.readFloat())
    );

    public static MapCodec<StarCutParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(object ->
            object.group(
                    Codec.FLOAT.fieldOf("xf").forGetter(p -> ((StarCutParticleOptions) p).xf),
                    Codec.FLOAT.fieldOf("yf").forGetter(p -> ((StarCutParticleOptions) p).yf),
                    Codec.FLOAT.fieldOf("zf").forGetter(p -> ((StarCutParticleOptions) p).zf),
                    Codec.BOOL.fieldOf("mirror").forGetter(p -> ((StarCutParticleOptions) p).mirror),
                    Codec.BOOL.fieldOf("vertical").forGetter(p -> ((StarCutParticleOptions) p).vertical),
                    Codec.FLOAT.fieldOf("scale").forGetter(p -> ((StarCutParticleOptions) p).scale)
            ).apply(object, StarCutParticleOptions::new
            ));

    public @NotNull ParticleType<StarCutParticleOptions> getType() {
        return ASARParticleRegistry.STAR_CUT_PARTICLE.get();
    }
}
