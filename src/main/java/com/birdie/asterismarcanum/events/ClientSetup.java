package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlowRenderer;
import com.birdie.asterismarcanum.entity.spells.ethereal_sphere.EtherealSphereRenderer;
import com.birdie.asterismarcanum.entity.spells.moonbeam.MoonbeamRenderer;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireRenderer;
import com.birdie.asterismarcanum.particle.NebulousDustParticle;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = AsterismArcanum.MOD_ID, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void  rendererRegister(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ASAREntityRegistry.STARFIRE_PROJECTILE.get(), StarfireRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.MOONBEAM.get(), MoonbeamRenderer::new);
//        event.registerEntityRenderer(ASAREntityRegistry.NEBULOUS_CONE_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.DARK_FLOW.get(), DarkFlowRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.ETHEREAL_SPHERE.get(), EtherealSphereRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE.get(), NebulousDustParticle.Provider::new);
    }
}
