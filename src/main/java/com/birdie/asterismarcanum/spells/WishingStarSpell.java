package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.particle.PulseParticleOptions;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import com.birdie.asterismarcanum.registries.ASARSoundsRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// Using a lot of code from Shadow Slash -- a multicast dash with a similar range as Teleport

public class WishingStarSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "wishing_star");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.recast_count", getRecastCount(spellLevel, caster)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(5)
            .build();

    public WishingStarSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 25;
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
        return 4;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ASARSoundsRegistry.ASTRAL_CAST_LOW.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        var recasts = playerMagicData.getPlayerRecasts();
        var spellPower = (getSpellPower(spellLevel, entity)/2);
        if (!recasts.hasRecastForSpell(getSpellId())) {
            recasts.addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 120, castSource, null), playerMagicData);
        }

        float distance = ((2 * spellPower) + 8F);
        Vec3 forward = entity.getForward();
        Vec3 end = Utils.raycastForBlock(level, entity.getEyePosition(), entity.getEyePosition().add(forward.scale(distance)), ClipContext.Fluid.NONE).getLocation();
        AABB hitbox = entity.getHitbox().expandTowards(end.subtract(entity.getEyePosition())).inflate(2);
        var targetableEntities = level.getEntities(entity, hitbox, e ->
                !e.isSpectator() &&
                        (e instanceof LivingEntity || e instanceof Projectile) &&
                        e.getBoundingBox().getCenter().subtract(entity.getBoundingBox().getCenter()).normalize().dot(entity.getForward()) >= .85);
        targetableEntities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(entity)));

        Vec3 rayVector = end.subtract(entity.getEyePosition());
        Vec3 impulse = rayVector.scale(1 / 12f).add(0, 0.1, 0);
        entity.setDeltaMovement(entity.getDeltaMovement().scale(0.2).add(impulse));
        entity.hurtMarked = true;
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.FALL_DAMAGE_IMMUNITY, 80, 0, false, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, false, false, false));

        forward = impulse.normalize(); // recalculate forward as the direction we are actually moving
        Vec3 up = new Vec3(0, 1, 0);
        if (forward.dot(up) > .999) {
            up = new Vec3(1, 0, 0);
        }
        Vec3 right = up.cross(forward);
        Vec3 particlePos = end.subtract(forward.scale(3)).add(right.scale(-0.3));

        int trailParticles = 15;
        double speed = rayVector.length() / ((2 * spellPower) + 8.0) * .75;
        for (int i = 0; i < trailParticles; i++) {
            Vec3 particleStart = entity.getBoundingBox().getCenter().add(Utils.getRandomVec3(1 + entity.getBbWidth()));
            Vec3 particleEnd = particleStart.add(rayVector);
            MagicManager.spawnParticles(level, new PulseParticleOptions(Utils.v3f(particleEnd), new Vector3f(5f, 5f, 5f)), particleStart.x, particleStart.y, particleStart.z, 1, 0, 0, 0, speed, false);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_INSTANT_CAST;
    }
}
