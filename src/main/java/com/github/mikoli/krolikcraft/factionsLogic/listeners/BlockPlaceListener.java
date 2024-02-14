package com.github.mikoli.krolikcraft.factionsLogic.listeners;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.claims.ClaimType;
import com.github.mikoli.krolikcraft.factionsLogic.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factionsLogic.factions.Faction;
import com.github.mikoli.krolikcraft.factionsLogic.utils.Permissions;
import com.github.mikoli.krolikcraft.factionsLogic.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

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

    private final PMEFactions plugin;

    public BlockPlaceListener(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ClaimsManager claimsManager = plugin.getClaimsManager();

        if (claimsManager.isChunkClaimed(block.getChunk())) {
            if (player.hasPermission(Permissions.ADMIN.getPermission())) return;

            UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
            UUID claimFactionId = claimsManager.getClaim(claimId).getOwner();
            Faction claimFaction = plugin.getFactionsManager().getFactionsList().get(claimFactionId);
            Faction playerFaction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());

            if (playerFaction == null) {
                event.setCancelled(true);
                player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-interact"));
            }
            else if (!claimFaction.getId().equals(playerFaction.getId()) && !claimFaction.isAtWarWith(playerFaction)) {
                event.setCancelled(true);
                player.sendMessage(plugin.getConfigUtils().getLocalisation("cant-interact"));
            }
        }
        else {
            createClaim(event);
            createFaction(event);
        }
    }

    private void createClaim(BlockPlaceEvent event) {
        //checking if placed block is claim flag
        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataUtils.CLAIMFLAG, dataContainer)) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataUtils.CLAIMFLAG, dataContainer).equals("true")) return;

        //checking if player is in faction and has ability to claim
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Faction playerFaction = plugin.getFactionsManager().getPlayersFaction(playerUUID);
        if (!RankPermissions.hasPlayerPermission(plugin, player, RankPermissions.OFFICER, false)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
            return;
        }
        if (!PersistentDataUtils.hasData(plugin, PersistentDataUtils.CLAIMOWNER, dataContainer)) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataUtils.CLAIMOWNER, dataContainer).equals(playerFaction.getId().toString())) return;

        //claiming
        Block block = event.getBlockPlaced();
        Chunk chunk = block.getChunk();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        ClaimType claimType = ClaimType.valueOf(PersistentDataUtils.getData(plugin, PersistentDataUtils.CLAIMTYPE, dataContainer));
        if (!claimsManager.checkIfCanCreateClaim(playerFaction, chunk, claimType, true)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }
        claimsManager.createClaim(playerFaction, claimType, block.getLocation().subtract(0, 1, 0));

        Block blockBelow = block.getLocation().subtract(0, 1, 0).getBlock();
        blockBelow.setType(Material.NOTE_BLOCK);
        PersistentDataUtils.removeData(plugin, PersistentDataUtils.CLAIMFLAG, dataContainer);
        item.setItemMeta(itemMeta);
        event.getPlayer().sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }

    private void createFaction(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (!PersistentDataUtils.hasData(plugin, PersistentDataUtils.COREFLAG, dataContainer)) return;
        if (!PersistentDataUtils.getData(plugin, PersistentDataUtils.COREFLAG, dataContainer).equals("true")) return;

        Player player = event.getPlayer();
        List<String> lore = itemMeta.getLore();
        UUID leader = UUID.fromString(lore.get(3).substring(12));
        if (!player.getUniqueId().equals(leader)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("target-not-member"));
            return;
        }

        Location factionLocation = event.getBlockPlaced().getLocation();
        if (!plugin.getClaimsManager().checkIfCanCreateClaim(null, factionLocation.getChunk(), ClaimType.CORE, false)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }
        String factionName = lore.get(1).substring(10);
        String factionShortcut = lore.get(2).substring(14);
        Faction faction = plugin.getFactionsManager().createFaction(factionName, factionShortcut, leader, factionLocation);
        plugin.getClaimsManager().createClaim(faction, ClaimType.CORE, factionLocation);
        player.sendMessage(plugin.getConfigUtils().getLocalisation("faction-created"));
    }
}
