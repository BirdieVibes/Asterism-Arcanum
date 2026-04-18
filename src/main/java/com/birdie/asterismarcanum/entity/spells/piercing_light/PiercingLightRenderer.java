package com.birdie.asterismarcanum.entity.spells.piercing_light;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class PiercingLightRenderer extends EntityRenderer<PiercingLightProjectile> {

    private static final ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/blood_needle/needle_5.png");

    public PiercingLightRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(PiercingLightProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();

        PoseStack.Pose pose = poseStack.last();
        Vec3 motion = entity.getDeltaMovement();
        float xRot = -((float) (Mth.atan2(motion.horizontalDistance(), motion.y) * (double) (180F / (float) Math.PI)) - 90.0F);
        float yRot = -((float) (Mth.atan2(motion.z, motion.x) * (double) (180F / (float) Math.PI)) + 90.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getZRot() + (entity.tickCount + partialTicks) * 40));

        //VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));

        //float halfWidth = width * .5f;
        //old color: 125, 0, 10
        float width = 2.5f;
        //drawSlash(pose, bufferSource, light, width, 2);
        poseStack.mulPose(Axis.XP.rotationDegrees(45));
        float scale = entity.getScale();
        poseStack.scale(scale, scale, scale);
        drawSlash(pose, entity, bufferSource, light, width);

        poseStack.popPose();

        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    private void drawSlash(PoseStack.Pose pose, PiercingLightProjectile entity, MultiBufferSource bufferSource, int light, float width) {
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(getTextureLocation(entity)));
        float halfWidth = width * .5f;
        //old color: 125, 0, 10
        consumer.addVertex(poseMatrix, 0, -halfWidth, -halfWidth).setColor(220, 220, 200, 255).setUv(0f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0f, 1f, 0f);
        consumer.addVertex(poseMatrix, 0, halfWidth, -halfWidth).setColor(200, 200, 220, 255).setUv(1f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0f, 1f, 0f);
        consumer.addVertex(poseMatrix, 0, halfWidth, halfWidth).setColor(220, 200, 200, 255).setUv(1f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0f, 1f, 0f);
        consumer.addVertex(poseMatrix, 0, -halfWidth, halfWidth).setColor(200, 220, 200, 255).setUv(0f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0f, 1f, 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(PiercingLightProjectile entity) {
//        int frame = (entity.tickCount / 4) % TEXTURES.length;
//        return TEXTURES[frame];
        return TEXTURE;
    }

}