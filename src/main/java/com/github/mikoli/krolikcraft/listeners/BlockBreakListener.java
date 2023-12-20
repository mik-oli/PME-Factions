package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.Claim;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.claims.LoadSaveClaimsData;

import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final PMEFactions plugin;

    public BlockBreakListener(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("pmefactions.admin")) return;

        Block block = event.getBlock();

        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(block.getChunk())) return;

        UUID playerUUID = player.getUniqueId();
        if (!plugin.getFactionsManager().isPlayerInFaction(playerUUID)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-interact"));
            return;
        }

        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
        Claim claim = claimsManager.getClaimsList().get(claimId);
        UUID claimFactionId = claimsManager.getClaimsList().get(claimId).getClaimOwner();
        Faction claimFaction = plugin.getFactionsManager().getFactionsList().get(claimFactionId);
        Faction playerFaction = plugin.getFactionsManager().getPlayersFaction(playerUUID);

        //taking over neutral claim
        if (claim.getClaimType() == ClaimType.NEUTRAL) {
            if (claim.getCoreLocation().getBlock().equals(block.getLocation())) {
                if (CommandsPermissions.hasPlayerPermission(plugin, player, plugin.getConfigUtils().getPermission("claim"), false)) {
                    claimsManager.changeClaimOwner(claim, playerFaction);
                    claim.setClaimType(ClaimType.OUTPOST);
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
                    event.setDropItems(false);
                    event.setCancelled(true);
                }
            }
        }
        else if (claimFaction != null) {
            //unclaiming own terrain
            if (playerFaction.getId().equals(claimFaction.getId())) {
                if (claimFaction.getCoreLocation().equals(block.getLocation())) {
                    if (!CommandsPermissions.hasPlayerPermission(plugin, player, plugin.getConfigUtils().getPermission("unclaim"), false)) {
                        player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-unclaim"));
                        event.setCancelled(true);
                        return;
                    }
                    claimsManager.removeClaim(claimId);
                    LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("unclaimed"));
                }
            } else if (claimFaction.getEnemies().contains(playerFaction.getId())) {
                //destroying faction core
                if (claimFaction.getCoreLocation().getBlock().equals(block.getLocation())) {
                    boolean hasOutposts = false;
                    for (Claim tempClaim : claimsManager.getClaimsList().values()) {
                        if (tempClaim.getClaimOwner().equals(claimFaction.getId()) && tempClaim.getClaimType() == ClaimType.OUTPOST) {
                            hasOutposts = true;
                            break;
                        }
                    }
                    if (!hasOutposts) {
                        plugin.getFactionsManager().removeFaction(claimFaction);
                        //TODO Faction destroyed message with input for faction
                    }
                //taking over enemy claim
                } else if (claim.getCoreLocation().equals(block.getLocation())) {
                    claimsManager.changeClaimOwner(claim, playerFaction);
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
                    event.setDropItems(false);
                    event.setCancelled(true);
                }
            }
        }
    }
}
