package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }
    @Override
    protected void generate() {

        this.dropSelf(ASARModBlocksRegistry.LUMINE.get());

        this.dropSelf(ASARModBlocksRegistry.LUMINE_BRICKS.get());
        this.dropSelf(ASARModBlocksRegistry.CHISELED_LUMINE.get());
        this.dropSelf(ASARModBlocksRegistry.POLISHED_LUMINE.get());

        this.dropSelf(ASARModBlocksRegistry.LUMINE_STAIRS.get());
        this.add(ASARModBlocksRegistry.LUMINE_SLAB.get(),
                block -> createSlabItemTable(ASARModBlocksRegistry.LUMINE_SLAB.get()));
        this.dropSelf(ASARModBlocksRegistry.LUMINE_WALL.get());

        this.dropSelf(ASARModBlocksRegistry.CHISELED_LUMINE_STAIRS.get());
        this.add(ASARModBlocksRegistry.CHISELED_LUMINE_SLAB.get(),
                block -> createSlabItemTable(ASARModBlocksRegistry.CHISELED_LUMINE_SLAB.get()));
        this.dropSelf(ASARModBlocksRegistry.CHISELED_LUMINE_WALL.get());

        this.dropSelf(ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS.get());
        this.add(ASARModBlocksRegistry.LUMINE_BRICKS_SLAB.get(),
                block -> createSlabItemTable(ASARModBlocksRegistry.LUMINE_BRICKS_SLAB.get()));
        this.dropSelf(ASARModBlocksRegistry.LUMINE_BRICKS_WALL.get());

        this.dropSelf(ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS.get());
        this.add(ASARModBlocksRegistry.POLISHED_LUMINE_SLAB.get(),
                block -> createSlabItemTable(ASARModBlocksRegistry.POLISHED_LUMINE_SLAB.get()));
        this.dropSelf(ASARModBlocksRegistry.POLISHED_LUMINE_WALL.get());

        dropOther(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get(), ASARItemsRegistry.STELLAR_COSMOS.get());
        // protected void dropPottedContents(Block flowerPot) {
        //        this.add(flowerPot, (Function)((p_304146_) -> this.createPotFlowerItemTable(((FlowerPotBlock)p_304146_).getPotted())));
        //    }
    }

//    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
//        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
//        return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
//                LootItem.lootTableItem(item)
//                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
//                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
//    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ASARModBlocksRegistry.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
