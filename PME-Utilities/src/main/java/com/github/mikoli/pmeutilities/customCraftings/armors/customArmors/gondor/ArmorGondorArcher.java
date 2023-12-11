package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.gondor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorGondorArcher extends Armor {

    @Override
    public String getTag() {
        return "gondorArcher";
    }

    @Override
    public int getColorId() {
        return 6553855;
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
        return new String[] {"SD ", "   ", "   "};
    }

    @Override
    public ItemStack getHelmet() {
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm łucznika Gondoru");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik łucznika Gondoru");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice łucznika Gondoru");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty łucznika Gondoru");
    }
}
