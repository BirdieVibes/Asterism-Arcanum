package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.star_swarm.GatePortal;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

//Heavily utilizing Gate of Ender from Fire's Ender Expansion

public class StarSwarmSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "star_swarm");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMaxLevel(4)
            .setCooldownSeconds(60)
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel,caster),1))
        );
    }

    public StarSwarmSpell(){
        this.manaCostPerLevel = 1;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 0;
        this.castTime = 200;
        this.baseManaCost = 15;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.CONTINUOUS;
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        this.shootRandomStar(level, spellLevel, entity);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .3f, false);
        return true;
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
    }

    public void shootRandomStar(Level world, int spellLevel, LivingEntity entity) {
        double radius = 2;
        radius *= getRadius(spellLevel,entity) / 3;

        {double gate1Deg = Math.toRadians(10);
            double xOffset = Math.cos(gate1Deg) * radius;
            double yOffset = Math.sin(gate1Deg) * radius + 2;
            double cosPsi = Math.cos(Math.toRadians(entity.getYRot()));
            double sinPsi = Math.sin(Math.toRadians(entity.getYRot()));
            double cosTheta = Math.cos(Math.toRadians(entity.getXRot()));
            double sinTheta = Math.sin(Math.toRadians(entity.getXRot()));
            Vec3 origin = entity.position().add(xOffset* cosPsi- yOffset * sinTheta * sinPsi,yOffset * cosTheta,xOffset * sinPsi + yOffset * sinTheta * cosPsi);
            GatePortal gate = new GatePortal(world,entity);
            gate.setPos(origin);

            gate.setDamage(this.getDamage(spellLevel,entity));
            world.addFreshEntity(gate);
        }
        {double gate2Deg = Math.toRadians(45);
            double xOffset = Math.cos(gate2Deg) * radius;
            double yOffset = Math.sin(gate2Deg) * radius + 2;
            double cosPsi = Math.cos(Math.toRadians(entity.getYRot()));
            double sinPsi = Math.sin(Math.toRadians(entity.getYRot()));
            double cosTheta = Math.cos(Math.toRadians(entity.getXRot()));
            double sinTheta = Math.sin(Math.toRadians(entity.getXRot()));
            Vec3 origin = entity.position().add(xOffset* cosPsi- yOffset * sinTheta * sinPsi,yOffset * cosTheta,xOffset * sinPsi + yOffset * sinTheta * cosPsi);
            GatePortal gate = new GatePortal(world,entity);
            gate.setPos(origin);

            gate.setDamage(this.getDamage(spellLevel,entity));
            world.addFreshEntity(gate);
        }
        {double gate3Deg = Math.toRadians(90);
            double xOffset = Math.cos(gate3Deg) * radius;
            double yOffset = Math.sin(gate3Deg) * radius + 2;
            double cosPsi = Math.cos(Math.toRadians(entity.getYRot()));
            double sinPsi = Math.sin(Math.toRadians(entity.getYRot()));
            double cosTheta = Math.cos(Math.toRadians(entity.getXRot()));
            double sinTheta = Math.sin(Math.toRadians(entity.getXRot()));
            Vec3 origin = entity.position().add(xOffset* cosPsi- yOffset * sinTheta * sinPsi,yOffset * cosTheta,xOffset * sinPsi + yOffset * sinTheta * cosPsi);
            GatePortal gate = new GatePortal(world,entity);
            gate.setPos(origin);

            gate.setDamage(this.getDamage(spellLevel,entity));
            world.addFreshEntity(gate);
        }
        {double gate4Deg = Math.toRadians(135);
            double xOffset = Math.cos(gate4Deg) * radius;
            double yOffset = Math.sin(gate4Deg) * radius + 2;
            double cosPsi = Math.cos(Math.toRadians(entity.getYRot()));
            double sinPsi = Math.sin(Math.toRadians(entity.getYRot()));
            double cosTheta = Math.cos(Math.toRadians(entity.getXRot()));
            double sinTheta = Math.sin(Math.toRadians(entity.getXRot()));
            Vec3 origin = entity.position().add(xOffset* cosPsi- yOffset * sinTheta * sinPsi,yOffset * cosTheta,xOffset * sinPsi + yOffset * sinTheta * cosPsi);
            GatePortal gate = new GatePortal(world,entity);
            gate.setPos(origin);

            gate.setDamage(this.getDamage(spellLevel,entity));
            world.addFreshEntity(gate);
        }
        {double gate5Deg = Math.toRadians(170);
            double xOffset = Math.cos(gate5Deg) * radius;
            double yOffset = Math.sin(gate5Deg) * radius + 2;
            double cosPsi = Math.cos(Math.toRadians(entity.getYRot()));
            double sinPsi = Math.sin(Math.toRadians(entity.getYRot()));
            double cosTheta = Math.cos(Math.toRadians(entity.getXRot()));
            double sinTheta = Math.sin(Math.toRadians(entity.getXRot()));
            Vec3 origin = entity.position().add(xOffset* cosPsi- yOffset * sinTheta * sinPsi,yOffset * cosTheta,xOffset * sinPsi + yOffset * sinTheta * cosPsi);
            GatePortal gate = new GatePortal(world,entity);
            gate.setPos(origin);

            gate.setDamage(this.getDamage(spellLevel,entity));
            world.addFreshEntity(gate);
        }
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return spellLevel + ((float) (getSpellPower(spellLevel, entity) * 0.5));
    }

    private float getRadius(int spellLevel, LivingEntity entity){
        return (float) spellLevel / 2 + 3 * (float)Math.sqrt(getSpellPower(spellLevel,entity) * 0.25f / Math.PI);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }
}
