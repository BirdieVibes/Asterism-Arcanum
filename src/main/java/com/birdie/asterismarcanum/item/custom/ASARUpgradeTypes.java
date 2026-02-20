package com.birdie.asterismarcanum.item.custom;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

import java.util.Optional;

public enum ASARUpgradeTypes implements UpgradeType {
    ASTRAL_SPELL_POWER(
            "astral_power",
            ASARItemsRegistry.ASTRAL_UPGRADE_ORB,
            ASARAttributeRegistry.ASTRAL_SPELL_POWER,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
            0.05F
    );

    final Holder<Attribute> attribute;
    final AttributeModifier.Operation operation;
    final float amountPerUpgrade;
    final ResourceLocation id;
    final Optional<Holder<Item>> containerItem;

    ASARUpgradeTypes(
            String key, Holder<Item> containerItem,
            Holder<Attribute> attribute, AttributeModifier.Operation operation,
            float amountPerUpgrade
    ) {
        this(key, Optional.of(containerItem), attribute, operation, amountPerUpgrade);
    }

    ASARUpgradeTypes(
            String key, Optional<Holder<Item>> containerItem,
            Holder<Attribute> attribute, AttributeModifier.Operation operation,
            float amountPerUpgrade
    ) {
        this.id = AsterismArcanum.namespacePath(key);
        this.attribute = attribute;
        this.operation = operation;
        this.amountPerUpgrade = amountPerUpgrade;
        this.containerItem = containerItem;
        UpgradeType.registerUpgrade(this);
    }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public Holder<Attribute> getAttribute() { return attribute; }

    @Override
    public AttributeModifier.Operation getOperation() { return operation;}

    @Override
    public float getAmountPerUpgrade() { return amountPerUpgrade; }

    @Override
    public Optional<Holder<Item>> getContainerItem() { return containerItem; }
}
