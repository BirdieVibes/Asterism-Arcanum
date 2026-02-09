package com.birdie.asterismarcanum;

import com.birdie.asterismarcanum.registries.*;
import com.birdie.asterismarcanum.item.ModItems;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(AsterismArcanum.MOD_ID)
public class AsterismArcanum {
    public static final String MOD_ID = "asterismarcanum";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AsterismArcanum(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Asterism Arcanum is loading...");

        modEventBus.addListener(this::commonSetup);

        ASARCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        SpellRegistries.register(modEventBus);

        ASAREntityRegistry.register(modEventBus);

        ASARSchoolRegistry.register(modEventBus);

        ASARAttributeRegistry.register(modEventBus);

        ASARParticleRegistry.register(modEventBus);

        ArcanumConfig.register(modContainer);

        LOGGER.info("Asterism Arcanum finished loading!");
    }

    public static ResourceLocation namespacePath(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) { }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // curios
            event.enqueueWork(() -> ModItems.getASARItems().stream()
                    .filter(item -> item.get() instanceof SpellBook)
                    .forEach((item) -> CuriosRendererRegistry.register(
                            item.get(), SpellBookCurioRenderer::new)
                    ));
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.STELLAR_NAUTILUS_SHELL);
            event.accept(ModItems.LIQUID_LUMINANCE);
        }
    }
}