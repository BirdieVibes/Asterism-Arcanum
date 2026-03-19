package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.brightburst.BrightburstEntity;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import com.birdie.asterismarcanum.registries.ASARSoundsRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
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

import java.util.List;

//Spell based on Black Hole in Iron's Spells n' Spellbooks
//Pushes entities within a broad range away from the caster by summoning an inverted black hole entity for a brief amount of time

public class BrightburstSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "brightburst");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(45)
            .build();

    public BrightburstSpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 60;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = getRadius(spellLevel, entity);

        HitResult raycast = Utils.raycastForEntity(level, entity, 0, true);
        Vec3 center = raycast.getLocation();
        if (raycast instanceof BlockHitResult blockHitResult) {
            if (blockHitResult.getDirection().getAxis().isHorizontal()) {
                center = center.subtract(0, radius + 1, 0); // Make black hole centered on hit location
            } else if (blockHitResult.getDirection() == Direction.DOWN) {
                center = center.subtract(0, radius + 1, 0); // Make black hole stick one block into ceiling surface
            } else {
                center = center.subtract(0, radius + 1, 0); // Make black hole sink into ground 1 block if we hit top face
            }
        }

        level.playSound(null, center.x, center.y, center.z, ASARSoundsRegistry.BRIGHTBURST_CAST.get(), SoundSource.AMBIENT, 4, 1);

        // I am so sorry but im just gonna leave this here lol, theoretically it could go in the renderer
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 1f),
                center.x, center.y + radius + (0.55 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 4f),
                center.x, center.y + radius + (0.5 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 8f),
                center.x, center.y + radius + (0.4 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 11.5f),
                center.x, center.y + radius + (0.25 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 13f),
                center.x, center.y + radius, center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 11.5f),
                center.x, center.y + (0.75 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 8f),
                center.x, center.y + (0.6 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 4f),
                center.x, center.y + (0.5 * radius), center.z,
                1, 0, 0, 0, 0, true
        );
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1f, 1f, 1f, 1f),
                center.x, center.y  + (0.45 * radius), center.z,
                1, 0, 0, 0, 0, true
        );

        BrightburstEntity brightburst = new BrightburstEntity(level, entity);
        brightburst.setRadius(radius);
        brightburst.setDamage(getDamage(spellLevel, entity));
        brightburst.moveTo(center);

        level.addFreshEntity(brightburst);

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
        return SpellAnimations.SLASH_ANIMATION;
    }

    @Override
    public boolean stopSoundOnCancel() {
        return true;
    }
}
