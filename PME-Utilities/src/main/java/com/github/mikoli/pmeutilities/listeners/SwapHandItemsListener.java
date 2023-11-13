package com.github.mikoli.pmeutilities.listeners;

import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.ringsMechanic.Rings;
import com.github.mikoli.pmeutilities.ringsMechanic.RingsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class SwapHandItemsListener implements Listener {

    private PMEUtilities plugin;

    public SwapHandItemsListener(PMEUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private  void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getOffHandItem();
        RingsManager ringsManager = plugin.getRingsManager();

        if (itemStack == null || !ringsManager.isItemRing(itemStack)) return;

        Rings ring = ringsManager.getRingFromItem(itemStack);
        ringsManager.addPlayerRing(player, ring);
        ringsManager.playerAddEffect(player, ring);
    }
}
