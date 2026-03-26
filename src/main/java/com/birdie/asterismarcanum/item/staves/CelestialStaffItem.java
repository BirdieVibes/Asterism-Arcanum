package com.birdie.asterismarcanum.item.staves;

import com.birdie.asterismarcanum.item.ASARDispatcher;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.items.staves.ImbueableStaffItem;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CelestialStaffItem extends ImbueableStaffItem {
    public final ASARDispatcher dispatcher = new ASARDispatcher();

    public CelestialStaffItem() {
        super (ItemPropertiesHelper.equipment(1).fireResistant().rarity(ASRarities.COSMIC_RARITY_PROXY.getValue()).attributes(ExtendedSwordItem.createAttributes(ASARStaffTier.CELESTIAL_STAFF))
        );
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            this.dispatcher.idle(player, stack);
        }
    }
}
