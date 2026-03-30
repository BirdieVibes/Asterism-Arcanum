package com.birdie.asterismarcanum.effects;

import com.birdie.asterismarcanum.AsterismArcanum;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

//mouses concept
public class StarburnEffect extends MobEffect {
    public StarburnEffect(MobEffectCategory category, int color) {
        super(MobEffectCategory.HARMFUL, 12851990);

        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "starburn"), -.2,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "starburn"), -.2,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(AttributeRegistry.SPELL_POWER, ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "starburn"), -.2,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    }
}