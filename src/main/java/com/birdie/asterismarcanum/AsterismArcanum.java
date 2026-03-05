package com.birdie.asterismarcanum;

import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothRenderer;
import com.birdie.asterismarcanum.item.weapon.CelestialStaffItemRenderer;
import com.birdie.asterismarcanum.registries.*;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
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
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import software.bernie.geckolib.GeckoLib;
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

        modEventBus.addListener(this::addCreative);

        SpellRegistries.register(modEventBus);
        ASAREntityRegistry.register(modEventBus);
        ASARSchoolRegistry.register(modEventBus);
        ASARAttributeRegistry.register(modEventBus);
        ASARParticleRegistry.register(modEventBus);
        ASARMobEffectRegistry.register(modEventBus);
        ASARModBlocksRegistry.register(modEventBus);
        ASARSoundsRegistry.register(modEventBus);

        AzureLib.initialize();

        NeoForge.EVENT_BUS.register(this);

        LOGGER.info("Asterism Arcanum finished loading!");
    }

    public static ResourceLocation namespacePath(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        AzIdentityRegistry.register(ASARItemsRegistry.CELESTIAL_STAFF.get());
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            AzItemRendererRegistry.register(CelestialStaffItemRenderer::new, ASARItemsRegistry.CELESTIAL_STAFF.get());
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ASARItemsRegistry.STELLAR_NAUTILUS_SHELL);
            event.accept(ASARItemsRegistry.LIQUID_LUMINANCE_BOTTLE);
        }
    }
}