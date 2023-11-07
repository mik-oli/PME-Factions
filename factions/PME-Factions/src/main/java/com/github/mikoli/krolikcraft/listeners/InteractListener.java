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
        if (block == null) return;
        if (!plugin.getClaimsManager().isChunkClaimed(block.getChunk())) return;

        Player player = event.getPlayer();
        Faction playerFaction = FactionsUtils.getPlayersFaction(plugin, player.getUniqueId());
        if (playerFaction == null) return;
        Faction claimFaction = plugin.getClaimsManager().getClaimOwner(block.getChunk());
        if (playerFaction == claimFaction) return;
        if (!playerFaction.getEnemies().contains(claimFaction.getId())) {
            player.sendMessage(plugin.getConfigUtils().getLocalisation("terrain-claimed"));
            event.setCancelled(true);
        }
    }
}
