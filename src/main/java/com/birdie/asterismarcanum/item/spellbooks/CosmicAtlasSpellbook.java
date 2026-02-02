package com.birdie.asterismarcanum.item.spellbooks;

import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CosmicAtlasSpellbook extends SpellBook {

    public CosmicAtlasSpellbook() {
        withSpellbookAttributes(
                new AttributeContainer(AttributeRegistry.MAX_MANA, 200, AttributeModifier.Operation.ADD_VALUE),
                new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.10F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
        );
    }
}
