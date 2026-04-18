package com.birdie.asterismarcanum.entity.spells.astral_slash;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Random;

// Credit to Glacial Slash from Discerning The Eldritch (and also blood slash!)
public class AstralSlashRenderer extends EntityRenderer<AstralSlashProjectile> {
    private static final ResourceLocation[] TEXTURES = {
            AsterismArcanum.namespacePath("textures/particle/astral_slash_1.png"),
            AsterismArcanum.namespacePath("textures/particle/astral_slash_2.png"),
            AsterismArcanum.namespacePath("textures/particle/astral_slash_3.png"),
            AsterismArcanum.namespacePath("textures/particle/astral_slash_4.png"),
            AsterismArcanum.namespacePath("textures/particle/astral_slash_5.png"),
            AsterismArcanum.namespacePath("textures/particle/astral_slash_6.png"),
    };

    public AstralSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(AstralSlashProjectile slashProjectile, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        PoseStack.Pose pose = poseStack.last();

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, slashProjectile.yRotO, slashProjectile.getYRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(-Mth.lerp(partialTick, slashProjectile.xRotO, slashProjectile.getXRot())));
        float randomZ = new Random(31L * slashProjectile.getId()).nextInt(8);
        poseStack.mulPose(Axis.XP.rotationDegrees(randomZ));

        createSlashTexturePlace(pose, slashProjectile, bufferSource, slashProjectile.getBbWidth() * 1.5F);

        poseStack.popPose();

        super.render(slashProjectile, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void createSlashTexturePlace(PoseStack.Pose pose, AstralSlashProjectile slashProjectile, MultiBufferSource bufferSource, float width)
    {
        Matrix4f poseMatrix = pose.pose();

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(slashProjectile)));

        float halfWidth = width * 0.5F;
        float height = slashProjectile.getBbHeight() * 0.5F;

        consumer.addVertex(poseMatrix, -halfWidth, height, -halfWidth).setColor(255, 255, 255, 255).setUv(0F, 1F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0F, 1F, 0F);
        consumer.addVertex(poseMatrix, halfWidth, height, -halfWidth).setColor(255, 255, 255, 255).setUv(1F, 1F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0F, 1F, 0F);
        consumer.addVertex(poseMatrix, halfWidth, height, halfWidth).setColor(255, 255, 255, 255).setUv(1F, 0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0F, 1F, 0F);
        consumer.addVertex(poseMatrix, -halfWidth, height, halfWidth).setColor(255, 255, 255, 255).setUv(0F, 0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0F, 1F, 0F);
    }

    @Override
    public ResourceLocation getTextureLocation(AstralSlashProjectile slashProjectile) {
        int frame = (slashProjectile.tickCount / 3) % TEXTURES.length;
        return TEXTURES[frame];
    }
}
