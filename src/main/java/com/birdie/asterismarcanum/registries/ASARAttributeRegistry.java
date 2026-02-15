package com.birdie.asterismarcanum.registries;

import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ASARAttributeRegistry {
    private static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, AsterismArcanum.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> ASTRAL_MAGIC_RESIST = newResistanceAttribute("astral");
    public static final DeferredHolder<Attribute, Attribute> ASTRAL_SPELL_POWER = newPowerAttribute("astral");

    public static void register(IEventBus eventBus)
    {
        ATTRIBUTES.register(eventBus);
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entityType -> ATTRIBUTES.getEntries().forEach(
                attributeDeferredHolder -> event.add(entityType, attributeDeferredHolder)
        ));
    }

    // ;_;
    private static DeferredHolder<Attribute, Attribute> newResistanceAttribute(String id) {
        return ATTRIBUTES.register(id + "_magic_resist", () -> (
                new MagicRangedAttribute(
                        "attribute.irons_spellbooks." + id + "_magic_resist",
                        1.0D, -100, 100
                ).setSyncable(true))
        );
    }

    private static DeferredHolder<Attribute, Attribute> newPowerAttribute(String id) {
        return ATTRIBUTES.register(id + "_spell_power", () -> (
                new MagicRangedAttribute(
                        "attribute.irons_spellbooks." + id + "_spell_power",
                        1.0D, -100, 100
                ).setSyncable(true))
        );
    }
}