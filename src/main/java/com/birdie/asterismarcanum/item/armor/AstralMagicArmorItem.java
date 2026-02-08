package com.birdie.asterismarcanum.item.armor;

import com.birdie.asterismarcanum.entity.armor.AstralMagicArmorModel;
import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.item.armor.ImbuableChestplateArmorItem;
import io.redspace.ironsspellbooks.registries.ArmorMaterialRegistry;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AstralMagicArmorItem extends ImbuableChestplateArmorItem {
    public AstralMagicArmorItem(ArmorItem.Type slot, Properties settings) {
        super(ArmorMaterialRegistry.SCHOOL, slot, settings, schoolAttributes(ASARAttributeRegistry.ASTRAL_SPELL_POWER));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GenericCustomArmorRenderer<>(new AstralMagicArmorModel());
    }
}
