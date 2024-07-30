package com.github.mikoli.rvfactions.factionsLogic.claims;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Claim {

    private final UUID id;
    private ClaimType claimType;
    private Location coreLocation;
    private UUID owner;
    private final Set<Chunk> chunksSet = new HashSet<>();

    public Claim(UUID id, ClaimType claimType) {
        this.id = id;
        this.claimType = claimType;
    }

    public UUID getId() {
        return this.id;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public ClaimType getClaimType() {
        return this.claimType;
    }

    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setCoreLocation(Location location) {
        this.coreLocation = location;
    }

    public Location getCoreLocation() {
        return this.coreLocation;
    }

    public Set<Chunk> getChunksSet() {
        return this.chunksSet;
    }

    public boolean isChunkPartOfClaim(Chunk inputChunk) {
        return this.chunksSet.contains(inputChunk);
    }

    public void addChunkToClaim(Chunk inputChunk) {
        this.chunksSet.add(inputChunk);
    }

    public void removeChunkFromClaim(Chunk inputChunk) {
        this.chunksSet.remove(inputChunk);
    }
}
