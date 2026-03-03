package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
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

        simpleBlock(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get(),
                models().cross(blockTexture(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get()).getPath(), blockTexture(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get())).renderType("cutout"));

    }
}
