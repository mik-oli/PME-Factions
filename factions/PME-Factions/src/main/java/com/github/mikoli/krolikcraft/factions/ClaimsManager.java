package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import org.bukkit.Location;

import java.util.*;
import java.util.Map.Entry;

public class ClaimsManager {

    private final Krolikcraft plugin;
    private final Set<UUID> claimsList = new HashSet<>();
    private final HashMap<UUID, String> claimsOwnerMap = new HashMap<>();
    private final HashMap<UUID, Entry<Location, Location>> claimsCornersMap = new HashMap<>();
    private final HashMap<UUID, ClaimType> claimsTypesMap = new HashMap<>();

    public ClaimsManager(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public UUID getClaimId(Location inputLocation) {
        for (UUID uuid : claimsCornersMap.keySet()) {
            double inputX = inputLocation.getX();
            double inputZ = inputLocation.getZ();
            Location bottomLeft = claimsCornersMap.get(uuid).getKey();
            Location upperRight = claimsCornersMap.get(uuid).getValue();
            if ((inputX >= bottomLeft.getX() && inputZ >= bottomLeft.getZ()) && (inputX <= upperRight.getX() && inputZ <= upperRight.getZ())) {
                return uuid;
            }
        }
        return null;
    }

    public void createClaim(Faction faction, Location inputLocation, ClaimType claimType) {
        UUID claimId = UUID.randomUUID();
        while (claimsList.contains(claimId)) claimId = UUID.randomUUID();
        claimsList.add(claimId);
        claimsOwnerMap.put(claimId, faction.getName());
        claimsTypesMap.put(claimId, claimType);

        //TODO adding/removing distance basing on config file
        Location bottomLeftLoc = inputLocation.subtract(8, 0, 8);
        Location upperRightLoc = inputLocation.add(8, 0, 8);
        claimsCornersMap.put(claimId, new AbstractMap.SimpleEntry<>(bottomLeftLoc, upperRightLoc));
    }

    public void removeClaim(UUID claimId) {
        claimsList.remove(claimId);
        claimsOwnerMap.remove(claimId);
        claimsCornersMap.remove(claimId);
        claimsTypesMap.remove(claimId);
    }

    public void changeClaimOwner(UUID claimId, Faction faction) {
        claimsOwnerMap.replace(claimId, faction.getName());
    }

    public Set<UUID> getClaimsList() {
        return claimsList;
    }

    public HashMap<UUID, String> getClaimsOwnerMap() {
        return claimsOwnerMap;
    }

    public HashMap<UUID, Entry<Location, Location>> getClaimsCornersMap() {
        return claimsCornersMap;
    }

    public HashMap<UUID, ClaimType> getClaimsTypesMap() {
        return claimsTypesMap;
    }
}
