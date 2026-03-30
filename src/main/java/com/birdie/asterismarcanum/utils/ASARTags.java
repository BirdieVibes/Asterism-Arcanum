package com.birdie.asterismarcanum.utils;

import com.birdie.asterismarcanum.AsterismArcanum;

import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import javax.swing.text.html.parser.Entity;


public class ASARTags {
    // Astral School Focus
    public static final TagKey<Item> ASTRAL_FOCUS = ItemTags.create(
            ResourceLocation.parse(AsterismArcanum.namespacePath("astral_focus").toString())
    );

    public static final TagKey<Item> ASTRAL_RESOURCE = ItemTags.create(
            ResourceLocation.parse(AsterismArcanum.namespacePath("astral_resource").toString())
    );

    public static final TagKey<Item> STAFF = ItemTags.create(ResourceLocation.parse(AsterismArcanum.namespacePath("staff").toString()));

}
