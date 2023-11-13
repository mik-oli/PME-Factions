package com.github.mikoli.pmeutilities.listeners;

import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.ringsMechanic.Rings;
import com.github.mikoli.pmeutilities.ringsMechanic.RingsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class InvDragListener implements Listener {

    private final PMEUtilities plugin;

    public InvDragListener(PMEUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onInvDragEvent(InventoryDragEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCursor();
        RingsManager ringsManager = plugin.getRingsManager();

        if (itemStack == null || !ringsManager.isItemRing(itemStack)) return;

        Rings ring = ringsManager.getRingFromItem(itemStack);
        ringsManager.addPlayerRing(player, ring);
        ringsManager.playerAddEffect(player, ring);
    }
}
