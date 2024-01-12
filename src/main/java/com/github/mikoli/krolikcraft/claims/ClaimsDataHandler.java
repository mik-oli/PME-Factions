package com.github.mikoli.krolikcraft.claims;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimsDataHandler {

    public static void loadClaimsData(FilesUtils file, ClaimsManager claimsManager) throws IOException {

        file.loadClaimsDataFile();
        FileConfiguration dataFile = file.getData();
        for (String s : dataFile.getKeys(false)) {
            UUID uuid = UUID.fromString(s);
            ClaimType claimType = ClaimType.valueOf(dataFile.getString(uuid + ".type"));
            Claim claim = new Claim(uuid, claimType);

            UUID owner = UUID.fromString(dataFile.getString(uuid + ".owner"));
            claim.setOwner(owner);

            String[] coreCords = dataFile.getString(uuid + ".core-location").split(";");
            Location claimCoreLocation = new Location(Bukkit.getWorld("world"), Double.parseDouble(coreCords[0]), Double.parseDouble(coreCords[1]), Double.parseDouble(coreCords[2]));
            claim.setCoreLocation(claimCoreLocation);

            for (String cords : dataFile.getStringList(uuid + ".chunks")) {
                String[] splitCords = cords.split(",");
                int chunkX = Integer.parseInt(splitCords[0]);
                int chunkZ = Integer.parseInt(splitCords[1]);
                Chunk chunk = Bukkit.getWorld("world").getChunkAt(chunkX, chunkZ);
                claim.addChunkToClaim(chunk);
            }
            claimsManager.getClaimsList().put(uuid, claim);
        }
    }

    public static void saveClaimsData(FilesUtils file, ClaimsManager claimsManager) throws IOException {

        file.loadClaimsDataFile();
        FileConfiguration dataFile = file.getData();
        for (Claim claim : claimsManager.getClaimsList().values()) {
            dataFile.set(claim.getId() + ".owner", (claim.getOwner() != null) ? (claim.getOwner().toString()) : "none");
            dataFile.set(claim.getId() + ".type", claim.getClaimType().toString());
            dataFile.set(claim.getId() + ".core-location", (int)claim.getCoreLocation().getX() + ";" + (int)claim.getCoreLocation().getY() + ";" + (int)claim.getCoreLocation().getZ());
            List<String> chunksCord = new ArrayList<>();
            for (Chunk chunk : claim.getChunksSet()) {
                chunksCord.add(chunk.getX() + "," + chunk.getZ());
            }
            dataFile.set(claim.getId() + ".chunks", chunksCord);
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
