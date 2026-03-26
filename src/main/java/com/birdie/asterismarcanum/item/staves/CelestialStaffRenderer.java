package com.birdie.asterismarcanum.item.staves;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import mod.azure.azurelib.common.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;

public class CelestialStaffRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath("asterismarcanum", "geo/celestial_staff.geo.json");
    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath("asterismarcanum", "textures/item/celestial_staff.png");

    public CelestialStaffRenderer() {
        super(AzItemRendererConfig.builder(GEO, TEX).setAnimatorProvider(CelestialStaffAnimator::new).addRenderLayer(new AzAutoGlowingLayer<>()).build());
    }
}
