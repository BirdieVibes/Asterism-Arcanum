package com.birdie.asterismarcanum.events;


import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import com.birdie.asterismarcanum.capabilities.magic.AstralSeaManager;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;


@EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void astralFloating(PlayerSwimEvent event) {
        if (event.getEntity().level().dimension().equals(AstralSeaManager.ASTRAL_SEA)) event.setResult(EventResult.SUCCESS);
    }

    @SubscribeEvent
    public static void otherAstralFloating(EntityJoinLevelEvent event) {
        if (event.getEntity().level().dimension().equals(AstralSeaManager.ASTRAL_SEA)) {
            if ((event.getEntity().getType() != EntityType.PLAYER) && !event.getEntity().isNoGravity()) {
                event.getEntity().addTag("entity_in_astral_sea");
                event.getEntity().setNoGravity(true);
            }
        } else if (event.getEntity().getTags().contains("entity_in_astral_sea")){
            event.getEntity().setNoGravity(false);
            event.getEntity().removeTag("entity_in_astral_sea");
        }
    }
}