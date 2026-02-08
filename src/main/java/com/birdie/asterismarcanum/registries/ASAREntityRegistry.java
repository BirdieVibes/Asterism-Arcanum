package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;

import com.birdie.asterismarcanum.entity.spells.constellation.Constellation;
import com.birdie.asterismarcanum.entity.spells.dark_flow.DarkFlow;
import com.birdie.asterismarcanum.entity.spells.star_swarm.StarSwarmProjectile;
import com.birdie.asterismarcanum.entity.spells.starfire.StarfireProjectile;
import com.birdie.asterismarcanum.entity.spells.moonbeam.MoonbeamEntity;
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

    public static final DeferredHolder<EntityType<?>, EntityType<StarSwarmProjectile>> STAR_SWARM_PROJECTILE =
            ENTITIES.register("star_swarm", () -> EntityType.Builder.<StarSwarmProjectile>of(StarSwarmProjectile::new, MobCategory.MISC)
                    .sized(4f, 4f)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("star_swarm").toString()));

    // Particle Entities and AOES
    public static final DeferredHolder<EntityType<?>, EntityType<MoonbeamEntity>> MOONBEAM =
            ENTITIES.register("moonbeam", () -> EntityType.Builder.<MoonbeamEntity>of(MoonbeamEntity::new, MobCategory.MISC)
                    .sized(1.5f, 14f)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("moonbeam").toString()));

//    public static final DeferredHolder<EntityType<?>, EntityType<NebulousConeProjectile>> NEBULOUS_CONE_PROJECTILE =
//            ENTITIES.register("nebulous_cone", () -> EntityType.Builder.<NebulousConeProjectile>of(NebulousConeProjectile::new, MobCategory.MISC)
//                    .sized(1f, 1f)
//                    .clientTrackingRange(64)
//                    .build(AsterismArcanum.namespacePath("nebulous_cone").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<DarkFlow>> DARK_FLOW =
            ENTITIES.register("black_hole", () -> EntityType.Builder.<DarkFlow>of(DarkFlow::new, MobCategory.MISC)
                    .sized(11, 11)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("dark_flow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<Constellation>> CONSTELLATION =
            ENTITIES.register("constellation", () -> EntityType.Builder.<Constellation>of(Constellation::new, MobCategory.MISC)
                    .sized(1, 1)
                    .clientTrackingRange(64)
                    .build(AsterismArcanum.namespacePath("constellation").toString()));

    // Mounts
    public static final DeferredHolder<EntityType<?>, EntityType<TidalLockEntity>> TIDAL_LOCK =
        ENTITIES.register("tidal_lock", () -> EntityType.Builder.<TidalLockEntity>of(TidalLockEntity::new, MobCategory.MISC)
                .sized(1f, 1f)
                .clientTrackingRange(64)
                .build(AsterismArcanum.namespacePath("tidal_lock").toString()));

    public static void register(IEventBus eventBus) { ENTITIES.register(eventBus); }
}
