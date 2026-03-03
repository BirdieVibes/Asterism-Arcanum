package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.FLOWER.get())
//                .pattern("BBB")
//                .pattern("BBB")
//                .pattern("BBB")
//                .define('B', ASARItemsRegistry.FLOWER_INGOT)
//                .unlockedBy("has_flower_ingot", has(ASARItemsRegistry.FLOWER_INGOT)).save(recipeOutput);
//
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ASARItemsRegistry.FLOWER_INGOT.get(), 9)
//                .requires(ASARModBlocksRegistry.FLOWER)
//                .unlockedBy("has_flower_block", has(ASARModBlocksRegistry.FLOWER)).save(recipeOutput, "asterismarcanum:ingots_from_flower");

    }
}
