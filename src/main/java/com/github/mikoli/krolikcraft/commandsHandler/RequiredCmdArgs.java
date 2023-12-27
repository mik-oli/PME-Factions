package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public static Faction getFactionByPlayer(PMEFactions plugin, Player player) {
        return plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
    }

    public static Faction getFactionByName(PMEFactions plugin, String arg) {
        return plugin.getFactionsManager().getFactionFromName(arg);
    }

    public static UUID getTargetPlayer(boolean admin, String arg) {
        UUID uuid = null;
        OfflinePlayer offlinePlayer;

        if (admin) offlinePlayer = Bukkit.getOfflinePlayer(java.util.UUID.fromString(arg));
        else offlinePlayer = Bukkit.getOfflinePlayer(arg);

        if (offlinePlayer.hasPlayedBefore()) uuid = offlinePlayer.getUniqueId();
        return uuid;
    }

    public static ClaimType getClaimType(String arg) {
        ClaimType claimType;
        try {
            claimType = ClaimType.valueOf(arg);
        } catch (Exception exception) {
            claimType = ClaimType.CLAIM_5x5;
        }
        return claimType;
    }

    public static ChatColor getFactionColor(String arg) {
        ChatColor color = ChatColor.WHITE;
        try {
            color = ChatColor.valueOf(arg);
        } catch (IllegalArgumentException ignored) {}
        return color;
    }

    public static UUID getUUID(String arg) {
        UUID uuid = null;
        try {
            uuid = java.util.UUID.fromString(arg);
        } catch (IllegalArgumentException ignored) {}
        return uuid;
    }
}
