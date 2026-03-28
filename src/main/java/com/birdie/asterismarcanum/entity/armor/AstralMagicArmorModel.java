package com.birdie.asterismarcanum.entity.armor;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.armor.AstralMagicArmorItem;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class AstralMagicArmorModel extends DefaultedItemGeoModel<AstralMagicArmorItem> {
    public AstralMagicArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, ""));
    }

    @Override
    public ResourceLocation getModelResource(AstralMagicArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "geo/astral_magic_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AstralMagicArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "textures/entity/astral_armor/astral_robes_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AstralMagicArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}