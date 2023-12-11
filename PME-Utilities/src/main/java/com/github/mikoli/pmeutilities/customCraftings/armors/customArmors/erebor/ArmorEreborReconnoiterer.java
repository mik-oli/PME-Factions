package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorEreborReconnoiterer extends Armor {

    @Override
    public String getTag() {
        return "ereborReconnoiterer";
    }

    @Override
    public int getColorId() {
        return 11796735;
    }

    @Override
    public Material getDye() {
        return Material.PINK_DYE;
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
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm zwiadowcy Ereboru");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik zwiadowcy Ereboru");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice zwiadowcy Ereboru");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty zwiadowcy Ereboru");
    }
}
