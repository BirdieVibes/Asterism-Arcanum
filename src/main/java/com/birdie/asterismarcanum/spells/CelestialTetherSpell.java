package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.celestial_tether.CelestialTetherEntity;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

// Celestial tether suspends you in the air and will absorb a number of incoming hits
public class CelestialTetherSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "celestial_tether");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.duration", Utils.timeFromTicks(getDuration(spellLevel, caster), 1)),
                (Component.translatable("ui.irons_spellbooks.hits_dodged", (int) getSpellPower(spellLevel, caster) + 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(30)
            .build();

    public CelestialTetherSpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 30;
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

    // Im thinking this VVVV isnt actually necessary because you automatically dismount when you teleport anyways and if it has no rider the tether dies
//    @Override
//    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
//        if (entity.hasEffect(MobEffectRegistry.ABYSSAL_SHROUD) || entity.hasEffect(MobEffectRegistry.EVASION)) {
//            return false;
//        }
//        return super.checkPreCastConditions(level, spellLevel, entity, playerMagicData);
//    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        CelestialTetherEntity celestialTetherEntity = new CelestialTetherEntity(world, entity);
        celestialTetherEntity.moveTo(entity.position());
        celestialTetherEntity.setAbsorbedHitsRemaining(spellLevel+1);
        celestialTetherEntity.setLifetime((int) getDuration(spellLevel, entity));
        world.addFreshEntity(celestialTetherEntity);
        entity.startRiding(celestialTetherEntity, true);

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    public float getDuration(int spellLevel, LivingEntity caster) {
        //that 4 is arbitrary I just wanted a reasonable length of uptime
        return (80 + getSpellPower(spellLevel, caster) * 10) * 4;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.SELF_CAST_TWO_HANDS;
    }
}
