package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AsterismArcanum.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


        blockWithItem(ASARModBlocksRegistry.LUMINE);
        blockWithItem(ASARModBlocksRegistry.LUMINE_BRICKS);
        blockWithItem(ASARModBlocksRegistry.CHISELED_LUMINE);
        blockWithItem(ASARModBlocksRegistry.POLISHED_LUMINE);

        stairsBlock(((StairBlock) ASARModBlocksRegistry.LUMINE_STAIRS.get()), blockTexture(ASARModBlocksRegistry.LUMINE.get()));
        slabBlock(((SlabBlock) ASARModBlocksRegistry.LUMINE_SLAB.get()), blockTexture(ASARModBlocksRegistry.LUMINE.get()), blockTexture(ASARModBlocksRegistry.LUMINE.get()));
        wallBlock(((WallBlock) ASARModBlocksRegistry.LUMINE_WALL.get()), blockTexture(ASARModBlocksRegistry.LUMINE.get()));
        blockItem(ASARModBlocksRegistry.LUMINE_STAIRS);
        blockItem(ASARModBlocksRegistry.LUMINE_SLAB);

        stairsBlock(((StairBlock) ASARModBlocksRegistry.CHISELED_LUMINE_STAIRS.get()), blockTexture(ASARModBlocksRegistry.CHISELED_LUMINE.get()));
        slabBlock(((SlabBlock) ASARModBlocksRegistry.CHISELED_LUMINE_SLAB.get()), blockTexture(ASARModBlocksRegistry.CHISELED_LUMINE.get()), blockTexture(ASARModBlocksRegistry.CHISELED_LUMINE.get()));
        wallBlock(((WallBlock) ASARModBlocksRegistry.CHISELED_LUMINE_WALL.get()), blockTexture(ASARModBlocksRegistry.CHISELED_LUMINE.get()));
        blockItem(ASARModBlocksRegistry.CHISELED_LUMINE_STAIRS);
        blockItem(ASARModBlocksRegistry.CHISELED_LUMINE_SLAB);

        stairsBlock(((StairBlock) ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS.get()), blockTexture(ASARModBlocksRegistry.LUMINE_BRICKS.get()));
        slabBlock(((SlabBlock) ASARModBlocksRegistry.LUMINE_BRICKS_SLAB.get()), blockTexture(ASARModBlocksRegistry.LUMINE_BRICKS.get()), blockTexture(ASARModBlocksRegistry.LUMINE_BRICKS.get()));
        wallBlock(((WallBlock) ASARModBlocksRegistry.LUMINE_BRICKS_WALL.get()), blockTexture(ASARModBlocksRegistry.LUMINE_BRICKS.get()));
        blockItem(ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS);
        blockItem(ASARModBlocksRegistry.LUMINE_BRICKS_SLAB);

        stairsBlock(((StairBlock) ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS.get()), blockTexture(ASARModBlocksRegistry.POLISHED_LUMINE.get()));
        slabBlock(((SlabBlock) ASARModBlocksRegistry.POLISHED_LUMINE_SLAB.get()), blockTexture(ASARModBlocksRegistry.POLISHED_LUMINE.get()), blockTexture(ASARModBlocksRegistry.POLISHED_LUMINE.get()));
        wallBlock(((WallBlock) ASARModBlocksRegistry.POLISHED_LUMINE_WALL.get()), blockTexture(ASARModBlocksRegistry.POLISHED_LUMINE.get()));
        blockItem(ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS);
        blockItem(ASARModBlocksRegistry.POLISHED_LUMINE_SLAB);

        simpleBlock(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get(),
                models().cross(blockTexture(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get()).getPath(), blockTexture(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get())).renderType("cutout"));

    }

    private void blockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }


    private void blockItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("asterismarcanum:block/" + deferredBlock.getId().getPath()));
    }
}
