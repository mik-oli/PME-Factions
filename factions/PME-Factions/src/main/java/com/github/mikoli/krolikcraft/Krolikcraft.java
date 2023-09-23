package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.factions.LoadSaveFactionData;
import com.github.mikoli.krolikcraft.utils.FilesUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtil;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public final class Krolikcraft extends JavaPlugin {

    private final HashMap<String, Faction> factionsHashMap = new HashMap<>();
    private final HashMap<String, FilesUtils> factionsFilesHashMap = new HashMap<>();
    private final FilesUtils claimsFilesUtil = new FilesUtils(this, "claims");
    private final PersistentDataUtil persistentDataUtil = new PersistentDataUtil(this);
    private final ClaimsManager claimsManager = new ClaimsManager(this);

    @Override
    public void onEnable() {
        this.loadFactionsData();
        this.loadFactionsData();
        this.claimsFilesUtil.createClaimsDataFile();
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

    public PersistentDataUtil getPersistentDataUtil() {
        return persistentDataUtil;
    }

    public ClaimsManager getClaimsManager() {
        return claimsManager;
    }

    private void loadFactionsData() {
        File factionsDirectory = new File(this.getDataFolder().getAbsolutePath() + File.separator + "factions");
        if (!factionsDirectory.exists()) return;

        for (File factionFile : factionsDirectory.listFiles()) {
            String factionName = factionFile.getName().replace(".yml", "");
            FilesUtils factionFilesUtil = new FilesUtils(this, factionName);
            factionFilesUtil.createClaimsDataFile();
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
            LoadSaveFactionData.saveFactionData(factionsFilesHashMap.get(str), factionsHashMap.get(str));
        }
    }

    private void loadClaimsData() {
        File factionsDirectory = new File(this.getDataFolder().getAbsolutePath() + File.separator + "factions");
        if (!factionsDirectory.exists()) return;

        LoadSaveClaimsData.loadFClaimsData(claimsFilesUtil, claimsManager);
    }

    private void saveClaimsData() throws IOException {
        LoadSaveClaimsData.saveClaimsData(claimsFilesUtil, claimsManager);
    }
}
