package com.github.mikoli.krolikcraft.claims;

import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.*;

public class LoadSaveClaimsData {

    public static void loadClaimsData(FilesUtils file, ClaimsManager claimsManager) {
        FileConfiguration dataFile = file.getData();

        for (String s : dataFile.getKeys(false)) {
            UUID uuid = UUID.fromString(s);
            claimsManager.getClaimsList().add(uuid);
            UUID owner = UUID.fromString(dataFile.getString(uuid + ".owner"));
            claimsManager.getClaimsOwnerMap().put(uuid, owner);
            ClaimType claimType = ClaimType.valueOf(dataFile.getString(uuid + ".type"));
            claimsManager.getClaimsTypesMap().put(uuid, claimType);
            String[] coreCords = dataFile.getString(uuid + ".core-location").split(";");
            Location claimCoreLocation = new Location(Bukkit.getWorld("world"), Integer.parseInt(coreCords[0]), Integer.parseInt(coreCords[1]), Integer.parseInt(coreCords[2]));
            claimsManager.getClaimCoreLocation().put(uuid, claimCoreLocation);
            Set<Chunk> chunksList = new HashSet<>();
            for (String cords : dataFile.getStringList(uuid + ".chunks")) {
                String[] splitCords = cords.split(",");
                int chunkX = Integer.parseInt(splitCords[0]);
                int chunkZ = Integer.parseInt(splitCords[1]);
                chunksList.add(Bukkit.getWorld("world").getChunkAt(chunkX, chunkZ));
            }
            claimsManager.getClaimsChunksMap().put(uuid, chunksList);
        }
    }

    public static void saveClaimsData(FilesUtils file, ClaimsManager claimsManager) throws IOException {
        FileConfiguration dataFile = file.getData();
        for (UUID id : claimsManager.getClaimsList()) {
            dataFile.set(id + ".owner", claimsManager.getClaimsOwnerMap().get(id).toString());
            dataFile.set(id + ".type", claimsManager.getClaimsTypesMap().get(id).toString());
            dataFile.set(id + ".core-location", claimsManager.getClaimCoreLocation().get(id).getX() + ";" + claimsManager.getClaimCoreLocation().get(id).getY() + ";" + claimsManager.getClaimCoreLocation().get(id).getZ());
            List<String> chunksCord = new ArrayList<>();
            for (Chunk chunk : claimsManager.getClaimsChunksMap().get(id)) {
                chunksCord.add(chunk.getX() + "," + chunk.getZ());
            }
            dataFile.set(id + ".chunks", chunksCord);
        }
        file.saveData();
    }

    public static void deleteClaimFromFile(FilesUtils file, UUID claimId) {
        FileConfiguration dataFile = file.getData();
        dataFile.set(claimId.toString(), null);
        try {
            file.saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
