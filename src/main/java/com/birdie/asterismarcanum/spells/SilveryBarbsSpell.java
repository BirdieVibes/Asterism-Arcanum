package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.RaycastBuilder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.spells.TargetAreaCastData;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SilveryBarbsSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "silvery_barbs");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(1)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(), 0))
        );
    }

    public SilveryBarbsSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 2;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 25;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    public static float radius;

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        radius = spellLevel + 3;
        float aimAssist = .25f;
        float range = 25f;
        Vec3 start = entity.getEyePosition();
        Vec3 end = entity.getLookAngle().normalize().scale(range).add(start);
        var raycast = RaycastBuilder.begin(entity.level(), entity)
                .start(start)
                .end(end)
                .checkForBlocks(true)
                .bbInflation(aimAssist)
                .build();
        Vec3 center = raycast.getLocation();

        level.getEntitiesOfClass(LivingEntity.class, new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius))).forEach((target) -> {
           if (target != entity && (target.isAlliedTo(entity) || (target instanceof IMagicSummon summon && summon.getSummoner() == entity))) {
               target.addEffect(new MobEffectInstance(MobEffects.LUCK, 20, 1));
               target.addTag("silvery_barbs_tag");
               MagicManager.spawnParticles(level, ParticleTypes.SMALL_GUST,
                       target.position().x,
                       target.position().y + target.getBbHeight()/2,
                       target.position().z,
                       6, .5,.5,.5,0,false);
               MagicManager.spawnParticles(level, ParticleTypes.ELECTRIC_SPARK,
                       target.position().x,
                       target.position().y + target.getBbHeight()/2,
                       target.position().z,
                       15, .5,.5,.5,0,false);
           }
        });

        MagicManager.spawnParticles(level, ParticleTypes.FLASH, center.x, center.y, center.z, 1, 0,0,0,.1,false);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public static float getRadius() {
        return radius;
    }
}
