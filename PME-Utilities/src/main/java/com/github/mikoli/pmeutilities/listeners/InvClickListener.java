package com.github.mikoli.pmeutilities.listeners;

import com.github.mikoli.pmeutilities.ItemLimiter;
import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.Permissions;
import com.github.mikoli.pmeutilities.ringsMechanic.Rings;
import com.github.mikoli.pmeutilities.ringsMechanic.RingsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvClickListener implements Listener {

    private final PMEUtilities plugin;

    public InvClickListener(PMEUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onInvClickEvent(InventoryClickEvent event) {

        ringsLogic(event);
        itemLimiter(event);
    }

    private void ringsLogic(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot != 40) return;

        ItemStack itemStack = event.getCursor();
        RingsManager ringsManager = plugin.getRingsManager();
        if (itemStack == null || !ringsManager.isItemRing(itemStack)) return;

        Rings ring = ringsManager.getRingFromItem(itemStack);
        ringsManager.addPlayerRing(player, ring);
        ringsManager.playerAddEffect(player, ring);
    }

    private void itemLimiter(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = null;

        if (player.hasPermission(Permissions.BYPASS_ITEM_LIMIT.getPermission())) return;

        if (inventory.getHolder() == player) item = event.getCursor();
        else if (inventory.getHolder() != player && event.getClick().isShiftClick()) inventory.getItem(event.getSlot());
        if (item == null) return;

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
