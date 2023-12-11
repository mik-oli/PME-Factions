package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.gondor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorRenger extends Armor {

    @Override
    public String getTag() {
        return "renger";
    }

    @Override
    public int getColorId() {
        return 8519935;
    }

    @Override
    public Material getDye() {
        return Material.WHITE_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.LEATHER;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"S D", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm rengera");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik rengera");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice rengera");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty rengera");
    }
}
