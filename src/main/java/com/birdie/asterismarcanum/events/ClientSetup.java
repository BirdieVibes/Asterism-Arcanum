package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.astromancer.AstromancerRenderer;
import com.birdie.asterismarcanum.entity.mobs.dragonfly.DragonflyModel;
import com.birdie.asterismarcanum.entity.mobs.dragonfly.DragonflyRenderer;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothModel;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothRenderer;
import com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth.SummonedLunarMothModel;
import com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth.SummonedLunarMothRenderer;
import com.birdie.asterismarcanum.entity.spells.celestial_tether.CelestialTetherRenderer;
import com.birdie.asterismarcanum.entity.spells.luminous_beam.LuminousBeamRenderer;
import com.birdie.asterismarcanum.entity.spells.piercing_light.PiercingLightRenderer;
import com.birdie.asterismarcanum.entity.spells.starcutter.StarcutterRenderer;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireRenderer;
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
        event.registerEntityRenderer(ASAREntityRegistry.STARCUTTER_PROJECTILE.get(), StarcutterRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.LUMINOUS_BEAM_PROJECTILE.get(), LuminousBeamRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.CELESTIAL_TETHER_ENTITY.get(), CelestialTetherRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.STAR_SWARM_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.PIERCING_LIGHT_PROJECTILE.get(), PiercingLightRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.ASTROMANCER.get(), AstromancerRenderer::new);
        event.registerEntityRenderer(ASAREntityRegistry.DRAGONFLY.get(), context -> {return new DragonflyRenderer(context, new DragonflyModel());});
        event.registerEntityRenderer(ASAREntityRegistry.LUNAR_MOTH.get(), context -> {return new LunarMothRenderer(context, new LunarMothModel());});
        event.registerEntityRenderer(ASAREntityRegistry.SUMMONED_LUNAR_MOTH.get(), context -> {return new SummonedLunarMothRenderer(context, new SummonedLunarMothModel());});
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AsterismArcanum.namespacePath("astral_sea"), new AstralSeaEffects());
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ASARParticleRegistry.STARS_PARTICLE.get(), StarsParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.STARDUST_PARTICLE.get(), StardustParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.SIGNS_PARTICLE.get(), SignsParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.ALTSIGNS_PARTICLE.get(), AltsignsParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.PULSE_PARTICLE.get(), PulseParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.STAR_CUT_PARTICLE.get(), StarCutParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.GATE_PARTICLE.get(), GateParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.DELAYED_FIRST_PULSE_PARTICLE.get(), DelayedFirstPulseParticle.Provider::new);
        event.registerSpriteSet(ASARParticleRegistry.DELAYED_SECOND_PULSE_PARTICLE.get(), DelayedSecondPulseParticle.Provider::new);
    }
}
