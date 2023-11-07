package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;
import java.util.UUID;

public class BlockPlaceListener implements Listener {

    private final Krolikcraft plugin;

    public BlockPlaceListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ClaimsManager claimsManager = plugin.getClaimsManager();

        if (!claimsManager.isChunkClaimed(block.getChunk())) return;
        if (!FactionsUtils.isPlayerInFaction(plugin, player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        createClaim(event);
        createFaction(event);
    }

    private void createClaim(BlockPlaceEvent event) {
        //checking if placed block is claim flag
        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMFLAG, dataContainer)) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMFLAG, dataContainer).equals("true")) return;

        //checking if player is in faction and has ability to claim
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return;
        Faction playerFaction = FactionsUtils.getPlayersFaction(plugin, playerUUID);
        if (!playerFaction.getLeader().equals(playerUUID)) return;
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMOWNER, dataContainer)) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMOWNER, dataContainer).equals(playerFaction.getId().toString())) return;

        //claiming
        Block block = event.getBlockPlaced();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = block.getChunk();
        ClaimType claimType = ClaimType.valueOf(PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMTYPE, dataContainer));
        if (!claimsManager.checkIfCanCreateClaim(playerFaction, chunk, claimType, true)) return;
        claimsManager.createClaim(playerFaction, chunk, claimType, block.getLocation().subtract(0, 1, 0));

        Block blockBelow = block.getLocation().subtract(0, 1, 0).getBlock();
        blockBelow.setType(Material.NOTE_BLOCK);
        PersistentDataUtils.removeData(plugin, PersistentDataKeys.CLAIMFLAG, dataContainer);
        item.setItemMeta(itemMeta);
        event.getPlayer().sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }

    private void createFaction(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.COREFLAG, dataContainer)) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.COREFLAG, dataContainer).equals("true")) return;

        Player player = event.getPlayer();
        List<String> lore = itemMeta.getLore();
        UUID leader = UUID.fromString(lore.get(3).substring(12));
        if (player.getUniqueId() != leader) return;

        Location factionLocation = event.getBlockPlaced().getLocation();
        if (!plugin.getClaimsManager().checkIfCanCreateClaim(null, factionLocation.getChunk(), ClaimType.CORE, false)) return;
        String factionName = lore.get(1).substring(10);
        String factionShortcut = lore.get(2).substring(14);
        FactionsUtils.createFaction(plugin, factionName, factionShortcut, leader, factionLocation);
        plugin.getClaimsManager().createClaim(FactionsUtils.getFactionFromName(plugin, factionName), factionLocation.getChunk(), ClaimType.CORE, factionLocation);
        player.sendMessage(plugin.getConfigUtils().getLocalisation("faction-created"));
    }
}
