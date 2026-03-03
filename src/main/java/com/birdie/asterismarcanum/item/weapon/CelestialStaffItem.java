package com.birdie.asterismarcanum.item.weapon;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

// ripped from Crystal Chronicles
public class CelestialStaffItem extends AnimatedStaffItem implements IPresetSpellContainer {

    public final CelestialStaffItemDispatcher dispatcher;

    List<SpellData> spellData = null;
    SpellDataRegistryHolder[] spellDataRegistryHolders;

    public CelestialStaffItem(Properties pProperties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
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

    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (!ISpellContainer.isSpellContainer(itemStack)) {
            var spells = getSpells();
            var spellContainer = ISpellContainer.create(spells.size(), true, false).mutableCopy();
            spells.forEach(spellData -> spellContainer.addSpell(spellData.getSpell(), spellData.getLevel(), true));
            itemStack.set(ComponentRegistry.SPELL_CONTAINER, spellContainer.toImmutable());
        }
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
