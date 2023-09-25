package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.commandsHandler.FactionsCommandsHandler;
import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.factions.LoadSaveFactionData;
import com.github.mikoli.krolikcraft.listeners.BlockPlaceListener;
import com.github.mikoli.krolikcraft.utils.FilesUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public final class Krolikcraft extends JavaPlugin {

    private final HashMap<String, Faction> factionsHashMap = new HashMap<>();
    private final HashMap<String, FilesUtils> factionsFilesHashMap = new HashMap<>();
    private final ClaimsManager claimsManager = new ClaimsManager(this);
    private final FilesUtils claimsFilesUtil = new FilesUtils(this, "claims");
    private final BlockPlaceListener blockPlaceListener = new BlockPlaceListener(this);

    @Override
    public void onEnable() {
        new FactionsCommandsHandler(this);
        try {
            this.loadFactionsData();
            this.loadClaimsData();
        } catch (IOException e) {
            Utils.consoleError(Arrays.toString(e.getStackTrace()));
        }

        this.setEventsListeners();
    }

    @Override
    public void onDisable() {

        try {
            this.saveFactionsData();
            this.saveClaimsData();
        } catch (IOException e) {
            Utils.consoleError(Arrays.toString(e.getStackTrace()));
        }
    }

    public HashMap<String, Faction> getFactionsHashMap() {
        return factionsHashMap;
    }

    public HashMap<String, FilesUtils> getFactionsFilesHashMap() {
        return factionsFilesHashMap;
    }

    public FilesUtils getClaimsFilesUtil() {
        return claimsFilesUtil;
    }

    public ClaimsManager getClaimsManager() {
        return claimsManager;
    }

    //Private methods
    private final PluginManager pluginManager = this.getServer().getPluginManager();
    private void setEventsListeners() {
        pluginManager.registerEvents(blockPlaceListener, this);
    }

    private void loadFactionsData() throws IOException {
        File factionsDirectory = new File(this.getDataFolder() + File.separator + "factions");
        if (!factionsDirectory.exists()) return;

        for (File factionFile : factionsDirectory.listFiles()) {
            String factionName = factionFile.getName().replace(".yml", "");
            FilesUtils factionFilesUtil = new FilesUtils(this, factionName);
            factionFilesUtil.createFactionsDataFile();
            factionsFilesHashMap.put(factionName, factionFilesUtil);
        }

        for (String str : factionsFilesHashMap.keySet()) {
            Faction faction = new Faction(this);
            LoadSaveFactionData.loadFactionData(factionsFilesHashMap.get(str), faction);
            factionsHashMap.put(str, faction);
        }
    }

    private void saveFactionsData() throws IOException {
        for (String str : factionsHashMap.keySet()) {
            if (!factionsFilesHashMap.containsKey(str) || factionsFilesHashMap.get(str) == null) {
                FilesUtils file = new FilesUtils(this, str);
                file.createFactionsDataFile();
                LoadSaveFactionData.saveFactionData(file, factionsHashMap.get(str));
            }
            else LoadSaveFactionData.saveFactionData(factionsFilesHashMap.get(str), factionsHashMap.get(str));
        }
    }

    private void loadClaimsData() throws IOException {
        File factionsDirectory = this.getDataFolder();
        if (!factionsDirectory.exists()) return;
        claimsFilesUtil.createClaimsDataFile();
        LoadSaveClaimsData.loadFClaimsData(claimsFilesUtil, claimsManager);
    }

    private void saveClaimsData() throws IOException {
        claimsFilesUtil.createClaimsDataFile();
        LoadSaveClaimsData.saveClaimsData(claimsFilesUtil, claimsManager);
    }
}
