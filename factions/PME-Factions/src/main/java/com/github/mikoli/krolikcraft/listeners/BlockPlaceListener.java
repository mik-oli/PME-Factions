package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.factions.ClaimType;
import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BlockPlaceListener implements Listener {

    private final Krolikcraft plugin;

    public BlockPlaceListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        //checking if placed block is claim flag
        ItemStack item = event.getItemInHand();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item))) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item)).equals("true")) return;

        //checking if player is in faction and has ability to claim
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return;
        if (!FactionsUtils.getPlayersFaction(plugin, playerUUID).getLeader().equals(playerUUID)) return; //TODO checking if player can claim

        //claiming
        Block block = event.getBlockPlaced();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        Faction faction = FactionsUtils.getPlayersFaction(plugin, playerUUID);
        Chunk chunk = block.getChunk();
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, false)) return;
        Set<Chunk> chunksToClaim = new HashSet<>();
        chunksToClaim.add(chunk);
        ClaimType claimType = ClaimType.valueOf(PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMTYPE,  PersistentDataUtils.getBlockContainer(block)));
        claimsManager.createClaim(faction, chunksToClaim, claimType);

        Block blockBelow = block.getRelative(BlockFace.DOWN);
        blockBelow.setType(Material.NOTE_BLOCK);
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(blockBelow), "true");
        PersistentDataUtils.removeData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item));
    }
}
