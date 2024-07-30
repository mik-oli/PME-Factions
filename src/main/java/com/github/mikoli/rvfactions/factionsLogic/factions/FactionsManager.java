package com.github.mikoli.rvfactions.factionsLogic.factions;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.Claim;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimsManager;

import com.github.mikoli.rvfactions.factionsLogic.events.FactionChangeEvent;
import com.github.mikoli.rvfactions.factionsLogic.events.FactionChangeType;
import com.github.mikoli.rvfactions.factionsLogic.utils.BukkitUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FactionsManager {

    private final RVFactions plugin;
    private final HashMap<UUID, Faction> factionsList = new HashMap<>();

    public FactionsManager(RVFactions plugin) {
        this.plugin = plugin;
    }

    public HashMap<UUID, Faction> getFactionsList() {
        return this.factionsList;
    }

    public Faction createFaction(String name, String shortcut, UUID leader, Location coreLocation) {
        UUID id = UUID.randomUUID();
        while (factionsList.containsKey(id)) id = UUID.randomUUID();

        Faction faction = new Faction(id);
        faction.setName(name);
        faction.setShortcut(shortcut);
        faction.setColor(ChatColor.WHITE);
        faction.setLeader(leader);
        faction.addMember(leader);
        faction.setCoreLocation(coreLocation);

        factionsList.put(id, faction);
        BukkitUtils.callEvent(new FactionChangeEvent(faction, FactionChangeType.CREATE));
        return faction;
    }

    public void removeFaction(Faction faction) {

        ClaimsManager claimsManager = plugin.getClaimsManager();
//        HashMap<UUID, Claim> tempList = claimsManager.getClaimsList();
//        for (UUID id : tempList.keySet()) {
//            Claim claim = claimsManager.getClaim(id);
//            if (claim.getOwner().equals(faction.getId())) claimsManager.removeClaim(claim.getId());
//        }
        List<UUID> toRemove = new ArrayList<>();
        for (UUID id : claimsManager.getClaimsList().keySet()) {
            Claim claim = claimsManager.getClaim(id);
            if (claim.getOwner().equals(faction.getId())) {
                toRemove.add(claim.getId());
            }
        }
        for (UUID id : toRemove) claimsManager.removeClaim(id);

        Block coreBlock = faction.getCoreLocation().getBlock();
        coreBlock.setType(Material.AIR);

        for (UUID id : faction.getEnemies()) {
            if (factionsList.get(id).isAtWarWith(faction)) factionsList.get(id).removeEnemy(faction.getId());
        }
        factionsList.remove(faction.getId());
        FactionsDataHandler.removeFactionData(faction);
        BukkitUtils.callEvent(new FactionChangeEvent(faction, FactionChangeType.REMOVE));
    }

    public Faction getFactionByUUID(UUID id) {
        return factionsList.get(id);
    }

    public Faction getPlayersFaction(UUID playerUUID) {
        for (Faction faction : factionsList.values()) {
            if (faction.isMember(playerUUID)) {
                return faction;
            }
        }
        return null;
    }

    public Faction getFactionFromName(String name) {
        for (Faction faction : factionsList.values()) {
            if (faction.getName().equals(name)) {
                return faction;
            }
        }
        return null;
    }
}
