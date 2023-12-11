package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorEreborWarrior extends Armor {

    @Override
    public String getTag() {
        return "ereborWarrior";
    }

    @Override
    public int getColorId() {
        return 11141375;
    }

    @Override
    public Material getDye() {
        return Material.PINK_DYE;
    }

    @Override
    public ArmorTypes getArmorType() {
        return ArmorTypes.DIAMOND;
    }

    @Override
    public String[] getCraftingShape() {
        return new String[] {"S D", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm wojownika Ereboru");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik wojownika Ereboru");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice wojownika Ereboru");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty wojownika Ereboru");
    }
}
