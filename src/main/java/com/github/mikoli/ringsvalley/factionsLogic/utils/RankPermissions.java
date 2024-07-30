package com.github.mikoli.ringsvalley.factionsLogic.utils;

import com.github.mikoli.ringsvalley.RVFactions;
import com.github.mikoli.ringsvalley.factionsLogic.factions.Faction;

import org.bukkit.entity.Player;

import java.util.UUID;

public enum RankPermissions {

    ADMIN,
    LEADER,
    OFFICER,
    MEMBER,
    NULL;

    public static boolean hasPlayerPermission(RVFactions plugin, Player player, RankPermissions permissions, boolean adminMode) {
        if ((adminMode || permissions == RankPermissions.ADMIN) && player.hasPermission("pmefactions.admin")) return true;

        UUID playerUUID = player.getUniqueId();
        Faction faction = plugin.getFactionsManager().getPlayersFaction(playerUUID);

        if (permissions == RankPermissions.NULL) return true;
        else if (faction == null) return false;
        else if (permissions == RankPermissions.MEMBER && faction.isMember(playerUUID)) return true;
        else if (permissions == RankPermissions.OFFICER && (faction.isOfficer(playerUUID) || faction.getLeader().equals(playerUUID))) return true;
        else if (permissions == RankPermissions.LEADER && faction.getLeader().equals(playerUUID)) return true;

        return false;
    }
}
