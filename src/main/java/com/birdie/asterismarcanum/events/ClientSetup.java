package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.astromancer.AstromancerRenderer;
import com.birdie.asterismarcanum.entity.spells.constellation.ConstellationRenderer;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlowRenderer;
import com.birdie.asterismarcanum.entity.spells.moonbeam.MoonbeamRenderer;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmRenderer;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireRenderer;
import com.birdie.asterismarcanum.entity.spells.tidal_lock.TidalLockRenderer;
import com.birdie.asterismarcanum.particle.*;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARMobEffectRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import io.redspace.ironsspellbooks.render.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import static io.redspace.ironsspellbooks.render.EnergySwirlLayer.CHARGE_TEXTURE;

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
        event.registerEntityRenderer(ASAREntityRegistry.ASTROMANCER_MAGE.get(), AstromancerRenderer::new);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, PlayerSkin.Model skinName) {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new EnergySwirlLayer.Vanilla(livingRenderer, CHARGE_TEXTURE, ASARMobEffectRegistry.LUNAR_CHANNELING));
        }
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
