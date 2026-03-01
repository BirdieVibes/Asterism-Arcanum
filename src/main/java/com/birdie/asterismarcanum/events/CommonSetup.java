package com.birdie.asterismarcanum.events;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.entity.mobs.astromancer.AstromancerEntity;
import com.birdie.asterismarcanum.entity.mobs.lunar_moth.LunarMothEntity;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = AsterismArcanum.MOD_ID)
public class CommonSetup {
    @SubscribeEvent
    public static void onAttributeCreateEvent(EntityAttributeCreationEvent event)
    {
        event.put(ASAREntityRegistry.ASTROMANCER_MAGE.get(), AstromancerEntity.createAttributes().build());
        event.put(ASAREntityRegistry.LUNAR_MOTH.get(), LunarMothEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event){

        event.register(ASAREntityRegistry.LUNAR_MOTH.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
