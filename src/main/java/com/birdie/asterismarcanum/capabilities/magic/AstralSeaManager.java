package com.birdie.asterismarcanum.capabilities.magic;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.Level;

public class AstralSeaManager {
    public static final ResourceKey<Level> ASTRAL_SEA = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "astral_sea"));

}
