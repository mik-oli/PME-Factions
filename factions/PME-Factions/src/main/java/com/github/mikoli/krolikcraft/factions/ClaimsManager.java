package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
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

    public void changeClaimOwner(UUID claimId, Faction faction) {
        claimsOwnerMap.replace(claimId, faction.getId());
    }

    public boolean checkIfCanCreateClaim(Faction faction, Chunk inputChunk, Boolean connected) {
        //if claim covers other claim
        if (this.getClaimId(inputChunk) != null) return false;

        //if claim borders other claim
        //TODO if must be connected, config file option
        if (!connected) return true;
        int inputX = inputChunk.getX();
        int inputZ = inputChunk.getZ();

        UUID id;
        id = this.getClaimId(inputChunk.getWorld().getChunkAt(inputX + 1, inputZ));
        if (id != null && this.getClaimsOwnerMap().get(id) == faction.getId()) return true;
        id = this.getClaimId(inputChunk.getWorld().getChunkAt(inputX, inputZ - 1));
        if (id != null && this.getClaimsOwnerMap().get(id) == faction.getId()) return true;
        id = this.getClaimId(inputChunk.getWorld().getChunkAt(inputX, inputZ + 1));
        if (id != null && this.getClaimsOwnerMap().get(id) == faction.getId()) return true;
        id = this.getClaimId(inputChunk.getWorld().getChunkAt(inputX - 1, inputZ));
        if (id != null && this.getClaimsOwnerMap().get(id) == faction.getId()) return true;
        else return false;
    }
}
