package com.birdie.asterismarcanum.entity.armor;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.armor.AstralMagicArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class AstralMagicArmorModel extends DefaultedItemGeoModel<AstralMagicArmorItem> {
    public AstralMagicArmorModel() {
        super(AsterismArcanum.namespacePath(""));
    }

    @Override
    public ResourceLocation getModelResource(AstralMagicArmorItem object) {
        return AsterismArcanum.namespacePath("geo/astral_magic_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AstralMagicArmorItem object) {
        return AsterismArcanum.namespacePath("textures/models/astral_magic_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AstralMagicArmorItem WizardArmorItem) {
        return AsterismArcanum.namespacePath("animations/wizard_armor_animation.json");
    }
}
