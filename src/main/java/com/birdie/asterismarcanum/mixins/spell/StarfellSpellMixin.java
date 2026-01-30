package com.birdie.asterismarcanum.mixins.spell;

import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.spells.ender.StarfallSpell;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StarfallSpell.class)
public abstract class StarfellSpellMixin extends AbstractSpell {
    @Override
    public SchoolType getSchoolType() {
        return ASARSchoolRegistry.ASTRAL.get();
    }
}
