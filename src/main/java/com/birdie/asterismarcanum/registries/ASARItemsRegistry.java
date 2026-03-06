package com.birdie.asterismarcanum.registries;

import com.birdie.asterismarcanum.AsterismArcanum;
import com.birdie.asterismarcanum.item.armor.AstralMagicArmorItem;
import com.birdie.asterismarcanum.item.weapon.ASARStaffTiers;
import com.birdie.asterismarcanum.item.weapon.AnimatedSwordItem;
import com.birdie.asterismarcanum.item.weapon.CelestialStaffItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;


public class ASARItemsRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AsterismArcanum.MOD_ID);

    //MISC
    public static final DeferredItem<Item> STELLAR_NAUTILUS_SHELL = ITEMS
            .register("stellar_nautilus_shell", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> STELLAR_COSMOS = ITEMS
            .register("stellar_cosmos", () -> new ItemNameBlockItem(ASARModBlocksRegistry.STELLAR_COSMOS_FLOWERS.get(), new Item.Properties()));

    public static final Supplier<DeferredSpawnEggItem> LUNAR_MOTH_SPAWN_EGG = ITEMS
            .register("lunar_moth_spawn_egg", () -> new DeferredSpawnEggItem(ASAREntityRegistry.LUNAR_MOTH, 1341255, 6666054, ItemPropertiesHelper.material().stacksTo(64)));

    // Astral School
    public static final DeferredItem<Item> LIQUID_LUMINANCE_BOTTLE = ITEMS
            .register("liquid_luminance_bottle", () -> new Item(new Item.Properties()));

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

    public static final DeferredHolder<Item, Item> CELESTIAL_STAFF = ITEMS.register("celestial_staff",
            () -> new CelestialStaffItem(ItemPropertiesHelper.equipment(1).attributes(AnimatedSwordItem.createAttributes(ASARStaffTiers.CELESTIAL_STAFF)).rarity(Rarity.UNCOMMON),
                    SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistries.STARFIRE, 11))));


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
}
