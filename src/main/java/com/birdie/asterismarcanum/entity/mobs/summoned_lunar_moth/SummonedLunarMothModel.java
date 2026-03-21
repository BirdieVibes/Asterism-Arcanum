package com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;


public class SummonedLunarMothModel extends DefaultedEntityGeoModel<SummonedLunarMothEntity> {

    public SummonedLunarMothModel() {
        super(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "lunar_moth"));
    }

    @Override
    public ResourceLocation getModelResource(SummonedLunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "geo/big_moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SummonedLunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/lunar_moth/lunar_moth_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SummonedLunarMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "animations/big_moth.animation.json");
    }

    @Override
    public void setCustomAnimations(SummonedLunarMothEntity animatable, long instanceId, AnimationState<SummonedLunarMothEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
