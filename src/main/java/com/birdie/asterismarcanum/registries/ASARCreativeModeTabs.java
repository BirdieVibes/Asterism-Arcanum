package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ASARCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AsterismArcanum.MOD_ID);

    public static final Supplier<CreativeModeTab> ASTERISM_ARCANUM_ITEMS_TAB = CREATIVE_MODE_TAB.register(
            "asterism_arcanum_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ASARItemsRegistry.STELLAR_NAUTILUS_SHELL.get()))
                    .title(Component.translatable("creativetab.asterismarcanum.asterism_arcanum_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ASARItemsRegistry.STELLAR_NAUTILUS_SHELL.get());
                        output.accept(ASARItemsRegistry.LIQUID_LUMINANCE_BOTTLE.get());
                        output.accept(ASARItemsRegistry.COSMIC_ATLAS.get());
                        output.accept(ASARItemsRegistry.ASTRAL_RUNE.get());
                        output.accept(ASARItemsRegistry.ASTRAL_UPGRADE_ORB.get());
                        output.accept(ASARItemsRegistry.ASTRAL_CROWN.get());
                        output.accept(ASARItemsRegistry.ASTRAL_CHESTPLATE.get());
                        output.accept(ASARItemsRegistry.ASTRAL_LEGGINGS.get());
                        output.accept(ASARItemsRegistry.ASTRAL_BOOTS.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
