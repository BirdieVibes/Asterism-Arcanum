package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;

import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.birdie.asterismarcanum.entity.mobs.summoned_lunar_moth.SummonedLunarMothEntity;
import com.birdie.asterismarcanum.entity.spells.brightburst.BrightburstEntity;
import com.birdie.asterismarcanum.entity.spells.luminous_beam.LuminousBeamProjectile;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmProjectile;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireProjectile;
import com.birdie.asterismarcanum.entity.spells.tidal_lock.TidalLockEntity;
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

    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<StarfireProjectile>> STARFIRE_PROJECTILE =
            ENTITIES.register("starfire", () -> EntityType.Builder.<StarfireProjectile>of(StarfireProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("starfire").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<LuminousBeamProjectile>> LUMINOUS_BEAM_PROJECTILE =
            ENTITIES.register("luminous_beam", () -> EntityType.Builder.<LuminousBeamProjectile>of(LuminousBeamProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "luminous_beam").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<StarSwarmProjectile>> STAR_SWARM_PROJECTILE =
            ENTITIES.register("star_swarm", () -> EntityType.Builder.<StarSwarmProjectile>of(StarSwarmProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "star_swarm").toString()));


    // Mobs
    public static final DeferredHolder<EntityType<?>, EntityType<LunarMothEntity>> LUNAR_MOTH =
            ENTITIES.register("lunar_moth", () -> EntityType.Builder.<LunarMothEntity>of
                            (LunarMothEntity::new, MobCategory.CREATURE).
                    sized(0.8f, 0.8f)
                    .build(
                            ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "lunar_moth").toString()
                    ));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonedLunarMothEntity>> SUMMONED_LUNAR_MOTH =
            ENTITIES.register("summoned_lunar_moth", () -> EntityType.Builder.<SummonedLunarMothEntity>of
                            (SummonedLunarMothEntity::new, MobCategory.CREATURE).
                    sized(0.8f, 0.8f)
                    .clientTrackingRange(64)
                    .build(
                            ResourceLocation.fromNamespaceAndPath(AsterismArcanum.MOD_ID, "summoned_lunar_moth").toString()
                    ));

    // Particle Entities and AOES

    public static final DeferredHolder<EntityType<?>, EntityType<BrightburstEntity>> BRIGHTBURST =
            ENTITIES.register("brightburst_entity", () -> EntityType.Builder.<BrightburstEntity>of(BrightburstEntity::new, MobCategory.MISC)
                    .sized(11, 11)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("brightburst").toString()));

    // Mounts
    public static final DeferredHolder<EntityType<?>, EntityType<TidalLockEntity>> TIDAL_LOCK =
        ENTITIES.register("tidal_lock", () -> EntityType.Builder.<TidalLockEntity>of(TidalLockEntity::new, MobCategory.MISC)
                .sized(1f, 1f)
                .clientTrackingRange(64)
                .build(AsterismArcanum.namespacePath("tidal_lock").toString()));

    public static void register(IEventBus eventBus) { ENTITIES.register(eventBus); }
}
