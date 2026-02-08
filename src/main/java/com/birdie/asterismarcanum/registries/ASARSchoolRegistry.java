package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.utils.ASARTags;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ASARSchoolRegistry extends SchoolRegistry {
    public static final ResourceLocation ASTRAL_RESOURCE = AsterismArcanum.namespacePath("astral");

    private static final DeferredRegister<SchoolType> ASAR_SCHOOLS =
            DeferredRegister.create(SCHOOL_REGISTRY_KEY, AsterismArcanum.MOD_ID);

    private static Supplier<SchoolType> registerSchool(SchoolType type) {
        return ASAR_SCHOOLS.register(type.getId().getPath(), () -> type);
    }

    public static final Supplier<SchoolType> ASTRAL = registerSchool(new SchoolType
            (
                    ASTRAL_RESOURCE,
                    ASARTags.ASTRAL_FOCUS,
                    Component.translatable("school.asterismarcanum.astral").withColor(7231167),
                    ASARAttributeRegistry.ASTRAL_SPELL_POWER,
                    ASARAttributeRegistry.ASTRAL_MAGIC_RESIST,
                    SoundRegistry.CLEANSE_CAST,
                    ASARDamageTypes.ASTRAL_MAGIC
            ));

    public static void register(IEventBus eventBus) { ASAR_SCHOOLS.register(eventBus); }

    @Nullable
    public static SchoolType getSchoolFromFocus(ItemStack focusStack) {
        for (SchoolType school : REGISTRY) {
            if (school.isFocus(focusStack)) {
                return school;
            }
        }

        return null;
    }
}
