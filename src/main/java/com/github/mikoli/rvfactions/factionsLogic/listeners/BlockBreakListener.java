package com.github.mikoli.rvfactions.factionsLogic.listeners;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.Claim;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimType;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimsManager;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;
import com.github.mikoli.rvfactions.factionsLogic.utils.Permissions;
import com.github.mikoli.rvfactions.factionsLogic.utils.RankPermissions;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final RVFactions plugin;

    public BlockBreakListener(RVFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(block.getChunk())) return;

        UUID playerUUID = player.getUniqueId();
        if (plugin.getFactionsManager().getPlayersFaction(playerUUID) == null && !player.hasPermission(Permissions.ADMIN.getPermission())) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-interact"));
            return;
        }

        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
        Claim claim = claimsManager.getClaim(claimId);
        UUID claimFactionId = claim.getOwner();

        if (player.hasPermission(Permissions.ADMIN.getPermission())) {
            if (claim.getCoreLocation().equals(block.getLocation())) event.setCancelled(true);
            return;
        }

        Faction claimFaction = plugin.getFactionsManager().getFactionByUUID(claimFactionId);
        Faction playerFaction = plugin.getFactionsManager().getPlayersFaction(playerUUID);

        //taking over neutral claim
        if (claim.getClaimType() == ClaimType.NEUTRAL) {
            if (claim.getCoreLocation().getBlock().equals(block)) {
                if (RankPermissions.hasPlayerPermission(plugin, player, RankPermissions.OFFICER, false)) {
                    claimsManager.changeOwnership(claim, playerFaction);
                    claim.setClaimType(ClaimType.OUTPOST);
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
                    event.setDropItems(false);
                    event.setCancelled(true);
                }
            }
        }
        else {
            //unclaiming own terrain
            if (playerFaction.getId().equals(claimFaction.getId())) {
                if (claim.getCoreLocation().equals(block.getLocation())) {
                    if (!RankPermissions.hasPlayerPermission(plugin, player, RankPermissions.OFFICER, false) || claim.getClaimType() == ClaimType.CORE) {
                        player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-unclaim"));
                        event.setCancelled(true);
                        return;
                    }
                    claimsManager.removeClaim(claimId);
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("unclaimed"));
                }
            } else if (claimFaction.isAtWarWith(playerFaction)) {
                //destroying faction core
                if (claim.getClaimType() == ClaimType.CORE && claimFaction.getCoreLocation().equals(block.getLocation())) {
                    boolean hasOutposts = false;
                    for (Claim tempClaim : claimsManager.getClaimsList().values()) {
                        if (tempClaim.getOwner().equals(claimFaction.getId()) && tempClaim.getClaimType() == ClaimType.OUTPOST) {
                            hasOutposts = true;
                            break;
                        }
                    }
                    if (!hasOutposts) {
                        plugin.getFactionsManager().removeFaction(claimFaction);
                        Bukkit.broadcastMessage(plugin.getConfigUtils().getLocalisation("faction-destroyed").replace("{0}", claimFaction.getName()));
                    }
                    event.setDropItems(false);
                    event.setCancelled(true);
                    //taking over enemy claim
                } else if (claim.getCoreLocation().equals(block.getLocation())) {
                    claimsManager.changeOwnership(claim, playerFaction);
                    player.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
                    event.setDropItems(false);
                    event.setCancelled(true);
                }
            }
        }
    }
}
