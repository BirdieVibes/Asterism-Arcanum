package com.birdie.asterismarcanum;

import com.birdie.asterismarcanum.registries.*;
import com.birdie.asterismarcanum.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(AsterismArcanum.MOD_ID)
public class AsterismArcanum {
    public static final String MOD_ID = "asterismarcanum";
    public static final Logger LOGGER = LogUtils.getLogger();


    public AsterismArcanum(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ASARCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        modEventBus.addListener(this::addCreative);

        SpellRegistries.register(modEventBus);

        ASAREntityRegistry.register(modEventBus);

        ASARSchoolRegistry.register(modEventBus);

        ASARAttributeRegistry.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    @Mod(AsterismArcanum.MOD_ID)
    public static  class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.STELLAR_NAUTILUS_SHELL);
            event.accept(ModItems.LIQUID_LUMINANCE);
        }
    }
}