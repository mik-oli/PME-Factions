package com.github.mikoli.krolikcraft.claims;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factions.Faction;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.*;

public class ClaimsManager {

    private final PMEFactions plugin;
    private final HashMap<UUID, Claim> claimsList = new HashMap<>();
//    private final Set<UUID> claimsList = new HashSet<>();
//    private final HashMap<UUID, UUID> claimsOwnerMap = new HashMap<>();
//    private final HashMap<UUID, Set<Chunk>> claimsChunksMap = new HashMap<>();
//    private final HashMap<UUID, ClaimType> claimsTypesMap = new HashMap<>();
//    private final HashMap<UUID, Location> claimCoreLocation = new HashMap<>();

    public ClaimsManager(PMEFactions plugin) {
        this.plugin = plugin;
    }

//    public Set<UUID> getClaimsList() {
//        return claimsList;
//    }
//
//    public HashMap<UUID, UUID> getClaimsOwnerMap() {
//        return claimsOwnerMap;
//    }
//
//    public HashMap<UUID, Set<Chunk>> getClaimsChunksMap() {
//        return claimsChunksMap;
//    }
//
//    public HashMap<UUID, ClaimType> getClaimsTypesMap() {
//        return claimsTypesMap;
//    }
//
//    public HashMap<UUID, Location> getClaimCoreLocation() {
//        return claimCoreLocation;
//    }

    public HashMap<UUID, Claim> getClaimsList() {
        return this.claimsList;
    }

    public void removeClaim(UUID claimId) {
        claimsList.remove(claimId);
    }

    public UUID getClaimId(Chunk inputChunk) {
        for (Claim claim : claimsList.values()) {
            if (claim.isChunkPartOfClaim(inputChunk)) return claim.getClaimId();
        }
        return null;
    }

//    public UUID getClaimId(Chunk inputChunk) {
//        for (UUID uuid : claimsChunksMap.keySet())
//            if (claimsChunksMap.get(uuid).contains(inputChunk)) return uuid;
//
//        return null;
//    }

    public boolean isChunkClaimed(Chunk inputChunk) {
        return this.getClaimId(inputChunk) != null;
    }

//    public void addChunkToClaim(UUID claimId, Chunk chunk) {
//        claimsChunksMap.get(claimId).add(chunk);
//    }

//    public Faction getClaimOwner(Chunk chunk) {
//        return plugin.getFactionsHashMap().get(this.getClaimsOwnerMap().get(this.getClaimId(chunk)));
//    }

    public void changeClaimOwner(Claim claim, Faction faction) {
        claim.setClaimOwner(faction.getId());
    }

    public boolean checkIfCanCreateClaim(Faction faction, Chunk coreChunk, ClaimType claimType, Boolean connected) {
        int range = claimType.getRange();
        Chunk topLeftChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() - range, coreChunk.getZ() + range);
        Chunk bottomRightChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() + range, coreChunk.getZ() - range);

        //checking if claim not overlaps other claim
        for (int i = topLeftChunk.getX(); i <= bottomRightChunk.getX(); i++) {
            for (int j = topLeftChunk.getZ(); j >= bottomRightChunk.getZ(); j--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i, j);
                if (this.isChunkClaimed(tempChunk)) return false;
            }
        }

        //checking if is connected
        if (connected) {
            for (int i = topLeftChunk.getX(); i <= bottomRightChunk.getX(); i++) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i + 1, topLeftChunk.getZ());
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim.getClaimOwner() == faction.getId()) return true;
            }
            for (int i = bottomRightChunk.getX(); i >= topLeftChunk.getX(); i--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i - 1, topLeftChunk.getZ());
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim.getClaimOwner() == faction.getId()) return true;
            }
            for (int i = topLeftChunk.getZ(); i >= bottomRightChunk.getZ(); i--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(topLeftChunk.getX(), i - 1);
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim.getClaimOwner() == faction.getId()) return true;
            }
            for (int i = bottomRightChunk.getZ(); i <= topLeftChunk.getZ(); i++) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(topLeftChunk.getX(), i + 1);
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim.getClaimOwner() == faction.getId()) return true;
            }
        }
        return true;
    }

    public void createClaim(Faction faction, Chunk coreChunk, ClaimType claimType, Location coreLocation) {
        UUID claimId = UUID.randomUUID();
        while (claimsList.containsKey(claimId)) claimId = UUID.randomUUID();

        Claim claim = new Claim(claimId, claimType);
        claim.setClaimOwner(faction.getId());
        claim.setCoreLocation(coreLocation);
        claimsList.put(claimId, claim);
//        claimsOwnerMap.put(claimId, faction.getId());
//        claimsTypesMap.put(claimId, claimType);
//        claimCoreLocation.put(claimId, coreLocation);

        int range = claimType.getRange();
//        Set<Chunk> inputChunks = new HashSet<>();
        Chunk topLeftChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() - range, coreChunk.getZ() + range);
        Chunk bottomRightChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() + range, coreChunk.getZ() - range);
        for (int i = topLeftChunk.getX(); i <= bottomRightChunk.getX(); i++) {
            for (int j = topLeftChunk.getZ(); j >= bottomRightChunk.getZ(); j--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i, j);
//                inputChunks.add(tempChunk);
                claim.addChunkToClaim(tempChunk);
            }
        }
//        claimsChunksMap.put(claimId, inputChunks);
    }
}
