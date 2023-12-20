package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final PMEFactions plugin;

    public InteractListener(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteractEvent(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
        if (block == null) return;
        if (!plugin.getClaimsManager().isChunkClaimed(block.getChunk())) return;

        Player player = event.getPlayer();
        Faction playerFaction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
        if (playerFaction == null) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-interact"));
            return;
        }
        Faction claimFaction = plugin.getFactionsManager().getFactionsList().get(plugin.getClaimsManager().getClaimsList().get(plugin.getClaimsManager().getClaimId(block.getChunk())).getClaimOwner());
        if (playerFaction == claimFaction) return;
        if (!playerFaction.getEnemies().contains(claimFaction.getId())) {
            player.sendMessage(plugin.getConfigUtils().getLocalisation("terrain-claimed"));
            event.setCancelled(true);
        }
    }
}
