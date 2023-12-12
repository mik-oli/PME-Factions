package com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor;

import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorTypes;
import com.github.mikoli.pmeutilities.customCraftings.armors.ArmorUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorEreborAxeman extends Armor {

    @Override
    public String getTag() {
        return "ereborAxeMan";
    }

    @Override
    public int getColorId() {
        return 10486015;
    }

    @Override
    public Material getDye() {
        return Material.YELLOW_DYE;
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
        return ArmorUtils.getHelmet(this.getColorId(), this.getArmorType(), "Hełm topornika Ereboru");
    }

    @Override
    public ItemStack getChestplate() {
        return ArmorUtils.getChestplate(this.getColorId(), this.getArmorType(), "Napierśnik  topornika Ereboru");
    }

    @Override
    public ItemStack getLeggings() {
        return ArmorUtils.getLeggings(this.getColorId(), this.getArmorType(), "Nogawice  topornika Ereboru");
    }

    @Override
    public ItemStack getBoots() {
        return ArmorUtils.getBoots(this.getColorId(), this.getArmorType(), "Buty  topornika Ereboru");
    }
}
