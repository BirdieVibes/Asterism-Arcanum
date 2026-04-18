package com.birdie.asterismarcanum.entity.mobs.dragonfly;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;


public class DragonflyModel extends DefaultedEntityGeoModel<DragonflyEntity> {

    public DragonflyModel() {
        super(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "dragonfly"));
    }

    @Override
    public ResourceLocation getModelResource(DragonflyEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "geo/dragonfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DragonflyEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/dragonfly/dragonfly_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DragonflyEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "animations/dragonfly.animation.json");
    }

    @Override
    public void setCustomAnimations(DragonflyEntity animatable, long instanceId, AnimationState<DragonflyEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
