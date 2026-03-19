package com.birdie.asterismarcanum.entity.spells.luminous_beam;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.render.RenderHelper;
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
import java.util.List;

import static io.redspace.ironsspellbooks.api.util.Utils.random;

// HEAVILY BASED ON ELECTROCUTE
// Single 'lightning' particle in a beam from the caster, theres a lot of things here that aren't theoretically necessary
// but I didn't want to risk breaking something because I barely get what's going on here
public class LuminousBeamRenderer extends EntityRenderer<LuminousBeamProjectile> {

    private static ResourceLocation SOLID = IronsSpellbooks.id("textures/entity/electric_beams/solid.png");

    public LuminousBeamRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LuminousBeamProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        if (entity.getOwner() == null)
            return;
        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        //VertexConsumer consumer = bufferSource.getBuffer(RenderType.lightning());
        poseStack.translate(0, entity.getEyeHeight() * .5f, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getOwner().getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getOwner().getXRot()));
        poseStack.translate(0, 0, 0.3);

        var red = Math.floor(((Math.random() * 100) / 2) + 120);
        var green = Math.floor(((Math.random() * 100) / 2) + 120);
        var blue = Math.floor(((Math.random() * 100) / 2) + 120);

        List<Vec3> segments = entity.getBeamCache();

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(getTextureLocation(entity)));
        float randomNum = Mth.randomBetween(random, 0, 1) / 3;
        float width = (randomNum + 0.8f);
        float height = width;
        Vec3 start = Vec3.ZERO;
        for (int i = 0; i < segments.size() - 1; i += 2) {
            var from = segments.get(i).add(start);
            var to = segments.get(i + 1).add(start);
            drawHull(from, to, width, height, pose, consumer, (int) red, (int) green, (int) blue, 50);
            drawHull(from, to, width * .55f, height * .55f, pose, consumer, (int) red, (int) green, (int) blue, 175);
        }

        consumer = bufferSource.getBuffer(RenderHelper.CustomerRenderType.magicNoCull(getTextureLocation(entity)));
        for (int i = 0; i < segments.size() - 1; i += 2) {
            var from = segments.get(i).add(start);
            var to = segments.get(i + 1).add(start);
            drawHull(from, to, width * .4f, height * .4f, pose, consumer, (int) red, (int) green, (int) blue, 26);
        }

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    public void drawHull(Vec3 from, Vec3 to, float width, float height, PoseStack.Pose pose, VertexConsumer consumer, int r, int g, int b, int a) {
        //Bottom
        drawQuad(from.subtract(0, height * .5f, 0), to.subtract(0, height * .5f, 0), width, 0, pose, consumer, r, g, b, a);
        //Top
        drawQuad(from.add(0, height * .5f, 0), to.add(0, height * .5f, 0), width, 0, pose, consumer, r, g, b, a);
        //Left
        drawQuad(from.subtract(width * .5f, 0, 0), to.subtract(width * .5f, 0, 0), 0, height, pose, consumer, r, g, b, a);
        //Right
        drawQuad(from.add(width * .5f, 0, 0), to.add(width * .5f, 0, 0), 0, height, pose, consumer, r, g, b, a);
    }

    public void drawQuad(Vec3 from, Vec3 to, float width, float height, PoseStack.Pose pose, VertexConsumer consumer, int r, int g, int b, int a) {
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        float halfWidth = width * .5f;
        float halfHeight = height * .5f;

        consumer.addVertex(poseMatrix, (float) from.x - halfWidth, (float) from.y - halfHeight, (float) from.z).setColor(r, g, b, a).setUv(0f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(300).setNormal(0f, 1f, 0f);
        consumer.addVertex(poseMatrix, (float) from.x + halfWidth, (float) from.y + halfHeight, (float) from.z).setColor(r, g, b, a).setUv(1f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(300).setNormal(0f, 1f, 0f);
        consumer.addVertex(poseMatrix, (float) to.x + halfWidth, (float) to.y + halfHeight, (float) to.z).setColor(r, g, b, a).setUv(1f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(300).setNormal(0f, 1f, 0f);
        consumer.addVertex(poseMatrix, (float) to.x - halfWidth, (float) to.y - halfHeight, (float) to.z).setColor(r, g, b, a).setUv(0f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(300).setNormal(0f, 1f, 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(LuminousBeamProjectile p_115264_) {
        return SOLID;
    }
}
