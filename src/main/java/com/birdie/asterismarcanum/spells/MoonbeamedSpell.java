package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.moonbeam.MoonbeamEntity;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.spells.ender.TeleportSpell;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

//A combination of the Sunbeam spell, teleport spell, and utilizing the combat-exclusion function from Iron's Spells n' Spellbooks
//Teleports the caster a long distance, but can only be used out of combat

public class MoonbeamedSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "moonbeamed");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(5)
            .build();

    public MoonbeamedSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 30;
        this.spellPowerPerLevel = 10;
        this.castTime = 0;
        this.baseManaCost = 30;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        if (entity.getCombatTracker().getCombatDuration() != 0) {
            serverPlayer.displayClientMessage(Component.translatable(
                    "ui.irons_spellbooks.cast_error_combat").withStyle(ChatFormatting.RED), true
            );

            return false;
        }

        return true;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable(
                "ui.irons_spellbooks.distance",
                Utils.stringTruncation(getDistance(spellLevel, caster), 1)
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
        return Optional.of(SoundEvents.AMETHYST_BLOCK_CHIME);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.AMETHYST_BLOCK_RESONATE);
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        super.onServerPreCast(level, spellLevel, entity, playerMagicData);

        MoonbeamEntity moonbeam = new MoonbeamEntity(level);
        moonbeam.setPos(entity.getBoundingBox().getCenter().subtract(0, moonbeam.getBbHeight() * .5f, 0));

        level.addFreshEntity(moonbeam);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        var teleportData = (TeleportSpell.TeleportData) playerMagicData.getAdditionalCastData();

        Vec3 dest;

        if (teleportData != null && teleportData.getTeleportTargetPosition() != null)
            dest = teleportData.getTeleportTargetPosition();
        else
            dest = this.findTeleportLocation(spellLevel, level, entity);

        if (entity.isPassenger()) entity.stopRiding();

        Utils.handleSpellTeleport(this, entity, dest);

        entity.resetFallDistance();

        level.playSound(null, dest.x, dest.y, dest.z, getCastFinishSound().get(), SoundSource.NEUTRAL, 1f, 1f);

        playerMagicData.resetAdditionalCastData();

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);

        MoonbeamEntity moonbeam = new MoonbeamEntity(level);
        moonbeam.setPos(entity.getBoundingBox().getCenter().subtract(0, moonbeam.getBbHeight() * .5f, 0));

        level.addFreshEntity(moonbeam);
    }

    private Vec3 findTeleportLocation(int spellLevel, Level level, LivingEntity entity) {
        return TeleportSpell.findTeleportLocation(level, entity, getDistance(spellLevel, entity));
    }

    private float getDistance(int spellLevel, LivingEntity sourceEntity) {
        return 9 + (float) (Utils.softCapFormula(getEntityPowerMultiplier(sourceEntity)) * getSpellPower(spellLevel, null));
    }

    @Override
    public AnimationHolder getCastStartAnimation() { return AnimationHolder.none(); }
}
