package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.elfs;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import com.github.mikoli.pmeutilities.customCraftings.armors.DefensePoints;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorNoldor extends Armor {

    @Override
    public String getTag() {
        return "noldor";
    }

    @Override
    public int getColorId() {
        return 3277055;
    }

    @Override
    public Material getDye() {
        return Material.GREEN_DYE;
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
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Noldorski hełm");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Noldorski napierśnik");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Noldorskie nogawice");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Noldorskie buty");
    }
}
