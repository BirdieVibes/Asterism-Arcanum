package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.ArcanumConfig;
import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.constellation.Constellation;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlow;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellSummonEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import org.checkerframework.checker.units.qual.K;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ConstellationSpell extends AbstractSpell {
    private final ResourceLocation spellId = AsterismArcanum.namespacePath("constellation");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable(
                "ui.irons_spellbooks.radius",
                Utils.stringTruncation(getRadius(spellLevel, caster), 1)
        ));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(45)
            .build();

    public ConstellationSpell(ArcanumConfig.ConstellationConfig config) {
        this.manaCostPerLevel = config.manaCostPerLevel.getAsInt();
        this.baseSpellPower = config.manaCostPerLevel.getAsInt();
        this.spellPowerPerLevel = config.spellPowerPerLevel.getAsInt();
        this.castTime = config.castTime.getAsInt();
        this.baseManaCost = config.baseManaCost.getAsInt();
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
