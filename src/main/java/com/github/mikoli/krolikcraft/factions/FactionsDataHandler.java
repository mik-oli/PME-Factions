package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FactionsDataHandler {

    public static void loadFactionsData(PMEFactions plugin) throws IOException {

        File factionsDirectory = new File(plugin.getDataFolder() + File.separator + "factions");
        if (!factionsDirectory.exists()) return;
        for (File factionFile : factionsDirectory.listFiles()) {
            String factionId = factionFile.getName().replace(".yml", "");
            FilesUtils factionFilesUtil = new FilesUtils(plugin, factionId);
            factionFilesUtil.loadFactionsDataFile();

            Faction faction = loadFactionData(factionFilesUtil);
            faction.setDataFile(factionFilesUtil);
            plugin.getFactionsManager().getFactionsList().put(UUID.fromString(factionId), faction);
        }
    }

    public static void saveFactionsData(PMEFactions plugin) throws IOException {

        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            if (faction.getDataFile() == null) {
                FilesUtils file = new FilesUtils(plugin, faction.getId().toString());
                file.loadFactionsDataFile();
                faction.setDataFile(file);
                saveFactionData(faction);
            }
            else saveFactionData(faction);
        }
    }

    public static void removeFactionData(Faction faction) {
        faction.getDataFile().deleteFile();
    }

    private static Faction loadFactionData(FilesUtils file) {
        FileConfiguration dataFile = file.getData();
        Faction faction = new Faction(UUID.fromString(dataFile.getString("id")));
        faction.setName(dataFile.getString("name"));
        faction.setShortcut(dataFile.getString("shortcut"));
        faction.setColor(ChatColor.getByChar(dataFile.getString("color")));
        faction.setLeader(UUID.fromString(dataFile.getString("leader")));

        for (String s : dataFile.getStringList("officers")) {
            faction.getOfficers().add(UUID.fromString(s));
        }

        for (String s : dataFile.getStringList("members")) {
            faction.addMember(UUID.fromString(s));
        }

        for (String s : dataFile.getStringList("enemies")) {
            faction.getEnemies().add(UUID.fromString(s));
        }

        String[] coreCords = dataFile.getString("core-location").split(";");
        Location coreLocation = new Location(Bukkit.getWorld("world"), Integer.parseInt(coreCords[0]), Integer.parseInt(coreCords[1]), Integer.parseInt(coreCords[2]));
        faction.setCoreLocation(coreLocation);

        return faction;
    }

    private static void saveFactionData(Faction faction) throws IOException {
        FileConfiguration dataFile = faction.getDataFile().getData();
        dataFile.set("id", faction.getId().toString());
        dataFile.set("name", faction.getName());
        dataFile.set("shortcut", faction.getShortcut());
        dataFile.set("color", faction.getColor().toString().substring(1));
        dataFile.set("leader", faction.getLeader().toString());
        dataFile.set("core-location", (int)faction.getCoreLocation().getX() + ";" + (int)faction.getCoreLocation().getY() + ";" + (int)faction.getCoreLocation().getZ());

        List<String> officersToSave = new ArrayList<>();
        for (UUID playerUUID : faction.getOfficers()) {
            officersToSave.add(playerUUID.toString());
        }
        dataFile.set("officers", officersToSave);

        List<String> membersToSave = new ArrayList<>();
        for (UUID playerUUID : faction.getMembers()) {
            membersToSave.add(playerUUID.toString());
        }
        dataFile.set("members", membersToSave);

        List<String> enemiesToSave = new ArrayList<>();
        for (UUID enemyId : faction.getEnemies()) {
            enemiesToSave.add(enemyId.toString());
        }
        dataFile.set("enemies", enemiesToSave);

        faction.getDataFile().saveData();
    }
}
