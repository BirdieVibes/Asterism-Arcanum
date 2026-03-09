package com.birdie.asterismarcanum.events;


import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import com.birdie.asterismarcanum.capabilities.magic.AstralSeaManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;


@EventBusSubscriber
public class ServerEvents {
    @SubscribeEvent
    public static void astralFloating(PlayerSwimEvent event) {
        if (event.getEntity().level().dimension().equals(AstralSeaManager.ASTRAL_SEA)) event.setResult(EventResult.SUCCESS);
    }
}