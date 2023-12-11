package com.github.mikoli.pmeutilities.customCraftings;

import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.customCraftings.armors.Armor;
import com.github.mikoli.pmeutilities.customCraftings.armors.customArmors.ArmorGundabad;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;

public class CustomRecipes {

    private final String[] armorShape = {"SD ", "   ", "   "};
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
                recipe.shape(armorShape);
                recipe.setIngredient('S', armor.getArmorType().getHelmet());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
            if (armor.getChestplate() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".chestplate");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getChestplate());
                recipe.shape(armorShape);
                recipe.setIngredient('S', armor.getArmorType().getChestplate());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
            if (armor.getLeggings() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".leggings");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getLeggings());
                recipe.shape(armorShape);
                recipe.setIngredient('S', armor.getArmorType().getLeggings());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
            if (armor.getBoots() != null) {
                NamespacedKey key = new NamespacedKey(plugin, armor.getTag() + ".boots");
                ShapedRecipe recipe = new ShapedRecipe(key, armor.getBoots());
                recipe.shape(armorShape);
                recipe.setIngredient('S', armor.getArmorType().getBoots());
                recipe.setIngredient('D', armor.getDye());

                plugin.getServer().addRecipe(recipe);
            }
        }
    }

    private void registerArmors() {
        this.armors.add(new ArmorGundabad());
    }
}
