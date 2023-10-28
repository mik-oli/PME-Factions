package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final Krolikcraft plugin;

    public InteractListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteractEvent(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
        if (!plugin.getClaimsManager().isChunkClaimed(block.getChunk())) return;

        Player player = event.getPlayer();
        Faction playerFaction = FactionsUtils.getPlayersFaction(plugin, player.getUniqueId());
        Faction claimFaction = plugin.getClaimsManager().getClaimOwner(block.getChunk());
        if (playerFaction == claimFaction) return;
        if (!playerFaction.getEnemies().contains(claimFaction)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("terrain-claimed"));
        }
    }
}
