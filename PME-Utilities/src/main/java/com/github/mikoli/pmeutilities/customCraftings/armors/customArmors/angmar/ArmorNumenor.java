package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.angmar;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import com.github.mikoli.pmeutilities.customCraftings.armors.DefensePoints;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorNumenor extends Armor {

    @Override
    public String getTag() {
        return "numenor";
    }

    @Override
    public int getColorId() {
        return 655615;
    }

    @Override
    public Material getDye() {
        return Material.BLUE_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.IRON;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"S D", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Nomenorski hełm");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Nomenorski napierśnik");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nomenorskie nogawice");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Nomenorskie buty");
    }
}
