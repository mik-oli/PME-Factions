package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadSaveFactionData {

    public static void loadFactionData(FilesUtils file, Faction faction) {
        FileConfiguration dataFile = file.getData();
        faction.setId(UUID.fromString(dataFile.getString("id")));
        faction.setName(dataFile.getString("name"));
        faction.setShortcut(dataFile.getString("shortcut"));
        faction.setColor(ChatColor.valueOf(dataFile.getString("color")));
        faction.setLeader(UUID.fromString(dataFile.getString("leader")));

        for (String s : dataFile.getStringList("members")) {
            faction.addMember(UUID.fromString(s));
        }
        String[] coreCords = dataFile.getString("core-location").split(";");
        Location coreLocation = new Location(Bukkit.getWorld("world"), Integer.parseInt(coreCords[0]), Integer.parseInt(coreCords[1]), Integer.parseInt(coreCords[2]));
        faction.setCoreLocation(coreLocation);
    }

    public static void saveFactionData(FilesUtils file, Faction faction) throws IOException {
        FileConfiguration dataFile = file.getData();
        dataFile.set("id", faction.getId());
        dataFile.set("name", faction.getName());
        dataFile.set("shortcut", faction.getShortcut());
        dataFile.set("color", faction.getColor().toString());
        dataFile.set("leader", faction.getLeader().toString());
        dataFile.set("core-location", faction.getCoreLocation().getX() + ";" + faction.getCoreLocation().getY() + ";" + faction.getCoreLocation().getZ());

        List<String> membersToSave = new ArrayList<>();
        for (UUID playerUUID : faction.getMembers()) {
            membersToSave.add(playerUUID.toString());
        }
        dataFile.set("members", membersToSave);

        file.saveData();
    }
}
