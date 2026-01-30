package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.ModItems;
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

    public static final Supplier<CreativeModeTab> ASTERISM_ARCANUM_ITEMS_TAB = CREATIVE_MODE_TAB.register("asterism_arcanum_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STELLAR_NAUTILUS_SHELL.get()))
                    .title(Component.translatable("creativetab.asterismarcanum.asterism_arcanum_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.STELLAR_NAUTILUS_SHELL);
                        output.accept(ModItems.LIQUID_LUMINANCE);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
