package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireRenderer;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = AsterismArcanum.MOD_ID, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void  rendererRegister(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ASAREntityRegistry.STARFIRE_PROJECTILE.get(), StarfireRenderer::new);
    }
}
