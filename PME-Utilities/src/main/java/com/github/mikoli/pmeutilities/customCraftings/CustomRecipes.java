package com.github.mikoli.pmeutilities.customCraftings;

import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.angmar.ArmorAngmar;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.angmar.ArmorGhost;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.angmar.ArmorGundabad;

import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.angmar.ArmorNumenor;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.elfs.ArmorNoldor;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.elfs.ArmorPrince;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.elfs.ArmorRivendel;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.elfs.ArmorSilvan;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor.ArmorEreborAxeman;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor.ArmorEreborLightInfantry;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor.ArmorEreborReconnoiterer;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.erebor.ArmorEreborWarrior;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.gondor.*;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.mordor.*;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;

public class CustomRecipes {

    private final PMEUtilities plugin;
    private final Set<Armor> armors = new HashSet<>();

    public CustomRecipes(PMEUtilities plugin) {
        this.plugin = plugin;
        this.registerArmors();
    }

    public void registerArmorsCrafting() {

        for (Armor armor : armors) {

            if (armor.getHelmet() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".helmet");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getHelmet());
                recipe.shape(armor.getCraftingShape());
                recipe.setIngredient('S', armor.getArmorType().getHelmet());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
            if (armor.getChestplate() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".chestplate");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getChestplate());
                recipe.shape(armor.getCraftingShape());
                recipe.setIngredient('S', armor.getArmorType().getChestplate());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
            if (armor.getLeggings() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".leggings");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getLeggings());
                recipe.shape(armor.getCraftingShape());
                recipe.setIngredient('S', armor.getArmorType().getLeggings());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
            if (armor.getBoots() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".boots");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getBoots());
                recipe.shape(armor.getCraftingShape());
                recipe.setIngredient('S', armor.getArmorType().getBoots());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
        }
    }

    private void registerArmors() {
        this.armors.add(new ArmorAngmar());
        this.armors.add(new ArmorGhost());
        this.armors.add(new ArmorGundabad());
        this.armors.add(new ArmorNumenor());
        this.armors.add(new ArmorNoldor());
        this.armors.add(new ArmorPrince());
        this.armors.add(new ArmorRivendel());
        this.armors.add(new ArmorSilvan());
        this.armors.add(new ArmorEreborAxeman());
        this.armors.add(new ArmorEreborLightInfantry());
        this.armors.add(new ArmorEreborReconnoiterer());
        this.armors.add(new ArmorEreborWarrior());
        this.armors.add(new ArmorGondorArcher());
        this.armors.add(new ArmorGondorCavalery());
        this.armors.add(new ArmorGondorInfantry());
        this.armors.add(new ArmorGuardsman());
        this.armors.add(new ArmorRenger());
        this.armors.add(new ArmorRochanInfantry());
        this.armors.add(new ArmorMordorGoblin());
        this.armors.add(new ArmorMordorHeavyOrc());
        this.armors.add(new ArmorMordorMoria());
        this.armors.add(new ArmorMordorSnagaOrc());
        this.armors.add(new ArmorMordorUrukhai());
    }
}
