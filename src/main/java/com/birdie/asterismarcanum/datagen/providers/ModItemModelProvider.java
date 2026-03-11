package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AsterismArcanum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(ASARItemsRegistry.ASTRAL_HELMET.get());
        basicItem(ASARItemsRegistry.ASTRAL_CHESTPLATE.get());
        basicItem(ASARItemsRegistry.ASTRAL_LEGGINGS.get());
        basicItem(ASARItemsRegistry.ASTRAL_BOOTS.get());

        basicItem(ASARItemsRegistry.STELLAR_NAUTILUS_SHELL.get());
        basicItem(ASARItemsRegistry.LIQUID_LUMINANCE_BOTTLE.get());
        basicItem(ASARItemsRegistry.ASTRAL_RUNE.get());
        basicItem(ASARItemsRegistry.STELLAR_COSMOS.get());

        wallItem(ASARModBlocksRegistry.LUMINE_WALL, ASARModBlocksRegistry.LUMINE);
        wallItem(ASARModBlocksRegistry.CHISELED_LUMINE_WALL, ASARModBlocksRegistry.CHISELED_LUMINE);
        wallItem(ASARModBlocksRegistry.LUMINE_BRICKS_WALL, ASARModBlocksRegistry.LUMINE_BRICKS);
        wallItem(ASARModBlocksRegistry.POLISHED_LUMINE_WALL, ASARModBlocksRegistry.POLISHED_LUMINE);
    }

    public void wallItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
