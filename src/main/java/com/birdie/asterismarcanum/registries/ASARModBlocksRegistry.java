package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.block.AstralFlowerBlock;
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
    //FLORA//
    public static final DeferredBlock<Block> STELLAR_COSMOS_FLOWERS = BLOCKS.register("stellar_cosmos_flowers",
            () -> new AstralFlowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));

    //LIQUID LUMINANCE BLOCKS//
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

    public static final DeferredBlock<Block> CHISELED_LUMINE_STAIRS = registerBlock("chiseled_lumine_stairs",
            () -> new StairBlock(ASARModBlocksRegistry.CHISELED_LUMINE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CHISELED_LUMINE_SLAB = registerBlock("chiseled_lumine_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CHISELED_LUMINE_WALL = registerBlock("chiseled_lumine_wall",
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
