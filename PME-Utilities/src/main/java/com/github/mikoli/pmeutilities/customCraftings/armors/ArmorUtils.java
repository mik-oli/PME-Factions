package com.github.mikoli.pmeutilities.customCraftings.armors;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

public class ArmorUtils {

    public static ItemStack getHelmet(int colorId, ArmorTypes armorType, String name) {
        return createArmorItem(Material.LEATHER_HELMET, colorId, DefensePoints.valueOf(armorType.name()).getHelmet(), EquipmentSlot.HEAD, name);
    }

    public static ItemStack getChestplate(int colorId, ArmorTypes armorType, String name) {
        return createArmorItem(Material.LEATHER_CHESTPLATE, colorId, DefensePoints.valueOf(armorType.name()).getChestplate(), EquipmentSlot.CHEST, name);
    }

    public static ItemStack getLeggings(int colorId, ArmorTypes armorType, String name) {
        return createArmorItem(Material.LEATHER_LEGGINGS, colorId, DefensePoints.valueOf(armorType.name()).getLeggings(), EquipmentSlot.LEGS, name);
    }

    public static ItemStack getBoots(int colorId, ArmorTypes armorType, String name) {
        return createArmorItem(Material.LEATHER_BOOTS, colorId, DefensePoints.valueOf(armorType.name()).getBoots(), EquipmentSlot.FEET, name);
    }

    private static ItemStack createArmorItem(Material material, int colorId, int defencePoints, EquipmentSlot slot, String name) {

        ItemStack armor = new ItemStack(material);
        ItemMeta itemMeta = armor.getItemMeta();

        AttributeModifier newAttribute = new AttributeModifier(UUID.randomUUID(), "generic.armor", defencePoints, AttributeModifier.Operation.ADD_NUMBER, slot);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, newAttribute);

        itemMeta.setDisplayName(name);

        armor.setItemMeta(itemMeta);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) armor.getItemMeta();
        leatherArmorMeta.setColor(Color.fromRGB(colorId));
        armor.setItemMeta(leatherArmorMeta);

        return armor;
    }
}
