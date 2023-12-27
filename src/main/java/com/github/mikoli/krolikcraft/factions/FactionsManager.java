package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;

import org.bukkit.*;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.UUID;

public class FactionsManager {

    private final PMEFactions plugin;
    private final HashMap<UUID, Faction> factionsList = new HashMap<>();

    public FactionsManager(PMEFactions plugin) {
        this.plugin = plugin;
    }

    public HashMap<UUID, Faction> getFactionsList() {
        return this.factionsList;
    }


    public boolean isPlayerInFaction(UUID playerUUID) {
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
           if (faction.getMembers().contains(playerUUID)) return true;
        }
        return false;
    }

    public Faction getPlayersFaction(UUID playerUUID) {
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            if (faction.isMember(playerUUID)) {
                return faction;
            }
        }
        return null;
    }

    public Faction getFactionFromName(String name) {
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            if (faction.getName().equals(name)) {
                return faction;
            }
        }
        return null;
    }

    public void createFaction(String name, String shortcut, UUID leader, Location coreLocation) {
        UUID id = UUID.randomUUID();
        while (plugin.getFactionsManager().getFactionsList().containsKey(id)) id = UUID.randomUUID();

        Faction faction = new Faction(id);
        faction.setName(name);
        faction.setShortcut(shortcut);
        faction.setColor(ChatColor.WHITE);
        faction.setLeader(leader);
        faction.addMember(leader);
        faction.setCoreLocation(coreLocation);

        plugin.getFactionsManager().getFactionsList().put(id, faction);
    }

    public void removeFaction(Faction faction) {

        ClaimsManager claimsManager = plugin.getClaimsManager();
        for (UUID id : claimsManager.getClaimsList().keySet()) {
            if (claimsManager.getClaimsList().get(id).getClaimOwner().equals(faction.getId()))
                claimsManager.removeClaim(id);
        }
        Block coreBlock = faction.getCoreLocation().getBlock();
        coreBlock.setType(Material.AIR);

        for (Faction f : plugin.getFactionsManager().getFactionsList().values()) {
            if (f.getEnemies().contains(faction.getId())) f.getEnemies().remove(faction.getId());
        }
        plugin.getFactionsManager().removeFaction(faction);
    }
}
