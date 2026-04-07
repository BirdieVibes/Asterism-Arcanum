package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.particle.StarCutParticleOptions;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static io.redspace.ironsspellbooks.api.util.Utils.random;
import static net.minecraft.Util.getRandom;

//self-made spell, brightburst pushes away nearby entities and damages them, the distance the entities are repelled is based on spell power, radius,
// and the knockback resistance in the entity
public class BrightburstSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "brightburst");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(20)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.radius",  Utils.stringTruncation(getRadius(spellLevel), 1)));
    }

    public BrightburstSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
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

    int radius;

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new ImpulseCastData();
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        radius = 10 + (spellLevel * 2);
        var damageSource = this.getDamageSource(entity);

        world.getEntitiesOfClass(LivingEntity.class, new AABB(entity.position()
                .subtract(radius, radius, radius), entity.position().add(radius, radius, radius))).forEach((target) -> {
            if (entity.distanceTo(target) <= radius && (target != entity) && (!entity.isAlliedTo(target)) && (!entity.isPassengerOfSameVehicle(target))) {
                target.hurt(damageSource, this.getDamage(spellLevel, entity));
                target.hasImpulse = true;
                Double baseScale = ((getSpellPower(spellLevel, entity)*2)*(1/(entity.distanceTo(target) + .01)));
                target.addDeltaMovement(target.position().subtract(entity.position()).normalize()
                        //clamped between 0 and the base scale so that we dont get negative movement (towards the player) if knockback is greater than the base scale
                        .scale(baseScale - Mth.clamp((target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 5), 0, baseScale)));
            }
        });

        for (int i = 0; i < 7; i++) {
            float xOff = Mth.randomBetween(random, 0, 360) * Mth.nextInt(random, -1, 1);
            float zOff = Mth.randomBetween(random, 0, 360) * Mth.nextInt(random, -1, 1);
            float yOffer = ((xOff - zOff) * (zOff + xOff));
            float yOff = (yOffer * Mth.nextInt(random, -1, 1));

            boolean mirror = random.nextBoolean();
            MagicManager.spawnParticles(world, new StarCutParticleOptions(
                            (float) entity.getX() * xOff, (float) entity.getY() * yOff, (float) entity.getZ() * zOff, mirror, false, 3 + spellLevel/2),
                    entity.getX(), entity.getY() + 1, entity.getZ(),
                    1, 0, 0, 0, 0, true
            );
        }

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private float getRadius(int spellLevel) {
        return 10 + (spellLevel * 2);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return 1 + getSpellPower(spellLevel, caster) / 2;
    }
}
