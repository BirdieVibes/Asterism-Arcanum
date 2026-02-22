package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.astromancer.AstromancerEntity;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = AsterismArcanum.MOD_ID)
public class CommonSetup {
    @SubscribeEvent
    public static void onAttributeCreateEvent(EntityAttributeCreationEvent event)
    {
        event.put(ASAREntityRegistry.ASTROMANCER_MAGE.get(), AstromancerEntity.createAttributes().build());
    }
}
