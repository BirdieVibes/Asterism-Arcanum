package com.birdie.asterismarcanum.worldgen;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ASARModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_LUNAR_MOTH = registerKey("spawn_lunar_moth");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        context.register(SPAWN_LUNAR_MOTH, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FROZEN_PEAKS), biomes.getOrThrow(Biomes.JAGGED_PEAKS), biomes.getOrThrow(Biomes.STONY_PEAKS)),
                        List.of(new MobSpawnSettings.SpawnerData(ASAREntityRegistry.LUNAR_MOTH.get(), 10, 2, 4))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, name));
    }

}
