package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.claims.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.commandsHandler.CommandsManager;
import com.github.mikoli.krolikcraft.factions.*;
import com.github.mikoli.krolikcraft.listeners.BlockBreakListener;
import com.github.mikoli.krolikcraft.listeners.BlockPlaceListener;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.FilesUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public final class Krolikcraft extends JavaPlugin {

    private final HashMap<UUID, Faction> factionsHashMap = new HashMap<>();
    private final HashMap<UUID, FilesUtils> factionsFilesHashMap = new HashMap<>();
    private final ClaimsManager claimsManager = new ClaimsManager(this);
    private final FilesUtils claimsFilesUtil = new FilesUtils(this, "claims");
    private final BlockPlaceListener blockPlaceListener = new BlockPlaceListener(this);
    private final BlockBreakListener blockBreakListener = new BlockBreakListener(this);
    private final CommandsManager commandsManager = new CommandsManager(this);
    private final ConfigUtils configUtils = new ConfigUtils(this);

    @Override
    public void onEnable() {
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

    public HashMap<UUID, Faction> getFactionsHashMap() {
        return factionsHashMap;
    }

    public HashMap<UUID, FilesUtils> getFactionsFilesHashMap() {
        return factionsFilesHashMap;
    }

    public FilesUtils getClaimsFilesUtil() {
        return claimsFilesUtil;
    }

    public ClaimsManager getClaimsManager() {
        return claimsManager;
    }

    public ConfigUtils getConfigUtils() {
        return configUtils;
    }

    //Private methods
    private final PluginManager pluginManager = this.getServer().getPluginManager();
    private void setEventsListeners() {
        pluginManager.registerEvents(blockPlaceListener, this);
        pluginManager.registerEvents(blockBreakListener, this);
    }

    private void loadFactionsData() throws IOException {
        File factionsDirectory = new File(this.getDataFolder() + File.separator + "factions");
        if (!factionsDirectory.exists()) return;

        for (File factionFile : factionsDirectory.listFiles()) {
            String factionId = factionFile.getName().replace(".yml", "");
            FilesUtils factionFilesUtil = new FilesUtils(this, factionId);
            factionFilesUtil.createFactionsDataFile();
            factionsFilesHashMap.put(UUID.fromString(factionId), factionFilesUtil);
        }

        for (UUID id : factionsFilesHashMap.keySet()) {
            Faction faction = new Faction(this);
            LoadSaveFactionData.loadFactionData(factionsFilesHashMap.get(id), faction);
            factionsHashMap.put(id, faction);
        }
    }

    private void saveFactionsData() throws IOException {
        for (UUID id : factionsHashMap.keySet()) {
            if (!factionsFilesHashMap.containsKey(id) || factionsFilesHashMap.get(id) == null) {
                FilesUtils file = new FilesUtils(this, id.toString());
                file.createFactionsDataFile();
                LoadSaveFactionData.saveFactionData(file, factionsHashMap.get(id));
            }
            else LoadSaveFactionData.saveFactionData(factionsFilesHashMap.get(id), factionsHashMap.get(id));
        }
    }

    private void loadClaimsData() throws IOException {
        File factionsDirectory = this.getDataFolder();
        if (!factionsDirectory.exists()) return;
        claimsFilesUtil.createClaimsDataFile();
        LoadSaveClaimsData.loadClaimsData(claimsFilesUtil, claimsManager);
    }

    private void saveClaimsData() throws IOException {
        claimsFilesUtil.createClaimsDataFile();
        LoadSaveClaimsData.saveClaimsData(claimsFilesUtil, claimsManager);
    }
}
