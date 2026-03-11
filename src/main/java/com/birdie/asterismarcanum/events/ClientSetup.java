package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothModel;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothRenderer;
import com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth.SummonedLunarMothModel;
import com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth.SummonedLunarMothRenderer;
import com.birdie.asterismarcanum.entity.spells.constellation.ConstellationRenderer;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlowRenderer;
import com.birdie.asterismarcanum.entity.spells.moonbeam.MoonbeamRenderer;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmRenderer;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireRenderer;
import com.birdie.asterismarcanum.entity.spells.tidal_lock.TidalLockRenderer;
import com.birdie.asterismarcanum.particle.*;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.render.AstralSeaEffects;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;


@EventBusSubscriber(modid = AsterismArcanum.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void  rendererRegister(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ASAREntityRegistry.STARFIRE_PROJECTILE.get(), StarfireRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.MOONBEAM.get(), MoonbeamRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.DARK_FLOW.get(), DarkFlowRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.STAR_SWARM_PROJECTILE.get(), StarSwarmRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.TIDAL_LOCK.get(), TidalLockRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.CONSTELLATION.get(), ConstellationRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.LUMINOUS_FLARE_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.LUNAR_MOTH.get(), context -> {return new LunarMothRenderer(context, new LunarMothModel());});
        event.registerEntityRenderer(ASAREntityRegistry.SUMMONED_LUNAR_MOTH.get(), context -> {return new SummonedLunarMothRenderer(context, new SummonedLunarMothModel());});
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AsterismArcanum.namespacePath("astral_sea"), new AstralSeaEffects());
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
        event.registerSpriteSet(ASARParticleRegistry.SIGNS_PARTICLE.get(), SignsParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.ALTSIGNS_PARTICLE.get(), AltsignsParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.PULSE_PARTICLE.get(), PulseParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.DELAYED_FIRST_PULSE_PARTICLE.get(), DelayedFirstPulseParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.DELAYED_SECOND_PULSE_PARTICLE.get(), DelayedSecondPulseParticle.Provider::new);
    }
}
