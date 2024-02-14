package com.github.mikoli.krolikcraft.factionsLogic.utils;

import com.github.mikoli.krolikcraft.PMEFactions;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.UUID;

public class BukkitUtils {

    public static String coloring(String str)
    {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String pluginPrefix()
    {
        return coloring("&e[&2PME&e]:&r ");
    }

    public static void consoleWarning(String str)
    {
        Bukkit.getLogger().warning(pluginPrefix() + str);
    }

    public static void consoleInfo(String str)
    {
        Bukkit.getLogger().info(pluginPrefix() + str);
    }

    public static void consoleError(String stackTrace)
    {
        Bukkit.getLogger().severe(pluginPrefix() + stackTrace);
    }

    public static void returnMessage(PMEFactions plugin, Object target, String message) {
        if (target instanceof Player) ((Player) target).sendMessage(plugin.getConfigUtils().getLocalisation(message));
        else ((CommandSender) target).sendMessage(plugin.getConfigUtils().getLocalisation(message));
    }

    public static UUID getPlayerUUID(String player) {

        UUID uuid = null;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);

        if (offlinePlayer.hasPlayedBefore()) uuid = offlinePlayer.getUniqueId();
        return uuid;
    }

    public static ChatColor getColor(String arg) {
        ChatColor color = ChatColor.WHITE;
        try {
            color = ChatColor.valueOf(arg);
        } catch (IllegalArgumentException ignored) {}
        return color;
    }

    public static void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
