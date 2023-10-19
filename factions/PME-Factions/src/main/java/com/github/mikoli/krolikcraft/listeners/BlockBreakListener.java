package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.claims.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

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
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(block))) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(block)).equals("true")) return;

        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(block.getChunk())) return;

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) {
            event.setCancelled(true);
        }

        Faction claimFaction = FactionsUtils.getFactionFromName(plugin, PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMOWNER, PersistentDataUtils.getBlockContainer(block)));
        Faction playerFaction = FactionsUtils.getPlayersFaction(plugin, playerUUID);
        if (FactionsUtils.hasPlayerPermission(plugin, player, plugin.getConfigUtils().getPermission("claim"), false) && claimFaction == playerFaction) {
            if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.COREBLOCK, PersistentDataUtils.getBlockContainer(block))) return;
            if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.COREBLOCK, PersistentDataUtils.getBlockContainer(block)).equals("true")) return;

            UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
            if (FactionsUtils.getPlayersFaction(plugin, playerUUID).getId() == claimsManager.getClaimsOwnerMap().get(claimId)) {
                player.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
                event.setCancelled(true);
            }
            claimsManager.removeClaim(claimId);
            LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
        } else if (claimFaction.getEnemies().contains(playerFaction.getId())) {
            if (PersistentDataUtils.getData(plugin, PersistentDataKeys.COREBLOCK, PersistentDataUtils.getBlockContainer(block)).equals("true")) {
                boolean hasOutposts = false;
                for (UUID claimID : claimsManager.getClaimsOwnerMap().keySet()) {
                    if (claimsManager.getClaimsOwnerMap().get(claimID) != claimFaction.getId()) continue;
                    if (claimsManager.getClaimsTypesMap().get(claimID) == ClaimType.OUTPOST) {
                        hasOutposts = true;
                        break;
                    }
                }
                if (!hasOutposts) {
                    FactionsUtils.removeFaction(plugin, claimFaction);
                    //TODO Faction destroyed message with input for faction
                }
            } else {
                claimsManager.changeClaimOwner(claimsManager.getClaimId(block.getChunk()), playerFaction);
                player.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
            }
        }
        event.setDropItems(false);
    }
}
