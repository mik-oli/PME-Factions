package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {

    private final Krolikcraft plugin;

    public ConfigUtil(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return this.plugin.getConfig();
    }
}
