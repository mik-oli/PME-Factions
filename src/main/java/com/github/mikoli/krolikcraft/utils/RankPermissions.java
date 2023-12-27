package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public enum RankPermissions {
    ADMIN,
    LEADER,
    OFFICER,
    MEMBER,
    NULL;

    public static boolean hasPlayerPermission(PMEFactions plugin, CommandSender commandSender, RankPermissions permissions, boolean adminMode) {
        if ((adminMode || permissions == RankPermissions.ADMIN) && commandSender.hasPermission("pmefactions.admin")) return true;

        UUID playerUUID = Bukkit.getPlayer(commandSender.getName()).getUniqueId();
        Faction faction = plugin.getFactionsManager().getPlayersFaction(playerUUID);
        if (faction == null) return false;

        if (permissions == RankPermissions.MEMBER && faction.isMember(playerUUID)) return true;
        else if (permissions == RankPermissions.OFFICER && faction.isOfficer(playerUUID)) return true;
        else if (permissions == RankPermissions.LEADER && faction.getLeader().equals(playerUUID)) return true;

        return false;
    }
}
