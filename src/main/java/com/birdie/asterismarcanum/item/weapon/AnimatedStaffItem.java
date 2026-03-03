package com.birdie.asterismarcanum.item.weapon;

import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.IronsWeaponTier;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

// ripped from Crystal Chronicles
public class AnimatedStaffItem extends StaffItem {

    public AnimatedStaffItem(Properties pProperties) {
        super(pProperties);
    }

    public static ItemAttributeModifiers createAttributes(IronsWeaponTier pTier) {
        var builder = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, pTier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, pTier.getSpeed(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                );
        for (AttributeContainer holder : pTier.getAdditionalAttributes()) {
            builder.add(holder.attribute(), holder.createModifier(EquipmentSlot.MAINHAND.getName()), EquipmentSlotGroup.MAINHAND);
        }
        return builder.build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        SpellSelectionManager spellSelectionManager = new SpellSelectionManager(player);
        SpellSelectionManager.SelectionOption selectionOption = spellSelectionManager.getSelection();
        if (selectionOption == null || selectionOption.spellData.equals(SpellData.EMPTY)) {
            return InteractionResultHolder.pass(itemStack);
        }
        SpellData spellData = selectionOption.spellData;
        int spellLevel = spellData.getSpell().getLevelFor(spellData.getLevel(), player);
        if (level.isClientSide()) {
            if (ClientMagicData.isCasting()) {
                return InteractionResultHolder.consume(itemStack);
            } else if (ClientMagicData.getPlayerMana() < spellData.getSpell().getManaCost(spellLevel)
                    || ClientMagicData.getCooldowns().isOnCooldown(spellData.getSpell())
                    || !ClientMagicData.getSyncedSpellData(player).isSpellLearned(spellData.getSpell())) {
                return InteractionResultHolder.pass(itemStack);
            } else {
                return InteractionResultHolder.consume(itemStack);
            }
        }

        var castingSlot = hand.ordinal() == 0 ? SpellSelectionManager.MAINHAND : SpellSelectionManager.OFFHAND;

        if (spellData.getSpell().attemptInitiateCast(itemStack, spellLevel, level, player, selectionOption.getCastSource(), true, castingSlot)) {
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
