package com.github.mikoli.pmeutilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum ItemLimiter {

    GOLDEN_APPLE(Material.GOLDEN_APPLE, 3),
    TNT(Material.TNT, 5);

    private final Material itemMaterial;
    private final int limit;

    ItemLimiter(Material itemMaterial, int limit) {
        this.itemMaterial = itemMaterial;
        this.limit = limit;
    }

    public Material getItemMaterial() {
        return itemMaterial;
    }

    public int getLimit() {
        return limit;
    }

    public static int getAmount(Player player, ItemLimiter item) {

        int x = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;
            if (itemStack.getType() == item.getItemMaterial()) x += itemStack.getAmount();
        }
        return x;
    }
}
