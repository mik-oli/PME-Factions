package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadSaveFactionData {

    public static void loadFactionData(FilesUtils file, Faction faction) {
        FileConfiguration dataFile = file.getData();
        faction.setId(UUID.fromString(dataFile.getString("id")));
        faction.setName(dataFile.getString("name"));
        faction.setLeader(UUID.fromString(dataFile.getString("leader")));

        for (String s : dataFile.getStringList("members")) {
            faction.addMember(UUID.fromString(s));
        }
    }

    public static void saveFactionData(FilesUtils file, Faction faction) throws IOException {
        FileConfiguration dataFile = file.getData();
        dataFile.set("id", faction.getId());
        dataFile.set("name", faction.getName());
        dataFile.set("leader", faction.getLeader().toString());

        List<String> membersToSave = new ArrayList<>();
        for (UUID playerUUID : faction.getMembers()) {
            membersToSave.add(playerUUID.toString());
        }
        dataFile.set("members", membersToSave);

        file.saveData();
    }
}
