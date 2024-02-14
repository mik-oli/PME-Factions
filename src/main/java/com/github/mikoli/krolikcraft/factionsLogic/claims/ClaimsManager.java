package com.github.mikoli.krolikcraft.factionsLogic.claims;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.events.ClaimChangeEvent;
import com.github.mikoli.krolikcraft.factionsLogic.events.ClaimChangeType;
import com.github.mikoli.krolikcraft.factionsLogic.factions.Faction;

import com.github.mikoli.krolikcraft.factionsLogic.utils.BukkitUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.UUID;

public class ClaimsManager {

    private final PMEFactions plugin;
    private final HashMap<UUID, Claim> claimsList = new HashMap<>();

    public ClaimsManager(PMEFactions plugin) {
        this.plugin = plugin;
    }

    public boolean isChunkClaimed(Chunk inputChunk) {
        return this.getClaimId(inputChunk) != null;
    }

    public UUID getClaimId(Chunk inputChunk) {
        for (Claim claim : claimsList.values()) {
            if (claim.isChunkPartOfClaim(inputChunk)) return claim.getId();
        }
        return null;
    }

    public Claim getClaim(UUID id) {
        return claimsList.get(id);
    }

    public HashMap<UUID, Claim> getClaimsList() {
        return this.claimsList;
    }

    public void createClaim(Faction faction, ClaimType claimType, Location coreLocation) {
        UUID claimId = UUID.randomUUID();
        while (claimsList.containsKey(claimId)) claimId = UUID.randomUUID();

        Claim claim = new Claim(claimId, claimType);
        if (faction != null) claim.setOwner(faction.getId());
        claim.setCoreLocation(coreLocation);
        claimsList.put(claimId, claim);

        int range = claimType.getRange();
        Chunk coreChunk = coreLocation.getChunk();
        Chunk topLeftChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() - range, coreChunk.getZ() + range);
        Chunk bottomRightChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() + range, coreChunk.getZ() - range);
        for (int i = topLeftChunk.getX(); i <= bottomRightChunk.getX(); i++) {
            for (int j = topLeftChunk.getZ(); j >= bottomRightChunk.getZ(); j--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i, j);
                claim.addChunkToClaim(tempChunk);
            }
        }

        BukkitUtils.callEvent(new ClaimChangeEvent(claim, ClaimChangeType.CREATE));
    }

    public void changeOwnership(Claim claim, Faction newOwner) {
        claim.setOwner(newOwner.getId());
        BukkitUtils.callEvent(new ClaimChangeEvent(claim, ClaimChangeType.CHANGE_OWNER));
    }


    public void removeClaim(UUID claimId) {
        Claim claim = claimsList.get(claimId);
        claim.getCoreLocation().getBlock().setType(Material.AIR);
        ClaimsDataHandler.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
        claimsList.remove(claimId);

        BukkitUtils.callEvent(new ClaimChangeEvent(claim, ClaimChangeType.REMOVE));
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
                if (claim != null && claim.getOwner().equals(faction.getId())) return true;
            }
            for (int i = bottomRightChunk.getX(); i >= topLeftChunk.getX(); i--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(i - 1, topLeftChunk.getZ());
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim != null && claim.getOwner().equals(faction.getId())) return true;
            }
            for (int i = topLeftChunk.getZ(); i >= bottomRightChunk.getZ(); i--) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(topLeftChunk.getX(), i - 1);
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim != null && claim.getOwner().equals(faction.getId())) return true;
            }
            for (int i = bottomRightChunk.getZ(); i <= topLeftChunk.getZ(); i++) {
                Chunk tempChunk = coreChunk.getWorld().getChunkAt(topLeftChunk.getX(), i + 1);
                Claim claim = this.claimsList.get(this.getClaimId(tempChunk));
                if (claim != null && claim.getOwner().equals(faction.getId())) return true;
            }
        }
        return true;
    }
}
