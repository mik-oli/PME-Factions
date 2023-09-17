package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class LoadSaveFactionData {

    public static void loadFactionData(FilesUtils file, Faction faction) {
        YamlConfiguration dataFile = file.getData();
        Server server = Bukkit.getServer();
        faction.setName(dataFile.getString("name"));
        faction.setLeader(server.getPlayer(dataFile.getString("leader")));

        for (String s : dataFile.getStringList("members")) {
            faction.addMember(server.getPlayer(s));
        }
    }

    public static void saveFactionData(FilesUtils file, Faction faction) throws IOException {
        YamlConfiguration dataFile = file.getData();
        Server server = Bukkit.getServer();
        dataFile.set("name", faction.getName());
        dataFile.set("leader", faction.getLeader().getUniqueId());

        HashSet<UUID> membersToSave = new HashSet<>();
        for (Player player : faction.getMembers()) {
            membersToSave.add(player.getUniqueId());
        }
        dataFile.set("members", membersToSave);

        file.saveData();
    }
}
