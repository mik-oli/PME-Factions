package com.github.mikoli.pmeutilities.customCraftings.armors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Armor {

    public abstract String getTag();
    public abstract int getColorId();
    public abstract Material getDye();
    public abstract ArmorTypes getArmorType();
    public abstract ItemStack getHelmet();
    public abstract ItemStack getChestplate();
    public abstract ItemStack getLeggings();
    public abstract ItemStack getBoots();
}
