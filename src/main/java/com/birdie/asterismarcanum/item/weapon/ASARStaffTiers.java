package com.birdie.asterismarcanum.item.weapon;

import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.IronsWeaponTier;
import io.redspace.ironsspellbooks.item.weapons.StaffTier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ASARStaffTiers implements IronsWeaponTier {

    public static StaffTier CELESTIAL_STAFF = new StaffTier(
            6.0f, -3.0f,
            new AttributeContainer(ASARAttributeRegistry.ASTRAL_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
            new AttributeContainer(AttributeRegistry.MANA_REGEN, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );

    float damage;
    float speed;
    AttributeContainer[] attributes;

    public ASARStaffTiers(float damage, float speed, AttributeContainer... attributes) {
        this.damage = damage;
        this.speed = speed;
        this.attributes = attributes;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    public AttributeContainer[] getAdditionalAttributes() {
        return this.attributes;
    }
}
