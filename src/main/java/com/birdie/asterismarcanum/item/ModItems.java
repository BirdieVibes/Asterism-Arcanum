package com.birdie.asterismarcanum.item;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.armor.AstralMagicArmorItem;
import com.birdie.asterismarcanum.registries.ASARAttributeRegistry;
import com.birdie.asterismarcanum.registries.ASARUpgradeOrbTypeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AsterismArcanum.MOD_ID);

    //crafting MISC
    public static final DeferredItem<Item> STELLAR_NAUTILUS_SHELL = ITEMS
            .register("stellar_nautilus_shell", () -> new Item(new Item.Properties()));

    //School Resource
    public static final DeferredItem<Item> LIQUID_LUMINANCE = ITEMS
            .register("liquid_luminance", () -> new Item(new Item.Properties()));

    //I fogor
    public static  final DeferredItem<Item> SCROLL_ASTRAL = ITEMS
            .register("scroll_astral", () -> new Item(new Item.Properties()));

    //School Equipment
    public static final DeferredHolder<Item, Item> COSMIC_ATLAS = ITEMS.register(
            "cosmic_atlas",
            () -> new SpellBook(10).withSpellbookAttributes(
                    new AttributeContainer(
                            ASARAttributeRegistry.ASTRAL_SPELL_POWER, .15F,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new AttributeContainer(AttributeRegistry.MAX_MANA, 200,
                            AttributeModifier.Operation.ADD_VALUE)
            )
    );

    public static final DeferredHolder<Item, Item> ASTRAL_CROWN = ITEMS.register("astral_crown",
            () -> new AstralMagicArmorItem(
                    ArmorItem.Type.HELMET,
                    ItemPropertiesHelper
                            .equipment(1)
                            .durability(ArmorItem.Type.HELMET.getDurability(37))
            )
    );

    public static final DeferredHolder<Item, Item> ASTRAL_CHESTPLATE = ITEMS.register(
            "astral_chestplate",
            () -> new AstralMagicArmorItem(
                    ArmorItem.Type.CHESTPLATE,
                    ItemPropertiesHelper
                            .equipment(1)
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(37))
            )
    );

    public static final DeferredHolder<Item, Item> ASTRAL_LEGGINGS = ITEMS.register(
            "astral_leggings",
            () -> new AstralMagicArmorItem(
                    ArmorItem.Type.LEGGINGS,
                    ItemPropertiesHelper
                            .equipment(1)
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(37))
            )
    );

    public static final DeferredHolder<Item, Item> ASTRAL_BOOTS = ITEMS.register(
            "astral_boots",
            () -> new AstralMagicArmorItem(
                    ArmorItem.Type.BOOTS,
                    ItemPropertiesHelper
                            .equipment(1)
                            .durability(ArmorItem.Type.BOOTS.getDurability(37))
            )
    );

    //School Crafting
    public static final DeferredHolder<Item, Item> ASTRAL_RUNE = ITEMS.register(
            "astral_rune", () -> new Item(ItemPropertiesHelper.material())
    );

    public static final DeferredHolder<Item, Item> ASTRAL_UPGRADE_ORB = ITEMS.register("astral_upgrade_orb",
            () -> new UpgradeOrbItem(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON).component(ComponentRegistry.UPGRADE_ORB_TYPE,
                    ASARUpgradeOrbTypeRegistry.ASTRAL_SPELL_POWER)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getASARItems()
    {
        return ITEMS.getEntries();
    }
}
