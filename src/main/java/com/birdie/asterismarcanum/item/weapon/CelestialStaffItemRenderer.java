package com.birdie.asterismarcanum.item.weapon;

import com.birdie.asterismarcanum.AsterismArcanum;
import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import mod.azure.azurelib.common.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;

public class CelestialStaffItemRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            AsterismArcanum.MOD_ID,
            "geo/celestial_staff.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            AsterismArcanum.MOD_ID,
            "textures/item/celestial_staff.png"
    );

    public CelestialStaffItemRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setAnimatorProvider(CelestialStaffItemAnimation::new)
                        .build()
        );
    }
}
