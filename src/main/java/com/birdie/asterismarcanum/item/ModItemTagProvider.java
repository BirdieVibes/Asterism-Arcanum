package com.birdie.asterismarcanum.item;

import com.birdie.asterismarcanum.utils.ASARTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.ModItemTagProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AsterismArcanum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ASARTags.ASTRAL_FOCUS)
                .add(ModItems.LIQUID_LUMINANCE.get());
        tag(ASARTags.ASTRAL_RESOURCE)
                .add(ModItems.ASTRAL_UPGRADE_ORB.get());
    }
}