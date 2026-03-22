package com.birdie.asterismarcanum.entity.spells.starcutter;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.render.RenderHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class StarcutterRenderer extends EntityRenderer<StarcutterEntity> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "ball_lightning_model"), "main");
    private static final ResourceLocation SWIRL_TEXTURES =
            AsterismArcanum.namespacePath("textures/entity/starcutter/starcutter.png");

    private final ModelPart orb;

    public StarcutterRenderer(EntityRendererProvider.Context context) {
        super(context);

        ModelPart modelpart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.orb = modelpart.getChild("orb");
    }

    @Override
    public void render(StarcutterEntity entity, float pEntityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int pPackedLight) {
        poseStack.pushPose();
        poseStack.translate(0, entity.getBoundingBox().getYsize() * .5f, 0);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));

        for (int i = 0; i < 3; i++) {
            poseStack.pushPose();

            float r = 0.85f;
            float g = 0.8f;
            float b = 0.4f;

            r = Mth.clamp(r + r * i, 0, 1f);
            g = Mth.clamp(g + g * i, 0, 1f);
            b = Mth.clamp(b + b * i, 0, 1f);

            float f = entity.tickCount + partialTicks + i * 777;
            float swirlX = Mth.cos(.065f * f) * 180;
            float swirlY = Mth.sin(.065f * f) * 180;
            float swirlZ = Mth.cos(.065f * f + 5464) * 180;
            float scalePerLayer = 0.2f;

            poseStack.mulPose(Axis.XP.rotationDegrees(swirlX * (int) Math.pow(-1, i)));
            poseStack.mulPose(Axis.YP.rotationDegrees(swirlY * (int) Math.pow(-1, i)));
            poseStack.mulPose(Axis.ZP.rotationDegrees(swirlZ * (int) Math.pow(-1, i)));
            consumer = bufferSource.getBuffer(RenderHelper.CustomerRenderType.magic(getSwirlTextureLocation(entity, i * i)));
            float scale = 1f - i * scalePerLayer;
            poseStack.scale(scale, scale, scale);
            this.orb.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, RenderHelper.colorf(r, g, b));
            poseStack.popPose();
        }
        poseStack.popPose();

        super.render(entity, pEntityYaw, partialTicks, poseStack, bufferSource, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(StarcutterEntity entity) {
        return SWIRL_TEXTURES;
    }

    private ResourceLocation getSwirlTextureLocation(StarcutterEntity entity, int offset) {
        return SWIRL_TEXTURES;
    }
}