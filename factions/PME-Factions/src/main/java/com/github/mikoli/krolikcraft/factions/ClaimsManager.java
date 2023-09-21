package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import org.bukkit.Chunk;

import java.util.*;

public class ClaimsManager {

    private final Krolikcraft plugin;
    private final Set<UUID> claimsList = new HashSet<>();
    private final HashMap<UUID, String> claimsOwnerMap = new HashMap<>();
    private final HashMap<UUID, Set<Chunk>> claimsChunksMap = new HashMap<>();
    private final HashMap<UUID, ClaimType> claimsTypesMap = new HashMap<>();

    public ClaimsManager(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public Set<UUID> getClaimsList() {
        return claimsList;
    }

    public HashMap<UUID, String> getClaimsOwnerMap() {
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

    public void createClaim(Faction faction, Chunk inputChunk, ClaimType claimType) {
        UUID claimId = UUID.randomUUID();
        while (claimsList.contains(claimId)) claimId = UUID.randomUUID();
        claimsList.add(claimId);
        claimsOwnerMap.put(claimId, faction.getName());
        claimsTypesMap.put(claimId, claimType);

        Set<Chunk> chunks = new HashSet<>();
        chunks.add(inputChunk);
        claimsChunksMap.put(claimId, chunks);
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

    public void changeClaimOwner(UUID claimId, Faction faction) {
        claimsOwnerMap.replace(claimId, faction.getName());
    }
}
