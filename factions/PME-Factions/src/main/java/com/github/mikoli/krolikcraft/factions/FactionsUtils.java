package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import org.bukkit.Location;

import java.util.UUID;

public class FactionsUtils {

    public static boolean isPlayerInFaction(Krolikcraft plugin, UUID playerUUID) {
        for (Faction faction : plugin.getFactionsHashMap().values()) {
           if (faction.getMembers().contains(playerUUID)) return true;
        }
        return false;
    }

    public static Faction getPlayersFaction(Krolikcraft plugin, UUID playerUUID) {
        for (Faction faction : plugin.getFactionsHashMap().values()) {
            if (faction.isMember(playerUUID)) {
                return faction;
            }
        }
        return null;
    }

    public static Faction getFactionFromName(Krolikcraft plugin, String name) {
        for (UUID uuid : plugin.getFactionsHashMap().keySet()) {
            if (plugin.getFactionsHashMap().get(uuid).getName().equals(name)) {
                return plugin.getFactionsHashMap().get(uuid);
            }
        }
        return null;
    }

    public static void createFaction(Krolikcraft plugin, String name, UUID leader, Location coreLocation) {
        Faction faction = new Faction(plugin);
        faction.setName(name);
        faction.setLeader(leader);
        faction.addMember(leader);

        UUID factionId = UUID.randomUUID();
        while (plugin.getFactionsHashMap().containsKey(factionId)) {
            factionId = UUID.randomUUID();
        }
        faction.setId(factionId);
        faction.setCoreLocation(coreLocation);
        plugin.getFactionsHashMap().put(factionId, faction);
    }
}
