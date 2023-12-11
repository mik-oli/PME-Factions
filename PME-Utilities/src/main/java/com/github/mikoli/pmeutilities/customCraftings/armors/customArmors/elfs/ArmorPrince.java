package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.elfs;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import com.github.mikoli.pmeutilities.customCraftings.armors.DefensePoints;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorPrince extends Armor {

    @Override
    public String getTag() {
        return "prince";
    }

    @Override
    public int getColorId() {
        return 2621695;
    }

    @Override
    public Material getDye() {
        return Material.GREEN_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.GOLD;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"SD ", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm księcia");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik księcia");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice księcia");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty księcia");
    }
}
