package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.LoadSaveFactionData;
import com.github.mikoli.krolikcraft.utils.FilesUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public final class Krolikcraft extends JavaPlugin {

    private final HashMap<String, Faction> factionsHashMap = new HashMap<>();
    private final HashMap<String, FilesUtils> factionsFilesHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        
        this.loadFactionsData();
    }

    @Override
    public void onDisable() {

        try {
            this.saveFactionsData();
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

    private void loadFactionsData() {
        File factionsDirectory = new File(this.getDataFolder().getAbsolutePath() + File.separator + "factions");
        if (factionsDirectory.exists() || factionsDirectory.listFiles().length < 1) return;

        for (File factionFile : factionsDirectory.listFiles()) {
            String factionName = factionFile.getName().replace(".yml", "");
            factionsFilesHashMap.put(factionName, new FilesUtils(this, factionName));
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
}
