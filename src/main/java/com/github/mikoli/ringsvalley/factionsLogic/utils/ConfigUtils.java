package com.github.mikoli.ringsvalley.factionsLogic.utils;

import com.github.mikoli.ringsvalley.RVFactions;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigUtils {

    private final ConfigurationSection config;

    public ConfigUtils(RVFactions plugin) {
        this.config = plugin.getConfig();
    }

    public ConfigurationSection getConfig() {
        return config;
    }

    public String getLocalisation(String key) {
        return BukkitUtils.pluginPrefix() + BukkitUtils.coloring(config.getString("localisation." + key));
    }

    public int getMaxLength(String key) {
        return config.getInt(key);
    }
}
