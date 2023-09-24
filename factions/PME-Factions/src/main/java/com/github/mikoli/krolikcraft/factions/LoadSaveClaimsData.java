package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LoadSaveClaimsData {

    public static void loadFClaimsData(FilesUtils file, ClaimsManager claimsManager) {
        FileConfiguration dataFile = file.getData();

        for (String s : dataFile.getStringList("claims")) {
            UUID uuid = UUID.fromString(s);
            claimsManager.getClaimsList().add(uuid);
            String owner = dataFile.getString("claims." + s + ".owner");
            claimsManager.getClaimsOwnerMap().put(uuid, owner);
            ClaimType claimType = ClaimType.valueOf(dataFile.getString("claims." + s + ".type"));
            claimsManager.getClaimsTypesMap().put(uuid, claimType);
            Set<Chunk> chunksList = new HashSet<>();
            for (String cords : dataFile.getStringList("claims." + s + ".chunks")) {
                int chunkX = Integer.getInteger(cords.split(",")[0]);
                int chunkZ = Integer.getInteger(cords.split(",")[1]);
                chunksList.add(Bukkit.getWorld("world").getChunkAt(chunkX, chunkZ));
            }
            claimsManager.getClaimsChunksMap().put(uuid, chunksList);
        }
    }

    public static void saveClaimsData(FilesUtils file, ClaimsManager claimsManager) throws IOException {
        FileConfiguration dataFile = file.getData();
        for (UUID id : claimsManager.getClaimsList()) {
            dataFile.set("claims." + id + ".owner", claimsManager.getClaimsOwnerMap().get(id));
            dataFile.set("claims." + id + ".type", claimsManager.getClaimsTypesMap().get(id));
            Set<String> chunksCord = new HashSet<>();
            for (Chunk chunk : claimsManager.getClaimsChunksMap().get(id)) {
                chunksCord.add(chunk.getX() + "," + chunk.getZ());
            }
            dataFile.set("claims." + id + ".chunks", chunksCord);
        }
        file.saveData();
    }
}
