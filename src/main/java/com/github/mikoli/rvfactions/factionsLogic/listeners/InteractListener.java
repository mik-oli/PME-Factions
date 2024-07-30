package com.github.mikoli.rvfactions.factionsLogic.listeners;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.Claim;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimsManager;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;

import com.github.mikoli.rvfactions.factionsLogic.utils.Permissions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final RVFactions plugin;

    public InteractListener(RVFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteractEvent(PlayerInteractEvent event) {

        if (event.getPlayer().hasPermission(Permissions.ADMIN.getPermission())) return;
        Block block = event.getClickedBlock();
        if (block == null) return;
        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(block.getChunk())) return;

        Player player = event.getPlayer();
        Faction playerFaction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
        if (playerFaction == null) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-interact"));
            return;
        }
        Claim claim = claimsManager.getClaim(claimsManager.getClaimId(block.getChunk()));
        Faction claimFaction = plugin.getFactionsManager().getFactionByUUID(claim.getOwner());
        if (claimFaction == null) return;
        else if (playerFaction == claimFaction) return;
        else if (!playerFaction.isAtWarWith(claimFaction)) {
            player.sendMessage(plugin.getConfigUtils().getLocalisation("terrain-claimed"));
            event.setCancelled(true);
        }
    }
}
