package com.birdie.asterismarcanum.spells;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmProjectile;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class StarSwarmSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "star_swarm");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ASARSchoolRegistry.ASTRAL_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(15)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.projectile_count", (int) (getRecastCount(spellLevel, caster)))
        );
    }

    public StarSwarmSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 40;
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
        var recasts = playerMagicData.getPlayerRecasts();
        if (!recasts.hasRecastForSpell(getSpellId())) {
            recasts.addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 200, castSource, null), playerMagicData);
        }
        Vec3 origin = entity.getEyePosition().add(entity.getForward().normalize().scale(.2f)).subtract(0, -2, 0);
        StarSwarmProjectile star_swarm = new StarSwarmProjectile(level, entity);
        star_swarm.setPos(origin.subtract(0, star_swarm.getBbHeight(), 0));
        Vec3 vec = entity.getForward().add(0,0.2,0).normalize(); // adjust for inaccuracy sometimes hitting the ground
        star_swarm.shoot(vec.scale(.7f));
        star_swarm.setDamage(getDamage(spellLevel, entity));
        star_swarm.setCursorHoming(true);
        level.addFreshEntity(star_swarm);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel, caster);
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new MultiTargetEntityCastData();
    }
}
