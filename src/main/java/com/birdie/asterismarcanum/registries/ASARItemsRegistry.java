package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.EtherealTalisman;
import com.birdie.asterismarcanum.item.armor.AstralMagicArmorItem;
import com.birdie.asterismarcanum.item.curios.spellbooks.astrolabe.AstrolabeSpellBook;
import com.birdie.asterismarcanum.item.staves.CelestialStaffItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

// this is broken and i dont know why
public class ASARItemsRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AsterismArcanum.MOD_ID);

    //MISC

    public static final DeferredHolder<Item, Item> ARCANE_TALISMAN = ITEMS.register("ethereal_talisman", () -> new EtherealTalisman(ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC)));

    public static final DeferredHolder<Item, Item> LUMINE_DUST = ITEMS.registerSimpleItem("lumine_dust");

    public static final Supplier<DeferredSpawnEggItem> DRAGONFLY_SPAWN_EGG = ITEMS
            .register("dragonfly_spawn_egg", () -> new DeferredSpawnEggItem(ASAREntityRegistry.DRAGONFLY, 1001255, 2666054, ItemPropertiesHelper.material().stacksTo(64)));

    public static final Supplier<DeferredSpawnEggItem> LUNAR_MOTH_SPAWN_EGG = ITEMS
            .register("lunar_moth_spawn_egg", () -> new DeferredSpawnEggItem(ASAREntityRegistry.LUNAR_MOTH, 1541255, 8666054, ItemPropertiesHelper.material().stacksTo(64)));

    // Astral School

    public static final DeferredHolder<Item, Item> ASTRAL_HELMET = ITEMS.register("astral_helmet",
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

    public static final DeferredHolder<Item, Item> CELESTIAL_STAFF = ITEMS.register("celestial_staff", CelestialStaffItem::new);
    public static final DeferredHolder<Item, Item> ASTROLABE = ITEMS.register("astrolabe", AstrolabeSpellBook::new);

    //School Crafting
    public static final DeferredHolder<Item, Item> ASTRAL_RUNE = ITEMS.registerSimpleItem("astral_rune");

    public static final DeferredHolder<Item, Item> DRAGONFLY_WINGS = ITEMS.registerSimpleItem("dragonfly_wings");


    public static final DeferredHolder<Item, Item> ASTRAL_UPGRADE_ORB = ITEMS.register("astral_upgrade_orb",
            () -> new UpgradeOrbItem(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON).component(ComponentRegistry.UPGRADE_ORB_TYPE,
                    ASARUpgradeOrbTypeRegistry.ASTRAL_SPELL_POWER)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
