package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;

import com.birdie.asterismarcanum.entity.mobs.dragonfly.DragonflyEntity;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth.SummonedLunarMothEntity;
import com.birdie.asterismarcanum.entity.spells.celestial_tether.CelestialTetherEntity;
import com.birdie.asterismarcanum.entity.spells.luminous_beam.LuminousBeamProjectile;
import com.birdie.asterismarcanum.entity.spells.piercing_light.PiercingLightProjectile;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmProjectile;
import com.birdie.asterismarcanum.entity.spells.starcutter.StarcutterEntity;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ASAREntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, AsterismArcanum.MOD_ID);

// Projectiles ========================================================

    // simple missile, has the richochet ability and 2 piercing
    public static final DeferredHolder<EntityType<?>, EntityType<StarfireProjectile>> STARFIRE_PROJECTILE =
            ENTITIES.register("starfire", () -> EntityType.Builder.<StarfireProjectile>of(StarfireProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("starfire").toString()));

    // this is a single section of the electrocute beam particle, very long, happens in a cone-type spell
    public static final DeferredHolder<EntityType<?>, EntityType<LuminousBeamProjectile>> LUMINOUS_BEAM_PROJECTILE =
            ENTITIES.register("luminous_beam", () -> EntityType.Builder.<LuminousBeamProjectile>of(LuminousBeamProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "luminous_beam").toString()));

    // this is a cone spell that rather than damaging entities in the cone, summons entities around your head that work as turrets (uses piercing light projectiles)
    public static final DeferredHolder<EntityType<?>, EntityType<StarSwarmProjectile>> STAR_SWARM_PROJECTILE =
            ENTITIES.register("star_swarm", () -> EntityType.Builder.<StarSwarmProjectile>of(StarSwarmProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "star_swarm").toString()));

    // this is a "reskinned" blood needle entity
    public static final DeferredHolder<EntityType<?>, EntityType<PiercingLightProjectile>> PIERCING_LIGHT_PROJECTILE =
            ENTITIES.register("piercing_light", () -> EntityType.Builder.<PiercingLightProjectile>of(PiercingLightProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "piercing_light").toString()));


// Mobs ===========================================================

    // spawn on mountains, non-aggressive, do or will drop moth scales to create liquid luminance
    public static final DeferredHolder<EntityType<?>, EntityType<LunarMothEntity>> LUNAR_MOTH =
            ENTITIES.register("lunar_moth", () -> EntityType.Builder.<LunarMothEntity>of
                            (LunarMothEntity::new, MobCategory.CREATURE).
                    sized(0.8f, 0.8f)
                    .build(
                            ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "lunar_moth").toString()
                    ));

    public static final DeferredHolder<EntityType<?>, EntityType<DragonflyEntity>> DRAGONFLY =
            ENTITIES.register("dragonfly", () -> EntityType.Builder.<DragonflyEntity>of
                            (DragonflyEntity::new, MobCategory.CREATURE).
                    sized(1f, 1f)
                    .build(
                            ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "dragonfly").toString()
                    ));

// Particle Entities and AOES ===================================

    public static final DeferredHolder<EntityType<?>, EntityType<StarcutterEntity>> STARCUTTER_PROJECTILE =
            ENTITIES.register("starcutter_entity", () -> EntityType.Builder.<StarcutterEntity>of(StarcutterEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("starcutter").toString()));

// Mounts ===================================================================

    // extends lunar moths, 1 gigantic moth u can ride :)
    public static final DeferredHolder<EntityType<?>, EntityType<SummonedLunarMothEntity>> SUMMONED_LUNAR_MOTH =
            ENTITIES.register("summoned_lunar_moth", () -> EntityType.Builder.<SummonedLunarMothEntity>of
                            (SummonedLunarMothEntity::new, MobCategory.CREATURE).
                    sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "summoned_lunar_moth").toString()
                    ));

    public static final DeferredHolder<EntityType<?>, EntityType<CelestialTetherEntity>> CELESTIAL_TETHER_ENTITY =
            ENTITIES.register("celestial_tether_entity", () -> EntityType.Builder.<CelestialTetherEntity>of
                            (CelestialTetherEntity::new, MobCategory.MISC).
                    sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "celestial_tether_entity").toString()
                    ));

    public static void register(IEventBus eventBus) { ENTITIES.register(eventBus); }
}
