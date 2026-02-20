package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.constellation.Constellation;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

//A frankensteined spell utilizing script from Flaming Barrage and Ball o' Lightning from Iron's Spells n' Spellbooks
//Still in progress, eventually will have lines of particles drawn between the summoned entities,
//currently just summons entities that explode after a short amount of time
//TODO: Lines between Constellation Points

public class ConstellationSpell extends AbstractSpell {
    private final ResourceLocation spellId = AsterismArcanum.namespacePath("constellation");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(45)
            .build();

    public ConstellationSpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower =1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 40;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable(
                "ui.irons_spellbooks.radius",
                Utils.stringTruncation(getRadius(spellLevel, caster), 1)
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 7;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.CLEANSE_CAST.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = this.getRadius(spellLevel, entity);
        HitResult rayCast = Utils.raycastForEntity(level, entity, 16 + radius * 1.5f, true);
        Vec3 center = rayCast.getLocation();

        PlayerRecasts recasts = playerMagicData.getPlayerRecasts();

        if (!recasts.hasRecastForSpell(getSpellId())) {
            recasts.addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity),
                    100, castSource, null), playerMagicData);
        }

        /*
        if (raycast instanceof BlockHitResult blockHitResult) {
            Constellation constellation = new Constellation(level, entity);

            constellation.setRadius(radius);
            constellation.setDamage(getDamage(spellLevel, entity));
            constellation.moveTo(center);

            level.addFreshEntity(constellation);
        }
         */

        Constellation constellation = new Constellation(level, entity);

        constellation.setRadius(radius);
        constellation.setDamage(getDamage(spellLevel, entity));
        constellation.moveTo(center);

        level.addFreshEntity(constellation);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return getSpellPower(spellLevel, entity) * 2;
    }

    private float getRadius(int spellLevel, LivingEntity entity) {
        return (2 * spellLevel + 4) + (1 * .125f * getSpellPower(spellLevel, entity));
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.FINISH_ANIMATION;
    }

    @Override
    public boolean stopSoundOnCancel() {
        return true;
    }
}
