package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.utils.ASARTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AsterismArcanum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        tag(ASARTags.Items.TRANSFORMABLE_ITEMS)
//                .add(ASARTags.BISMUTH.get())
//                .add(Items.ALLIUM);
        tag(ItemTags.SWORDS)
                .add(ASARItemsRegistry.CELESTIAL_STAFF.get());

        tag(ASARTags.STAFF)
                .add(ASARItemsRegistry.CELESTIAL_STAFF.get());

    }
}
