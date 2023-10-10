package com.github.mikoli.krolikcraft.claims;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.factions.Faction;
import org.bukkit.Chunk;

import java.util.*;

public class ClaimsManager {

    private final Krolikcraft plugin;
    private final Set<UUID> claimsList = new HashSet<>();
    private final HashMap<UUID, UUID> claimsOwnerMap = new HashMap<>();
    private final HashMap<UUID, Set<Chunk>> claimsChunksMap = new HashMap<>();
    private final HashMap<UUID, ClaimType> claimsTypesMap = new HashMap<>();

    public ClaimsManager(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public Set<UUID> getClaimsList() {
        return claimsList;
    }

    public HashMap<UUID, UUID> getClaimsOwnerMap() {
        return claimsOwnerMap;
    }

    public HashMap<UUID, Set<Chunk>> getClaimsChunksMap() {
        return claimsChunksMap;
    }

    public HashMap<UUID, ClaimType> getClaimsTypesMap() {
        return claimsTypesMap;
    }

    public UUID getClaimId(Chunk inputChunk) {
        for (UUID uuid : claimsChunksMap.keySet())
            if (claimsChunksMap.get(uuid).contains(inputChunk)) return uuid;

        return null;
    }

    public boolean isChunkClaimed(Chunk inputChunk) {
        return this.getClaimId(inputChunk) != null;
    }

    public void createClaim(Faction faction, Set<Chunk> inputChunks, ClaimType claimType) {
        UUID claimId = UUID.randomUUID();
        while (claimsList.contains(claimId)) claimId = UUID.randomUUID();
        claimsList.add(claimId);
        claimsOwnerMap.put(claimId, faction.getId());
        claimsTypesMap.put(claimId, claimType);
        claimsChunksMap.put(claimId, inputChunks);
    }

    public void removeClaim(UUID claimId) {
        claimsList.remove(claimId);
        claimsOwnerMap.remove(claimId);
        claimsChunksMap.remove(claimId);
        claimsTypesMap.remove(claimId);
    }

    public void addChunkToClaim(UUID claimId, Chunk chunk) {
        claimsChunksMap.get(claimId).add(chunk);
    }

    public Faction getClaimOwner(Chunk chunk) {
        return plugin.getFactionsHashMap().get(this.getClaimsOwnerMap().get(this.getClaimId(chunk)));
    }

    public void changeClaimOwner(UUID claimId, Faction faction) {
        claimsOwnerMap.replace(claimId, faction.getId());
    }

    public boolean checkIfCanCreateClaim(Faction faction, Chunk coreChunk, int range, Boolean connected) {
        Chunk topLeftChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() + range, coreChunk.getZ() + range);
        Chunk bottomRightChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() - range, coreChunk.getZ() - range);

        //checking if claim not overlaps other claim
        for (int i = topLeftChunk.getX(); i <= bottomRightChunk.getX(); i++) {
            for (int j = topLeftChunk.getZ(); j >= bottomRightChunk.getZ(); j--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i, j);
                if (this.isChunkClaimed(tempChunk)) return false;
            }
        }

        //checking if is connected
        if (!connected) {
            for (int i = topLeftChunk.getX(); i <= bottomRightChunk.getX(); i++) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i + 1, topLeftChunk.getZ());
                if (this.getClaimOwner(tempChunk) == faction) return true;
            }
            for (int i = bottomRightChunk.getX(); i >= topLeftChunk.getX(); i--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i - 1, topLeftChunk.getZ());
                if (this.getClaimOwner(tempChunk) == faction) return true;
            }
            for (int i = topLeftChunk.getZ(); i >= bottomRightChunk.getZ(); i--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(topLeftChunk.getX(), i - 1);
                if (this.getClaimOwner(tempChunk) == faction) return true;
            }
            for (int i = bottomRightChunk.getZ(); i <= topLeftChunk.getZ(); i++) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(topLeftChunk.getX(), i + 1);
                if (this.getClaimOwner(tempChunk) == faction) return true;
            }
        }

        return true;
    }
}
