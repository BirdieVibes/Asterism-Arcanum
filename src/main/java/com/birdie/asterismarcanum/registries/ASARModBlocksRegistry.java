package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ASARModBlocksRegistry {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AsterismArcanum.MOD_ID);

    //==============================LUMINE BLOCKS====================================================
    public static final DeferredBlock<Block> LUMINE = registerBlock("lumine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> CHISELED_LUMINE = registerBlock("chiseled_lumine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> LUMINE_BRICKS = registerBlock("lumine_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> POLISHED_LUMINE = registerBlock("polished_lumine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> LUMINE_STAIRS = registerBlock("lumine_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.LUMINE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LUMINE_SLAB = registerBlock("lumine_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LUMINE_WALL = registerBlock("lumine_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> LUMINE_BRICKS_STAIRS = registerBlock("lumine_bricks_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.LUMINE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LUMINE_BRICKS_SLAB = registerBlock("lumine_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LUMINE_BRICKS_WALL = registerBlock("lumine_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> POLISHED_LUMINE_STAIRS = registerBlock("polished_lumine_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.POLISHED_LUMINE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> POLISHED_LUMINE_SLAB = registerBlock("polished_lumine_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> POLISHED_LUMINE_WALL = registerBlock("polished_lumine_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    //==========================UMBRAL LUMINE BLOCKS=====================================================
    public static final DeferredBlock<Block> UMBRAL_LUMINE = registerBlock("umbral_lumine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> CHISELED_UMBRAL_LUMINE = registerBlock("chiseled_umbral_lumine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> UMBRAL_LUMINE_BRICKS = registerBlock("umbral_lumine_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> POLISHED_UMBRAL_LUMINE = registerBlock("polished_umbral_lumine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> UMBRAL_LUMINE_STAIRS = registerBlock("umbral_lumine_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.UMBRAL_LUMINE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UMBRAL_LUMINE_SLAB = registerBlock("umbral_lumine_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UMBRAL_LUMINE_WALL = registerBlock("umbral_lumine_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> UMBRAL_LUMINE_BRICKS_STAIRS = registerBlock("umbral_lumine_bricks_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UMBRAL_LUMINE_BRICKS_SLAB = registerBlock("umbral_lumine_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UMBRAL_LUMINE_BRICKS_WALL = registerBlock("umbral_lumine_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> POLISHED_UMBRAL_LUMINE_STAIRS = registerBlock("polished_umbral_lumine_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> POLISHED_UMBRAL_LUMINE_SLAB = registerBlock("polished_umbral_lumine_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> POLISHED_UMBRAL_LUMINE_WALL = registerBlock("polished_umbral_lumine_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ASARItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
