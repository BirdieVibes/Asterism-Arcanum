package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.ArcanumConfig;
import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.AbstractBeamProjectile;
import com.birdie.asterismarcanum.entity.spells.luminous_ray.LuminousRayProjectile;
import com.birdie.asterismarcanum.particle.PulseParticleOptions;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.spells.ray_of_frost.RayOfFrostVisualEntity;
import io.redspace.ironsspellbooks.network.particles.BloodSiphonParticlesPacket;
import io.redspace.ironsspellbooks.particle.EnderSlashParticleOptions;
import io.redspace.ironsspellbooks.particle.TraceParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.CastingMobAimingData;
import io.redspace.ironsspellbooks.spells.EntityCastData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static io.redspace.ironsspellbooks.spells.blood.RayOfSiphoningSpell.getRange;

public class LuminousRaySpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "luminous_ray");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable(
                "ui.irons_spellbooks.damage",
                Utils.stringTruncation(getDamage(spellLevel, caster), 2)
        ));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(12)
            .build();

    public LuminousRaySpell(ArcanumConfig.LuminousConfig config) {
        this.manaCostPerLevel = config.manaCostPerLevel.getAsInt();
        this.baseSpellPower = config.manaCostPerLevel.getAsInt();
        this.spellPowerPerLevel = config.spellPowerPerLevel.getAsInt();
        this.castTime = config.castTime.getAsInt();
        this.baseManaCost = config.baseManaCost.getAsInt();
    }

    @Override
    public CastType getCastType() {
        return CastType.CONTINUOUS;
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
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.CLOUD_OF_REGEN_LOOP.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.isCasting() && playerMagicData.getCastingSpellId().equals(this.getSpellId())
                && playerMagicData.getAdditionalCastData() instanceof EntityCastData entityCastData
                && entityCastData.getCastingEntity() instanceof AbstractBeamProjectile beam) {
            beam.setDealDamageActive();
        } else {
            LuminousRayProjectile luminousRayProjectile = new LuminousRayProjectile(level, entity);
            luminousRayProjectile.setPos(entity.position().add(0, entity.getEyeHeight() * .9, 0));
            luminousRayProjectile.setDamage(getDamage(spellLevel, entity));
            level.addFreshEntity(luminousRayProjectile);

            playerMagicData.setAdditionalCastData(new EntityCastData(luminousRayProjectile));
        }
        float distance = 6f;
        Vec3 forward = entity.getForward();
        Vec3 end = Utils.raycastForBlock(level, entity.getEyePosition(), entity.getEyePosition().add(forward.scale(distance)),
                ClipContext.Fluid.NONE).getLocation();
        Vec3 rayVector = end.subtract(entity.getEyePosition());
        Vec3 impulse = rayVector.scale(1 / 2f).add(0, 0.1, 0);
        entity.setDeltaMovement(entity.getDeltaMovement().scale(0.2).add(impulse));


        forward = impulse.normalize(); // recalculate forward as the direction we are actually moving
        Vec3 up = new Vec3(0, 1, 0);
        if (forward.dot(up) > .999) {
            up = new Vec3(1, 0, 0);
        }
        Vec3 right = up.cross(forward);
        int trailParticles = 15;
        double speed = rayVector.length() / 6.0 * 2;
        double lessSpeed = rayVector.length() / 6.0 * 1;
        for (int i = 0; i < trailParticles; i++) {
            Vec3 particleStart = entity.getBoundingBox().getCenter().add(Utils.getRandomVec3(0.5 * entity.getBbWidth()));
            Vec3 particleEnd = particleStart.add(rayVector);
            MagicManager.spawnParticles(level, new PulseParticleOptions(Utils.v3f(particleEnd), new Vector3f(10f, 10f, 10f)),
                    particleStart.x, particleStart.y, particleStart.z, 15, 0, 0, 0, speed, true);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public float getDamage(int spellLevel, LivingEntity caster) {
        return 1 + getSpellPower(spellLevel, caster) * .5f;
    }

    @Override
    public SpellDamageSource getDamageSource(@Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker);
    }

    @Override
    public boolean shouldAIStopCasting(int spellLevel, Mob mob, LivingEntity target) {
        return mob.distanceToSqr(target) > (10 * 10) * 1.2;
    }
}
