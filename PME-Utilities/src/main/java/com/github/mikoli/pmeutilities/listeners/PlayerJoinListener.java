package com.github.mikoli.pmeutilities.listeners;

import com.github.mikoli.pmeutilities.PMEUtilities;
import com.github.mikoli.pmeutilities.ringsMechanic.Rings;
import com.github.mikoli.pmeutilities.ringsMechanic.RingsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    private final PMEUtilities plugin;

    public PlayerJoinListener(PMEUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(EquipmentSlot.OFF_HAND);
        RingsManager ringsManager = plugin.getRingsManager();

        if (!ringsManager.isItemRing(itemStack)) return;
        if (ringsManager.hasPlayerRing(player)) return;

        Rings ring = ringsManager.getRingFromItem(itemStack);
        ringsManager.addPlayerRing(player, ring);
        ringsManager.playerAddEffect(player, ring);
    }
}
