package com.birdie.asterismarcanum.entity.mobs.lunar_moth;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.render.RenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.RenderTypeHelper;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import javax.annotation.Nullable;

public class LunarMothRenderer extends GeoEntityRenderer<LunarMothEntity> {

    public LunarMothRenderer(EntityRendererProvider.Context renderManager, GeoModel<LunarMothEntity> model) {
        super(renderManager, model);
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(LunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/lunar_moth/lunar_moth_texture.png");
    }

    @Override
    public void render(LunarMothEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
