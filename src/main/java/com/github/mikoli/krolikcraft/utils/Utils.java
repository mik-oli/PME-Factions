package com.github.mikoli.krolikcraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {

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
}
