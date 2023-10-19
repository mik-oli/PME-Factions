package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigUtils {

    private final ConfigurationSection config;

    public ConfigUtils(Krolikcraft plugin) {
        this.config = plugin.getConfig();
    }

    public ConfigurationSection getConfig() {
        return this.config;
    }

    public CommandsPermissions getPermission(String command) {
        return CommandsPermissions.valueOf(config.getString("commands-permissions." + command));
    }

    public String getLocalisation(String key) {
        return Utils.pluginPrefix() + Utils.coloring(config.getString("localisation." + key));
    }
}
