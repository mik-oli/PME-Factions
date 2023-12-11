package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.angmar;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import com.github.mikoli.pmeutilities.customCraftings.armors.DefensePoints;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorGhost extends Armor {

    @Override
    public String getTag() {
        return "ghost";
    }

    @Override
    public int getColorId() {
        return 1966335;
    }

    @Override
    public Material getDye() {
        return Material.BLUE_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.DIAMOND;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"SD ", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm upiora");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik upiora");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice upiora");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty upiora");
    }
}
