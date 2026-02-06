package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import io.redspace.ironsspellbooks.item.armor.UpgradeOrbType;
import io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ASARUpgradeOrbTypeRegistry {
    public static ResourceKey<UpgradeOrbType> ASTRAL_SPELL_POWER =
            ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "astral_spell_power"));
}
