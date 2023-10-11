package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BlockPlaceListener implements Listener {

    private final Krolikcraft plugin;

    public BlockPlaceListener(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        createClaim(event);
        createFaction(event);
    }

    private void createClaim(BlockPlaceEvent event) {
        //checking if placed block is claim flag
        ItemStack item = event.getItemInHand();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item))) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item)).equals("true")) return;

        //checking if player is in faction and has ability to claim
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return;
        if (!FactionsUtils.getPlayersFaction(plugin, playerUUID).getLeader().equals(playerUUID)) return;
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.CLAIMOWNER, PersistentDataUtils.getItemContainer(item))) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMOWNER, PersistentDataUtils.getItemContainer(item)).equals(FactionsUtils.getPlayersFaction(plugin, playerUUID).getId().toString())) return;

        //claiming
        Block block = event.getBlockPlaced();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        Faction faction = FactionsUtils.getPlayersFaction(plugin, playerUUID);
        Chunk chunk = block.getChunk();
        ClaimType claimType = ClaimType.valueOf(PersistentDataUtils.getData(plugin, PersistentDataKeys.CLAIMTYPE,  PersistentDataUtils.getBlockContainer(block)));
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, claimType, false)) return;
        claimsManager.createClaim(faction, chunk, claimType);

        Block blockBelow = block.getRelative(BlockFace.DOWN);
        blockBelow.setType(Material.NOTE_BLOCK);
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(blockBelow), "true");
        PersistentDataUtils.removeData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item));
    }

    private void createFaction(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataKeys.COREFLAG, PersistentDataUtils.getItemContainer(item))) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataKeys.COREFLAG, PersistentDataUtils.getItemContainer(item)).equals("true")) return;

        Player player = event.getPlayer();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        UUID leader = UUID.fromString(lore.get(3).substring(12));
        if (player.getUniqueId() != leader) return; //TODO error

        Location factionLocation = event.getBlockPlaced().getLocation();
        if (!plugin.getClaimsManager().checkIfCanCreateClaim(null, factionLocation.getChunk(), ClaimType.CORE, false)) return;
        String factionName = lore.get(1).substring(10);
        String factionShortcut = lore.get(2).substring(14);
        FactionsUtils.createFaction(plugin, factionName, leader, factionLocation);
        plugin.getClaimsManager().createClaim(FactionsUtils.getFactionFromName(plugin, factionName), factionLocation.getChunk(), ClaimType.CORE);
    }
}
