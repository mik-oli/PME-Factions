package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.factions.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

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
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(block))) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(block)).equals("true")) return;

        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(block.getChunk())) return;

        UUID playerUUID = event.getPlayer().getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return; //TOTEST test if cancel event is required

        //case 1, broken by faction leader -> unclaim
        //case 2, broken by enemy in war -> change owner
        if (FactionsUtils.getPlayersFaction(plugin, playerUUID).getLeader().equals(playerUUID)) { //TODO if player can unclaim
            UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
            claimsManager.removeClaim(claimId);
            LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
        } else {
            return;
        }

        event.setDropItems(false);
    }
}
