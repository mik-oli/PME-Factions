package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final Krolikcraft plugin;

    public BlockBreakListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.BEDROCK) return;

        UUID playerUUID = event.getPlayer().getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return;
        //case 1, broken by faction leader -> unclaim
        //case 2, broken by enemy in war -> change owner
        if (FactionsUtils.getPlayersFaction(plugin, playerUUID).getLeader().equals(playerUUID)) {
            ClaimsManager claimsManager = plugin.getClaimsManager();
            UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
            claimsManager.removeClaim(claimId);
        } else {
            return;
        }
    }
}
