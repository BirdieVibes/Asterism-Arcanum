package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.worldgen.ASARModBiomeModifiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ASARDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER=new RegistrySetBuilder()
//            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ASARModBiomeModifiers::bootstrap);

    public ASARDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(AsterismArcanum.MOD_ID));
    }
}
