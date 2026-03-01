package com.birdie.asterismarcanum.entity.mobs.lunar_moth;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;


public class LunarMothModel extends DefaultedEntityGeoModel<LunarMothEntity> {

    public LunarMothModel() {
        super(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "lunar_moth"));
    }

    @Override
    public ResourceLocation getModelResource(LunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "geo/lunar_moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/lunar_moth/lunar_moth_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "animations/lunar_moth.animation.json");
    }

    @Override
    public void setCustomAnimations(LunarMothEntity animatable, long instanceId, AnimationState<LunarMothEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
