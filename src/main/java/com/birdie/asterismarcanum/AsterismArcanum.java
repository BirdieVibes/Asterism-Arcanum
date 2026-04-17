package com.birdie.asterismarcanum;

import com.birdie.asterismarcanum.item.staves.CelestialStaffRenderer;
import com.birdie.asterismarcanum.registries.*;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(AsterismArcanum.MOD_ID)
public class AsterismArcanum {
    public static final String MOD_ID = "asterismarcanum";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AsterismArcanum(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Asterism Arcanum is loading...");

        modEventBus.addListener(this::commonSetup);

        ASARCreativeModeTabs.register(modEventBus);

        ASARItemsRegistry.register(modEventBus);

        SpellRegistries.register(modEventBus);
        ASAREntityRegistry.register(modEventBus);
        ASARSchoolRegistry.register(modEventBus);
        ASARAttributeRegistry.register(modEventBus);
        ASARParticleRegistry.register(modEventBus);
        ASARModBlocksRegistry.register(modEventBus);
        ASARSoundsRegistry.register(modEventBus);

        AzureLib.initialize();

        NeoForge.EVENT_BUS.register(this);

        LOGGER.info("Asterism Arcanum finished loading!");
    }

    public static ResourceLocation namespacePath(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        AzIdentityRegistry.register(
                ASARItemsRegistry.CELESTIAL_STAFF.get()
        );
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ASARItemsRegistry.ITEMS.getEntries().stream().filter(item -> item.get() instanceof SpellBook).forEach((item) -> CuriosRendererRegistry.register(item.get(), SpellBookCurioRenderer::new));
            });

            AzItemRendererRegistry.register(CelestialStaffRenderer::new, ASARItemsRegistry.CELESTIAL_STAFF.get());
        }
    }
}