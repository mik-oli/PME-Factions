package com.github.mikoli.pmeutilities.listeners;

import com.github.mikoli.pmeutilities.ItemLimiter;
import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPickUpListener implements Listener {

    private final PMEUtilities plugin;

    public ItemPickUpListener(PMEUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onItemPickUpEvent(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack item = event.getItem().getItemStack();

        if (player.hasPermission(Permissions.BYPASS_ITEM_LIMIT.getPermission())) return;

        for (ItemLimiter limitedItem : ItemLimiter.values()) {
            if (limitedItem.getItemMaterial() == item.getType()) {
                if (item.getAmount() > (limitedItem.getLimit() - ItemLimiter.getAmount(player, limitedItem))) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
