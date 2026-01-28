package com.birdie.asterismarcanum.item;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AsterismArcanum.MOD_ID);

    public static final DeferredItem<Item> STELLAR_NAUTILUS_SHELL = ITEMS.register("stellar_nautilus_shell",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
