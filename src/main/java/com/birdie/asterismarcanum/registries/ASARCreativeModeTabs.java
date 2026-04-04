package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ASARCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AsterismArcanum.MOD_ID);

    public static final Supplier<CreativeModeTab> ASTERISM_ARCANUM_ITEMS_TAB = CREATIVE_MODE_TAB.register(
            "asterism_arcanum_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ASARItemsRegistry.DRAGONFLY_WINGS.get()))
                    .title(Component.translatable("creativetab.asterismarcanum.asterism_arcanum_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ASARItemsRegistry.DRAGONFLY_WINGS.get());
                        output.accept(ASARItemsRegistry.ASTRAL_RUNE.get());
                        output.accept(ASARItemsRegistry.ASTRAL_UPGRADE_ORB.get());
                        output.accept((ItemLike) ASARItemsRegistry.ASTRAL_HELMET);
                        output.accept((ItemLike) ASARItemsRegistry.ASTRAL_CHESTPLATE);
                        output.accept((ItemLike) ASARItemsRegistry.ASTRAL_LEGGINGS);
                        output.accept((ItemLike) ASARItemsRegistry.ASTRAL_BOOTS);
                        output.accept(ASARItemsRegistry.DRAGONFLY_SPAWN_EGG.get());
                        output.accept(ASARItemsRegistry.LUNAR_MOTH_SPAWN_EGG.get());
                        output.accept(ASARItemsRegistry.CELESTIAL_STAFF.get());
                        output.accept(ASARItemsRegistry.ASTROLABE.get());
                        output.accept(ASARItemsRegistry.ARCANE_TALISMAN.get());
                        output.accept(ASARItemsRegistry.LUMINE_DUST.get());
                        //==============lumine===================
                        output.accept(ASARModBlocksRegistry.LUMINE.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_SLAB.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_STAIRS.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_WALL.get());
                        output.accept(ASARModBlocksRegistry.CHISELED_LUMINE.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_LUMINE.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_LUMINE_SLAB.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_LUMINE_STAIRS.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_LUMINE_WALL.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_BRICKS.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_BRICKS_SLAB.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_BRICKS_STAIRS.get());
                        output.accept(ASARModBlocksRegistry.LUMINE_BRICKS_WALL.get());
                        //==================umbral lumine=========================
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_SLAB.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_STAIRS.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_WALL.get());
                        output.accept(ASARModBlocksRegistry.CHISELED_UMBRAL_LUMINE.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_SLAB.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_STAIRS.get());
                        output.accept(ASARModBlocksRegistry.POLISHED_UMBRAL_LUMINE_WALL.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_SLAB.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_STAIRS.get());
                        output.accept(ASARModBlocksRegistry.UMBRAL_LUMINE_BRICKS_WALL.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
