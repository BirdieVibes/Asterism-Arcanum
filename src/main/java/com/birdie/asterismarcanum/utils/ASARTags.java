package com.birdie.asterismarcanum.utils;

import com.birdie.asterismarcanum.AsterismArcanum;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public class ASARTags {
    // Astral School Focus
    public static final TagKey<Item> ASTRAL_FOCUS = ItemTags.create(
            ResourceLocation.parse(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "astral_focus")
                    .toString())
    );

    public static final TagKey<Item> ASTRAL_RESOURCE = ItemTags.create(
            ResourceLocation.parse(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "astral_resource")
                    .toString())
    );
}
