package com.birdie.asterismarcanum.events;


import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import com.birdie.asterismarcanum.capabilities.magic.AstralSeaManager;
import com.birdie.asterismarcanum.entity.spells.celestial_tether.CelestialTetherEntity;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void astralFloating(PlayerSwimEvent event) {
        if (event.getEntity().level().dimension().equals(AstralSeaManager.ASTRAL_SEA))
            event.setResult(EventResult.SUCCESS);
    }

    @SubscribeEvent
    public static void otherAstralFloating(EntityJoinLevelEvent event) {
        if (event.getEntity().level().dimension().equals(AstralSeaManager.ASTRAL_SEA)) {
            if ((event.getEntity().getType() != EntityType.PLAYER) && !event.getEntity().isNoGravity()) {
                event.getEntity().addTag("entity_in_astral_sea");
                event.getEntity().setNoGravity(true);
            }
        } else if (event.getEntity().getTags().contains("entity_in_astral_sea")) {
            event.getEntity().setNoGravity(false);
            event.getEntity().removeTag("entity_in_astral_sea");
        }
    }

    @SubscribeEvent
    public static void onTetherIncomingDamage(LivingIncomingDamageEvent event) {
        var tetheredEntity = event.getEntity();

        if (event.getSource().getEntity() != null && tetheredEntity.getVehicle() instanceof CelestialTetherEntity celestialTetherEntity && !DamageSources.isFriendlyFireBetween(event.getSource().getEntity(), tetheredEntity)) {
            event.setCanceled(true);
            celestialTetherEntity.subtractAbsorbedHits();
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        var target = event.getEntity();

        if (target.hasEffect(MobEffects.LUCK) && target.getTags().contains("silvery_barbs_tag")) {
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10, 1));
            event.setCanceled(true);
        } else if (target.getTags().contains("silvery_barbs_tag") && !target.hasEffect(MobEffects.LUCK)) {
            target.removeTag("silvery_barbs_tag");
        }

    }
}