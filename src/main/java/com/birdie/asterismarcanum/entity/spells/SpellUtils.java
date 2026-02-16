package com.birdie.asterismarcanum.entity.spells;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
//Thank you Koji!
public class SpellUtils {
    public static void addParticle(Level level, ParticleOptions particleOptions, Vec3 position, Vec3 p, Vec3 random) {
        level.addParticle(particleOptions,
                position.x + random.x + p.x, position.y + random.y + p.y, position.z + random.z + p.z,
                random.x, random.y, random.z
        );
    }
}
