package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.mordor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorMordorGoblin extends Armor {

    @Override
    public String getTag() {
        return "mordorGoblin";
    }

    @Override
    public int getColorId() {
        return 13107455;
    }

    @Override
    public Material getDye() {
        return Material.RED_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.LEATHER;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"SD ", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Gobliński hełm");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Gobliński napierśnik");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Goblińskie nogawice");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Goblińskie buty");
    }
}
