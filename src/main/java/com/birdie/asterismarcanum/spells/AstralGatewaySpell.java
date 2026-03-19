package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.capabilities.magic.AstralSeaManager;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.capabilities.magic.SerializedTargetData;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.network.particles.AbsorptionParticlesPacket;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.TargetAreaCastData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerRespawnPositionEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

//Based on pocket dimension, teleports you and entities in range to or from the astral realm

public class AstralGatewaySpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "astral_gateway");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(60)
            .build();

    public AstralGatewaySpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 0;
        this.spellPowerPerLevel = 0;
        this.castTime = 40;
        this.baseManaCost = 300;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        // do not allow cast time scaling
        return castTime;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.POCKET_DIMENSION_TRAVEL.get());
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.ELDRITCH_PREPARE.get());
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
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
    public ICastDataSerializable getEmptyCastData() {
        return new SerializedTargetData();
    }

    public static final float radius = 8;

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        super.onServerPreCast(level, spellLevel, entity, playerMagicData);
        if (playerMagicData == null)
            return;
        TargetedAreaEntity targetedAreaEntity = TargetedAreaEntity.createTargetAreaEntity(level, entity.position(), radius, 180609, entity);
        playerMagicData.setAdditionalCastData(new TargetAreaCastData(entity.position(), targetedAreaEntity));
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        level.getEntitiesOfClass(LivingEntity.class, new AABB(entity.position().subtract(radius, radius, radius), entity.position().add(radius, radius, radius))).forEach((target) -> {
            if (entity instanceof ServerPlayer serverPlayer && entity.distanceTo(target) <= radius) {
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new AbsorptionParticlesPacket(target.position()));
                if (serverPlayer.level().dimension().equals(AstralSeaManager.ASTRAL_SEA)) {
                    var destination = NeoForge.EVENT_BUS.post(new PlayerRespawnPositionEvent(serverPlayer, serverPlayer.findRespawnPositionAndUseSpawnBlock(true, DimensionTransition.DO_NOTHING), false)).getDimensionTransition();
                    serverPlayer.changeDimension(destination);
                } else {
                    Vec3 playerPos = new Vec3(serverPlayer.getX(), 64, serverPlayer.getZ());

                    serverPlayer.changeDimension(new DimensionTransition(serverPlayer.getServer().getLevel(AstralSeaManager.ASTRAL_SEA), playerPos, Vec3.ZERO, serverPlayer.getXRot(), serverPlayer.getXRot(), DimensionTransition.DO_NOTHING));
                }
            }
        });

        playSound(getCastFinishSound(), entity);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_ANIMATION;
    }
}
