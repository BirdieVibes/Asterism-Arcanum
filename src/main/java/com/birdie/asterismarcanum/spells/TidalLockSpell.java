package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.tidal_lock.TidalLockEntity;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

//Based on the ice tomb spell from Iron's Spells n' Spellbooks
//Allows you to hover in a location for a time or until you end the spell

public class TidalLockSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "tidal_lock");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(60)
            .build();

    public TidalLockSpell() {
        this.manaCostPerLevel = 3;
        this.baseSpellPower = 50;
        this.spellPowerPerLevel = 20;
        this.castTime = 0;
        this.baseManaCost = 25;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable(
                "ui.irons_spellbooks.duration",
                Utils.timeFromTicks(getDuration(spellLevel, caster), 1)
        ));
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.CLEANSE_CAST.get());
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        TidalLockEntity tidalLockEntity = new TidalLockEntity(world, entity);
        tidalLockEntity.moveTo(entity.position());
        tidalLockEntity.setDeltaMovement(entity.getDeltaMovement());
        tidalLockEntity.setLifetime((int) getDuration(spellLevel, entity));
        world.addFreshEntity(tidalLockEntity);
        entity.startRiding(tidalLockEntity, true);


        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    public float getDuration(int spellLevel, LivingEntity caster) {
        return 160 + spellLevel * 160 * Mth.sqrt(getEntityPowerMultiplier(caster));
    }

    @Override
    public AnimationHolder getCastStartAnimation() { return SpellAnimations.SELF_CAST_TWO_HANDS; }
}
