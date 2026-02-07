package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.spells.constellation.ConstellationRenderer;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlowRenderer;
import com.birdie.asterismarcanum.entity.spells.moonbeam.MoonbeamRenderer;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmRenderer;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireRenderer;
import com.birdie.asterismarcanum.entity.spells.tidal_lock.TidalLockRenderer;
import com.birdie.asterismarcanum.particle.NebulousDustParticle;
import com.birdie.asterismarcanum.particle.StardustParticle;
import com.birdie.asterismarcanum.particle.StarsParticle;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
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
        event.registerEntityRenderer(ASAREntityRegistry.STAR_SWARM_PROJECTILE.get(), StarSwarmRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.TIDAL_LOCK.get(), TidalLockRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.CONSTELLATION.get(), ConstellationRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE_1.get(), NebulousDustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE_2.get(), NebulousDustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE_3.get(), NebulousDustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE_4.get(), NebulousDustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE_5.get(), NebulousDustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.NEBULOUS_DUST_PARTICLE_6.get(), NebulousDustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.STARS_PARTICLE.get(), StarsParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.STARDUST_PARTICLE.get(), StardustParticle.Provider::new);
    }
}
