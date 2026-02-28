package com.birdie.asterismarcanum.entity.mobs.lunar_moth;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class LunarMothModel extends GeoModel<LunarMothEntity> {

    @Override
    public ResourceLocation getModelResource(LunarMothEntity lunarMothEntity) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "geo/lunar_moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LunarMothEntity lunarMothEntity) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/lunar_moth/lunar_moth_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LunarMothEntity lunarMothEntity) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "asterismarcanum/animations/lunar_moth.animation.json");
    }
}
