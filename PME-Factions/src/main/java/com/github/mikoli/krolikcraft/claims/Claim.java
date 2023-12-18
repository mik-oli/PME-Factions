package com.github.mikoli.krolikcraft.claims;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Claim {

    private final UUID claimId;
    private final ClaimType claimType;
    private UUID claimOwner;
    private Location coreLocation;
    private final Set<Chunk> claimChunksMap = new HashSet<>();

    public Claim(UUID id, ClaimType claimType) {
        this.claimId = id;
        this.claimType = claimType;
    }

    public UUID getClaimId() {
        return this.claimId;
    }

    public ClaimType getClaimType() {
        return this.claimType;
    }

    public void setClaimOwner(UUID newOwner) {
        this.claimOwner = newOwner;
    }

    public UUID getClaimOwner() {
        return this.claimOwner;
    }

    public void setCoreLocation(Location location) {
        this.coreLocation = location;
    }

    public Location getCoreLocation() {
        return this.coreLocation;
    }

    public Set<Chunk> getClaimChunksMap() {
        return this.claimChunksMap;
    }

    public boolean isChunkPartOfClaim(Chunk inputChunk) {
        return this.claimChunksMap.contains(inputChunk);
    }

    public void addChunkToClaim(Chunk inputChunk) {
        this.claimChunksMap.add(inputChunk);
    }

    public void removeChunkFromClaim(Chunk inputChunk) {
        this.claimChunksMap.remove(inputChunk);
    }
}
