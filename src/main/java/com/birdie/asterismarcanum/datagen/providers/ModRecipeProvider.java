package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.registries.ASARModBlocksRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        //====================================ASTROLABE================================
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ASARItemsRegistry.ASTROLABE.get(), 1)
            .pattern(" DC")
            .pattern("DAD")
            .pattern("BD ")
            .define('A', Items.CLOCK)
            .define('B', ItemRegistry.MITHRIL_INGOT.get())
            .define('C', ItemRegistry.TARNISHED_CROWN.get())
            .define('D', Items.CHAIN)
            .unlockedBy("has_material", has(ItemRegistry.MITHRIL_INGOT.get())).save(recipeOutput);

        //=================================LUMINE======================================

        SimpleCookingRecipeBuilder.smelting(
          Ingredient.of(ASARItemsRegistry.DRAGONFLY_WINGS.get()),
          RecipeCategory.MISC,
          ASARItemsRegistry.LUMINE_DUST.get(),
          5,
          100
        ).unlockedBy("has_material", has((ItemLike) ASARItemsRegistry.DRAGONFLY_WINGS)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE.get(), 1)
            .pattern("BAB")
            .pattern("ABA")
            .pattern("BAB")
            .define('B', (ItemLike) ASARItemsRegistry.LUMINE_DUST)
            .define('A', Items.QUARTZ)
            .unlockedBy("has_material", has((ItemLike) ASARItemsRegistry.LUMINE_DUST)).save(recipeOutput);

        stairBuilder(ASARModBlocksRegistry.LUMINE_STAIRS.get(), Ingredient.of(ASARModBlocksRegistry.LUMINE)).group("lumine")
                .unlockedBy("has_material", has(ASARModBlocksRegistry.LUMINE.get())).save(recipeOutput);
        slab(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_SLAB.get(), ASARModBlocksRegistry.LUMINE.get());
        wall(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_WALL.get(), ASARModBlocksRegistry.LUMINE.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_STAIRS, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_SLAB, ASARModBlocksRegistry.LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_WALL, ASARModBlocksRegistry.LUMINE);

        //=================================POLISHED LUMINE=====================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.POLISHED_LUMINE.get(), 4)
            .pattern("BB ")
            .pattern("BB ")
            .pattern("   ")
            .define('B', ASARModBlocksRegistry.LUMINE)
            .unlockedBy("has_material", has(ASARModBlocksRegistry.LUMINE)).save(recipeOutput);

        stairBuilder(ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS.get(), Ingredient.of(ASARModBlocksRegistry.POLISHED_LUMINE)).group("polished_lumine")
                .unlockedBy("has_material", has(ASARModBlocksRegistry.POLISHED_LUMINE.get())).save(recipeOutput);
        slab(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_SLAB.get(), ASARModBlocksRegistry.POLISHED_LUMINE.get());
        wall(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_WALL.get(), ASARModBlocksRegistry.POLISHED_LUMINE.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS, ASARModBlocksRegistry.POLISHED_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_SLAB, ASARModBlocksRegistry.POLISHED_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_WALL, ASARModBlocksRegistry.POLISHED_LUMINE);
        //'lower' tiers of lumine can also craft higher tiers in the stonecutter
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_SLAB, ASARModBlocksRegistry.LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_LUMINE_WALL, ASARModBlocksRegistry.LUMINE);

        //=================================LUMINE BRICK=============================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.LUMINE_BRICKS.get(), 4)
            .pattern("BB ")
            .pattern("BB ")
            .pattern("   ")
            .define('B', ASARModBlocksRegistry.POLISHED_LUMINE)
            .unlockedBy("has_material", has(ASARModBlocksRegistry.POLISHED_LUMINE)).save(recipeOutput);

        stairBuilder(ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS.get(), Ingredient.of(ASARModBlocksRegistry.LUMINE_BRICKS)).group("lumine_bricks")
                .unlockedBy("has_material", has(ASARModBlocksRegistry.LUMINE_BRICKS.get())).save(recipeOutput);
        slab(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_SLAB.get(), ASARModBlocksRegistry.LUMINE_BRICKS.get());
        wall(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_WALL.get(), ASARModBlocksRegistry.LUMINE_BRICKS.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS, ASARModBlocksRegistry.POLISHED_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS, ASARModBlocksRegistry.LUMINE_BRICKS);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_SLAB, ASARModBlocksRegistry.LUMINE_BRICKS,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_WALL, ASARModBlocksRegistry.LUMINE_BRICKS);
        //'lower' tiers of lumine can also craft higher tiers in the stonecutter
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_SLAB, ASARModBlocksRegistry.LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_WALL, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS, ASARModBlocksRegistry.POLISHED_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_SLAB, ASARModBlocksRegistry.POLISHED_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.LUMINE_BRICKS_WALL, ASARModBlocksRegistry.POLISHED_LUMINE);

        //==================================CHISELED LUMINE=========================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.CHISELED_LUMINE.get())
            .pattern("   ")
            .pattern("B  ")
            .pattern("B  ")
            .define('B', ASARModBlocksRegistry.LUMINE_BRICKS_SLAB)
            .unlockedBy("has_material", has(ASARModBlocksRegistry.LUMINE_BRICKS)).save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.CHISELED_LUMINE, ASARModBlocksRegistry.LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.CHISELED_LUMINE, ASARModBlocksRegistry.POLISHED_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.CHISELED_LUMINE, ASARModBlocksRegistry.LUMINE_BRICKS);

        //=============================UMBRAL LUMINE============================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE.get(), 1)
            .pattern("BAB")
            .pattern("ABA")
            .pattern("BAB")
            .define('B', (ItemLike) ASARItemsRegistry.LUMINE_DUST)
            .define('A', Items.AMETHYST_SHARD)
            .unlockedBy("has_material", has((ItemLike) ASARItemsRegistry.LUMINE_DUST)).save(recipeOutput);

        stairBuilder(ASARModBlocksRegistry.UMBRAL_LUMINE_STAIRS.get(), Ingredient.of(ASARModBlocksRegistry.UMBRAL_LUMINE)).group("umbral_lumine")
                .unlockedBy("has_material", has(ASARModBlocksRegistry.UMBRAL_LUMINE.get())).save(recipeOutput);
        slab(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_SLAB.get(), ASARModBlocksRegistry.UMBRAL_LUMINE.get());
        wall(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_WALL.get(), ASARModBlocksRegistry.UMBRAL_LUMINE.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_STAIRS, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_SLAB, ASARModBlocksRegistry.UMBRAL_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_WALL, ASARModBlocksRegistry.UMBRAL_LUMINE);

        //=================================POLISHED UMBRAL LUMINE=====================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get(), 4)
            .pattern("BB ")
            .pattern("BB ")
            .pattern("   ")
            .define('B', ASARModBlocksRegistry.UMBRAL_LUMINE)
            .unlockedBy("has_material", has(ASARModBlocksRegistry.UMBRAL_LUMINE)).save(recipeOutput);

        stairBuilder(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_STAIRS.get(), Ingredient.of(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE)).group("polished_umbral_lumine")
                .unlockedBy("has_material", has(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get())).save(recipeOutput);
        slab(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_SLAB.get(), ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get());
        wall(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_WALL.get(), ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_STAIRS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_SLAB, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_WALL, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE);
        //'lower' tiers of lumine can also craft higher tiers in the stonecutter
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_STAIRS, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_SLAB, ASARModBlocksRegistry.UMBRAL_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_WALL, ASARModBlocksRegistry.UMBRAL_LUMINE);

        //=================================UMBRAL LUMINE BRICK=============================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get(), 4)
            .pattern("BB ")
            .pattern("BB ")
            .pattern("   ")
            .define('B', ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE)
            .unlockedBy("has_material", has(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE)).save(recipeOutput);

        stairBuilder(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_STAIRS.get(), Ingredient.of(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS)).group("umbral_lumine_bricks")
                .unlockedBy("has_material", has(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get())).save(recipeOutput);
        slab(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_SLAB.get(), ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get());
        wall(recipeOutput,RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_WALL.get(), ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_STAIRS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_SLAB, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_WALL, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS);
        //'lower' tiers of lumine can also craft higher tiers in the stonecutter
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_STAIRS, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_SLAB, ASARModBlocksRegistry.UMBRAL_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_WALL, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_STAIRS, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_SLAB, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE,2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_WALL, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE);

        //==================================CHISELED UMBRAL LUMINE=========================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ASARModBlocksRegistry.CHISELED_UMBRAL_LUMINE.get())
            .pattern("   ")
            .pattern("B  ")
            .pattern("B  ")
            .define('B', ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_SLAB)
            .unlockedBy("has_material", has(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS)).save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.CHISELED_UMBRAL_LUMINE, ASARModBlocksRegistry.UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.CHISELED_UMBRAL_LUMINE, ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ASARModBlocksRegistry.CHISELED_UMBRAL_LUMINE, ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS);


        //
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ASARItemsRegistry.FLOWER_INGOT.get(), 9)
//                .requires(ASARModBlocksRegistry.FLOWER)
//                .unlockedBy("has_flower_block", has(ASARModBlocksRegistry.FLOWER)).save(recipeOutput, "asterismarcanum:ingots_from_flower");

    }
}
