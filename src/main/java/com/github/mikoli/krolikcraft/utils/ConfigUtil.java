package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {

    private final Krolikcraft plugin;
    private File configFile;
    private FileConfiguration config;

    public ConfigUtil(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() throws IOException {
        this.config.save(configFile);
    }

    public void createConfigFile(String name) {
        configFile = new File(plugin.getDataFolder(), name + ".yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        config = new YamlConfiguration();
        YamlConfiguration.loadConfiguration(configFile);
    }
}
