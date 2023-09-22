package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.factions.ClaimType;
import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockPlaceListener implements Listener {

    private final Krolikcraft plugin;

    public BlockPlaceListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.BEDROCK) return; //TODO changing bedrock to other block || checking if block has tag to claim

        Player player = event.getPlayer();
        if (!plugin.getFactionsHashMap().containsKey(plugin.getPersistentDataUtil().getPlayerData(player, PersistentDataKeys.FACTION))) return;
        if (!Boolean.getBoolean(plugin.getPersistentDataUtil().getPlayerData(player, PersistentDataKeys.ISLEADER).toString())) return;

        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = block.getChunk();
        Faction faction = plugin.getFactionsHashMap().get(plugin.getPersistentDataUtil().getPlayerData(player, PersistentDataKeys.FACTION));
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, false)) return;
        Set<Chunk> chunksToClaim = new HashSet<>();
        chunksToClaim.add(chunk);
        claimsManager.createClaim(faction, chunksToClaim, ClaimType.CLAIM);
    }
}
