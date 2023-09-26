package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;

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
}
