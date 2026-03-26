package com.birdie.asterismarcanum.item.staves;

import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.IronsWeaponTier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ASARStaffTier implements IronsWeaponTier {

    public static ASARStaffTier CELESTIAL_STAFF = new ASARStaffTier(1.5F, -2.5F,
            new AttributeContainer(ASARAttributeRegistry.ASTRAL_SPELL_POWER, 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
            new AttributeContainer(AttributeRegistry.MANA_REGEN, 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
            new AttributeContainer(AttributeRegistry.COOLDOWN_REDUCTION, 0.10f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );

    float damage;
    float speed;
    AttributeContainer[] attributeContainers;

    public ASARStaffTier(float damage, float speed, AttributeContainer... attributeContainers)
    {
        this.damage = damage;
        this.speed = speed;
        this.attributeContainers = attributeContainers;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public AttributeContainer[] getAdditionalAttributes() {
        return this.attributeContainers;
    }
}
