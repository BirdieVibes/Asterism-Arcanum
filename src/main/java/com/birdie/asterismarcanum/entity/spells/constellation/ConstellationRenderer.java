package com.birdie.asterismarcanum.entity.spells.constellation;

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

//based on ball lightning from ISS

public class ConstellationRenderer extends EntityRenderer<Constellation> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "ball_lightning_model"), "main");
    private static final ResourceLocation SWIRL_TEXTURES =
            AsterismArcanum.namespacePath("textures/entity/constellation/constellation.png");

    private final ModelPart orb;

    public ConstellationRenderer(EntityRendererProvider.Context context) {
        super(context);

        ModelPart modelpart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.orb = modelpart.getChild("orb");
    }

    @Override
    public void render(Constellation entity, float pEntityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int pPackedLight) {
        poseStack.pushPose();
        poseStack.translate(0, entity.getBoundingBox().getYsize() * .5f, 0);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        //Vec3 motion = entity.getDeltaMovement();
        //float xRot = -((float) (Mth.atan2(motion.horizontalDistance(), motion.y) * (double) (180F / (float) Math.PI)) - 90.0F);
        //float yRot = -((float) (Mth.atan2(motion.z, motion.x) * (double) (180F / (float) Math.PI)) + 90.0F);
        //poseStack.mulPose(Vector3f.YP.rotationDegrees(yRot));
        //poseStack.mulPose(Vector3f.XP.rotationDegrees(xRot));

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));

        //this.orb.render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY);
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
            float scale = 2f - i * scalePerLayer;
            poseStack.scale(scale, scale, scale);
            this.orb.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, RenderHelper.colorf(r, g, b));
            poseStack.popPose();
        }
        poseStack.popPose();

        super.render(entity, pEntityYaw, partialTicks, poseStack, bufferSource, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Constellation entity) {
        return SWIRL_TEXTURES;
    }

    // Unused method?
    //    public static LayerDefinition createBodyLayer() {
    //        MeshDefinition meshdefinition = new MeshDefinition();
    //        PartDefinition partdefinition = meshdefinition.getRoot();
    //
    //        partdefinition.addOrReplaceChild("orb",
    //                CubeListBuilder.create().texOffs(0, 0)
    //                        .addBox(
    //                                -4.0F, -4.0F, -4.0F,
    //                                8.0F, 8.0F, 8.0F
    //                        ),
    //
    //                PartPose.ZERO);
    //
    //        return LayerDefinition.create(meshdefinition, 8, 8);
    //    }

    private ResourceLocation getSwirlTextureLocation(Constellation entity, int offset) {
        return SWIRL_TEXTURES;
    }
}
