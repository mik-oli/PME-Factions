package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FilesUtils {

    private final Krolikcraft plugin;
    private final String fileName;
    private File dataFile;
    private YamlConfiguration data;

    public FilesUtils(Krolikcraft plugin, String name) {
        this.plugin = plugin;
        this.fileName = name;

        this.createDataFile();
    }

    public YamlConfiguration getData() {
        return this.data;
    }

    public void saveData() throws IOException {
        this.data.save(dataFile);
    }

    private void createDataFile() {
        dataFile = new File(plugin.getDataFolder() + File.separator + "factions", fileName + ".yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            plugin.saveResource(fileName + ".yml", false);
        }

        data = new YamlConfiguration();
        YamlConfiguration.loadConfiguration(dataFile);
    }
}
