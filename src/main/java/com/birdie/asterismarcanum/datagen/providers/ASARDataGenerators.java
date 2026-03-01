package com.birdie.asterismarcanum.datagen.providers;

import com.birdie.asterismarcanum.AsterismArcanum;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = AsterismArcanum.MOD_ID)
public class ASARDataGenerators {
    @SubscribeEvent
    public static void gateData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput packOutput=generator.getPackOutput();
        ExistingFileHelper existingFileHelper=event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ASARDatapackProvider(packOutput, lookupProvider));

    }
}
