package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.gondor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorGondorCavalery extends Armor {

    @Override
    public String getTag() {
        return "gondorCavalery";
    }

    @Override
    public int getColorId() {
        return 5898495;
    }

    @Override
    public Material getDye() {
        return Material.WHITE_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.IRON;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"SD ", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm jazdy Gondoru");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik jazdy Gondoru");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice jazdy Gondoru");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty jazdy Gondoru");
    }
}
