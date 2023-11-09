package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.claims.LoadSaveClaimsData;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final Krolikcraft plugin;

    public BlockBreakListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();

        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(block.getChunk())) return;

        Player player = event.getPlayer();
        if (player.hasPermission("pmefactions.admin")) return;

        UUID playerUUID = player.getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) {
            event.setCancelled(true);
            return;
        }

        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
        Faction claimFaction = claimsManager.getClaimOwner(block.getChunk());
        Faction playerFaction = FactionsUtils.getPlayersFaction(plugin, playerUUID);
        if (playerFaction.getId().equals(claimFaction.getId())) {
            if (claimsManager.getClaimCoreLocation().get(claimId).equals(block.getLocation())) {
                if (!FactionsUtils.hasPlayerPermission(plugin, player, plugin.getConfigUtils().getPermission("unclaim"), false)) {
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
                    event.setCancelled(true);
                    return;
                }
                claimsManager.removeClaim(claimId);
                LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
                player.sendMessage(plugin.getConfigUtils().getLocalisation("unclaimed"));
            }
        } else if (claimFaction.getEnemies().contains(playerFaction.getId())) {
            if (claimFaction.getCoreLocation().getBlock().equals(block)) {
                boolean hasOutposts = false;
                for (UUID claimID : claimsManager.getClaimsOwnerMap().keySet()) {
                    if (claimsManager.getClaimsOwnerMap().get(claimID).equals(claimFaction.getId())) continue;
                    if (claimsManager.getClaimsTypesMap().get(claimID) == ClaimType.OUTPOST) {
                        hasOutposts = true;
                        break;
                    }
                }
                if (!hasOutposts) {
                    FactionsUtils.removeFaction(plugin, claimFaction);
                    //TODO Faction destroyed message with input for faction
                }
            } else if (claimsManager.getClaimCoreLocation().get(claimId).equals(block.getLocation())) {
                claimsManager.changeClaimOwner(claimId, playerFaction);
                player.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
                event.setDropItems(false);
                event.setCancelled(true);
            }
        } else if (claimsManager.getClaimsTypesMap().get(claimId) == ClaimType.NEUTRAL) {
            if (claimFaction.getCoreLocation().getBlock().equals(block)) {
                if (FactionsUtils.hasPlayerPermission(plugin, player, plugin.getConfigUtils().getPermission("claim"), false)) {
                    claimsManager.changeClaimOwner(claimId, playerFaction);
                }
            }
        }
    }
}
