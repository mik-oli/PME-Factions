package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.PMEFactions;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigUtils {

    private final ConfigurationSection config;

    public ConfigUtils(PMEFactions plugin) {
        this.config = plugin.getConfig();
    }

    public ConfigurationSection getConfig() {
        return this.config;
    }

    public RankPermissions getPermission(String command) {
        return RankPermissions.valueOf(config.getString("commands-permissions." + command).toUpperCase());
    }

    public String getLocalisation(String key) {
        return Utils.pluginPrefix() + Utils.coloring(config.getString("localisation." + key));
    }

    public int getMaxLength(String key) {
        return config.getInt(key);
    }
}
