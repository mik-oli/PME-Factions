package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public enum RequiredCmdArgs {

    NULL,
    REQUESTFACTION,
    TARGETFACTION,
    TARGETPLAYER,
    CLAIMTYPE,
    NAME,
    SHORTCUT,
    COLOR,
    UUID;

    private static Faction getRequestFaction(PMEFactions plugin, boolean admin, CommandSender commandSender, String arg) {

        if (admin) return plugin.getFactionsManager().getFactionFromName(arg);
        else return plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());
    }

    private static Faction getTargetFaction(PMEFactions plugin, boolean admin, String arg) {
        return plugin.getFactionsManager().getFactionFromName(arg);
    }

    private static UUID getTargetPlayer(boolean admin, String arg) {
        UUID uuid = null;
        OfflinePlayer offlinePlayer;

        if (admin) offlinePlayer = Bukkit.getOfflinePlayer(java.util.UUID.fromString(arg));
        else offlinePlayer = Bukkit.getOfflinePlayer(arg);

        if (offlinePlayer.hasPlayedBefore()) uuid = offlinePlayer.getUniqueId();
        return uuid;
    }

    private static ClaimType getClaimType(boolean admin, String arg) {
        ClaimType claimType;
        try {
            claimType = ClaimType.valueOf(arg);
        } catch (Exception exception) {
            claimType = ClaimType.CLAIM_5x5;
        }
        return claimType;
    }

    private static ChatColor getFactionColor(boolean admin, String arg) {
        ChatColor color = null;
        try {
            color = ChatColor.valueOf(arg);
        } catch (IllegalArgumentException ignored) {}
        return color;
    }

    private static UUID getUUID(String arg) {
        UUID uuid = null;
        try {
            uuid = java.util.UUID.fromString(arg);
        } catch (IllegalArgumentException ignored) {}
        return uuid;
    }
}
