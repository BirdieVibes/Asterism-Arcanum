package com.birdie.asterismarcanum.entity.mobs.lunar_moth;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LunarMothRenderer extends GeoEntityRenderer<LunarMothEntity> {

    public LunarMothRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LunarMothModel());
    }
}
