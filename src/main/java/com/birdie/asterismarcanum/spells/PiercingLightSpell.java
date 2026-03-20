package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.piercing_light.PiercingLightProjectile;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

// a reverse-accupuncture spell centered on the caster!! i.e. spawns a bunch of projectiles at your location moving away from you
public class PiercingLightSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "piercing_light");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(20)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                // times 3 because we are adding entities 3 times per i (this has to be here because getCount affects the amount of entities spawned)
                Component.translatable("ui.irons_spellbooks.projectile_count", getCount(spellLevel, caster) * 3));
    }

    public PiercingLightSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 0;
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
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (entity != null) {
            int count = getCount(spellLevel, entity);
            float damage = getDamage(spellLevel, entity);
            Vec3 center = entity.position().add(0, entity.getEyeHeight() / 3, 0);
            float degreesPerNeedle = 360f / count;
            for (int i = 0; i < count; i++) {
                Vec3 offset = new Vec3(0, Math.random(), .55).normalize().scale(entity.getBbWidth() + 1.5f).yRot(degreesPerNeedle * i * Mth.DEG_TO_RAD);
                Vec3 spawn = center.add(offset);
                Vec3 motion = center.subtract(spawn).normalize();

                //==========================

                PiercingLightProjectile needleMid = new PiercingLightProjectile(world, entity);
                needleMid.moveTo(spawn);
                // this negative value makes the needles go away from the caster
                needleMid.shoot(motion.scale(-0.15f));
                needleMid.setDamage(damage);
                needleMid.setScale(.2f);
                world.addFreshEntity(needleMid);

                //=============================

                PiercingLightProjectile needleNear = new PiercingLightProjectile(world, entity);
                needleNear.moveTo(spawn);
                // this negative value makes the needles go away from the caster
                needleNear.shoot(motion.scale(-0.05f));
                needleNear.setDamage(damage);
                needleNear.setScale(.2f);
                world.addFreshEntity(needleNear);

                //==============================

                PiercingLightProjectile needleFar = new PiercingLightProjectile(world, entity);
                needleFar.moveTo(spawn);
                // this negative value makes the needles go away from the caster
                needleFar.shoot(motion.scale(-0.25f));
                needleFar.setDamage(damage);
                needleFar.setScale(.2f);
                world.addFreshEntity(needleFar);
            }
        }


        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }


    private int getCount(int spellLevel, LivingEntity caster) {
        return (int) ((4 + spellLevel) * getSpellPower(spellLevel, caster));
    }

    //because we're spawning like 30ish projectiles, each one is doing a very small amount of damage
    private float getDamage(int spellLevel, LivingEntity caster) {
        return 1 + getSpellPower(spellLevel, caster) / 2;
    }
}