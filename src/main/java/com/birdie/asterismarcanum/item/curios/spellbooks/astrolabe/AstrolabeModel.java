package com.birdie.asterismarcanum.item.curios.spellbooks.astrolabe;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class AstrolabeModel extends DefaultedItemGeoModel<AstrolabeSpellBook> {
    public AstrolabeModel() {super(ResourceLocation.fromNamespaceAndPath("asterismarcanum", ""));}

    public ResourceLocation getModelResource(AstrolabeSpellBook animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "geo/astrolabe.geo.json");
    }

    public ResourceLocation getTextureResource(AstrolabeSpellBook animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/item/astrolabe.png");
    }

    public ResourceLocation getAnimationResource(AstrolabeSpellBook animatable) {
        return ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "animations/wizard_armor_animation.json");
    }
}
