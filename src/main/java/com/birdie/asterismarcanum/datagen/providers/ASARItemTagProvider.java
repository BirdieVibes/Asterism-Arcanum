package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.utils.ASARTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ASARItemTagProvider extends ItemTagsProvider {
    public ASARItemTagProvider(
            PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, blockTags, AsterismArcanum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ASARTags.ASTRAL_FOCUS).add(ASARItemsRegistry.LIQUID_LUMINANCE_BOTTLE.get());
        tag(ASARTags.ASTRAL_RESOURCE).add(ASARItemsRegistry.ASTRAL_UPGRADE_ORB.get());
    }
}
