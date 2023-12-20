package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public enum CommandsPermissions {
    ADMIN,
    LEADER,
    OFFICER,
    MEMBER,
    NULL;

    public static boolean hasPlayerPermission(PMEFactions plugin, CommandSender commandSender, CommandsPermissions permissions, boolean adminMode) {
        if ((adminMode || permissions == CommandsPermissions.ADMIN) && commandSender.hasPermission("pmefactions.admin")) return true;

        UUID playerUUID = Bukkit.getPlayer(commandSender.getName()).getUniqueId();
        Faction faction = plugin.getFactionsManager().getPlayersFaction(playerUUID);
        if (faction == null) return false;

        if (permissions == CommandsPermissions.MEMBER && faction.isMember(playerUUID)) return true;
        else if (permissions == CommandsPermissions.OFFICER && faction.isOfficer(playerUUID)) return true;
        else if (permissions == CommandsPermissions.LEADER && faction.getLeader().equals(playerUUID)) return true;

        return false;
    }
}
