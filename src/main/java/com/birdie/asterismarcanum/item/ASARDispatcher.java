package com.birdie.asterismarcanum.item;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class ASARDispatcher {
    private static final AzCommand IDLE_COMMAND;

    public void idle(Entity entity, ItemStack itemStack) {
        IDLE_COMMAND.sendForItem(entity, itemStack);
    }

    static {
        IDLE_COMMAND = AzCommand.create("base_controller", "idle", AzPlayBehaviors.LOOP);
    }
}
