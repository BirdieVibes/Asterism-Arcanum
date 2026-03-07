package com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SummonedLunarMothRenderer extends GeoEntityRenderer<SummonedLunarMothEntity> {

    public SummonedLunarMothRenderer(EntityRendererProvider.Context renderManager, GeoModel<SummonedLunarMothEntity> model) {
        super(renderManager, model);
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SummonedLunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/lunar_moth/lunar_moth_texture.png");
    }

    @Override
    public void render(SummonedLunarMothEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
