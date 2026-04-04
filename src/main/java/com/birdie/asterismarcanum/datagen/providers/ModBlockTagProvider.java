package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AsterismArcanum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ASARModBlocksRegistry.LUMINE.get())
                .add(ASARModBlocksRegistry.LUMINE_BRICKS.get())
                .add(ASARModBlocksRegistry.CHISELED_LUMINE.get())
                .add(ASARModBlocksRegistry.POLISHED_LUMINE.get())
                .add(ASARModBlocksRegistry.UMBRAL_LUMINE.get())
                .add(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get())
                .add(ASARModBlocksRegistry.CHISELED_UMBRAL_LUMINE.get())
                .add(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get());

        this.tag(BlockTags.STONE_BRICKS)
                .add(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get())
                .add(ASARModBlocksRegistry.LUMINE_BRICKS.get());

        this.tag(Tags.Blocks.STONES)
                .add(ASARModBlocksRegistry.UMBRAL_LUMINE.get())
                .add(ASARModBlocksRegistry.LUMINE.get());

        this.tag(BlockTags.WALLS)
                .add(ASARModBlocksRegistry.UMBRAL_LUMINE_WALL.get())
                .add(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_WALL.get())
                .add(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_WALL.get())
                .add(ASARModBlocksRegistry.LUMINE_WALL.get())
                .add(ASARModBlocksRegistry.LUMINE_BRICKS_WALL.get())
                .add(ASARModBlocksRegistry.POLISHED_LUMINE_WALL.get());
    }
}
