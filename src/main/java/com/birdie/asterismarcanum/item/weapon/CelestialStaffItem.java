package com.birdie.asterismarcanum.item.weapon;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

// ripped from Crystal Chronicles
public class CelestialStaffItem extends AnimatedStaffItem {

    public final CelestialStaffItemDispatcher dispatcher;

    List<SpellData> spellData = null;
    SpellDataRegistryHolder[] spellDataRegistryHolders;

    public CelestialStaffItem(Properties pProperties) {
        super(pProperties);
        this.spellDataRegistryHolders = spellDataRegistryHolders;
        this.dispatcher = new CelestialStaffItemDispatcher();
    }

    public List<SpellData> getSpells() {
        if (spellData == null) {
            spellData = Arrays.stream(spellDataRegistryHolders).map(SpellDataRegistryHolder::getSpellData).toList();
            spellDataRegistryHolders = null;
        }
        return spellData;
    }

    /*
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (livingEntity instanceof Player player && !level.isClientSide()) {
            // This is where you now trigger an animation to play
            dispatcher.idle(player, stack);
        }
     */

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (entity instanceof Player player && !level.isClientSide()) {
            dispatcher.idle(player, stack);
        }

    }
}
