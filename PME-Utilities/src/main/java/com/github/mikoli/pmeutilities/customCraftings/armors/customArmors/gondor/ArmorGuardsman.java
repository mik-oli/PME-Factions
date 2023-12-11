package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.gondor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorGuardsman extends Armor {

    @Override
    public String getTag() {
        return "guardsman";
    }

    @Override
    public int getColorId() {
        return 5243135;
    }

    @Override
    public Material getDye() {
        return Material.WHITE_DYE;
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
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm gwardzisty");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik gwardzisty");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice gwardzisty");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty gwardzisty");
    }
}
