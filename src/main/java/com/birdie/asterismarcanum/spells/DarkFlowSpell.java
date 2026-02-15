package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.ArcanumConfig;
import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlow;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DarkFlowSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "dark_flow");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.radius",
                Utils.stringTruncation(getRadius(spellLevel, caster), 1)
        ));
    }


    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(45)
            .build();

    public DarkFlowSpell(ArcanumConfig.DarkFlowConfig config) {
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = getRadius(spellLevel, entity);

        HitResult rayCast = Utils.raycastForEntity(level, entity, 0, true);
        Vec3 center = getCenterFromRaycastAndRadius(rayCast, radius);

        level.playSound(null, center.x, center.y, center.z, SoundRegistry.BLACK_HOLE_CAST.get(), SoundSource.AMBIENT, 4, 1);

        // tracks player location and aligns entity to player

        DarkFlow darkFlow = new DarkFlow(level, entity);
        darkFlow.setRadius(radius);
        darkFlow.setDamage(getDamage(spellLevel, entity));
        darkFlow.moveTo(center);

        level.addFreshEntity(darkFlow);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private static @NotNull Vec3 getCenterFromRaycastAndRadius(HitResult rayCast, float radius) {
        Vec3 center = rayCast.getLocation();

        if (rayCast instanceof BlockHitResult blockHitResult) {
            if (blockHitResult.getDirection().getAxis().isHorizontal()) {
                center = center.subtract(0, radius + 1, 0); // Make black hole centered on hit location
            } else if (blockHitResult.getDirection() == Direction.DOWN) {
                center = center.subtract(0, radius + 1, 0); // Make black hole stick one block into ceiling surface
            } else {
                center = center.subtract(0, radius + 1, 0); // Make black hole sink into ground 1 block if we hit top face
            }
        }

        return center;
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
