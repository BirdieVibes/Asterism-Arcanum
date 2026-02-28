package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = AsterismArcanum.MOD_ID)
public class ASARModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ASAREntityRegistry.LUNAR_MOTH.get(), LunarMothEntity.setAttributes());
    }


}
