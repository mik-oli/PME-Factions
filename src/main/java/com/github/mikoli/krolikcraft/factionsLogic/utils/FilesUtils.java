package com.github.mikoli.krolikcraft.factionsLogic.utils;

import com.github.mikoli.krolikcraft.PMEFactions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FilesUtils {

    private final PMEFactions plugin;
    private final String fileName;
    private File dataFile;
    private FileConfiguration data;

    public FilesUtils(PMEFactions plugin, String name) {
        this.plugin = plugin;
        this.fileName = name;
    }

    public FileConfiguration getData() {
        return this.data;
    }

    public void saveData() throws IOException {
        this.data.save(dataFile);
    }

    public void loadFactionsDataFile() throws IOException {
        dataFile = new File(plugin.getDataFolder() + File.separator + "factions", fileName + ".yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            dataFile.createNewFile();
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void loadClaimsDataFile() throws IOException {
        dataFile = new File(plugin.getDataFolder(), fileName + ".yml");
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void deleteFile() {
        dataFile.delete();
    }
}
