package com.github.mikoli.rvfactions.dynmap;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.Claim;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimType;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;

import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkerApiManager {

    private final RVFactions plugin;
    private final MarkerSet markerSet;
    private final HashMap<Faction, FactionStyles> factionStylesMap = new HashMap<>();
    private final AreaStyle neutralOutpostStyle = new AreaStyle(0.5, 0x05ff8f, 0.5, 0x05ff8f, "Neutral outpost", "Neutral outpost to capture.");
    private final HashMap<Claim, AreaMarker> claimsMarkersMap = new HashMap<>();

    public MarkerApiManager(RVFactions plugin, DynmapAPI dynmapAPI) {
        this.plugin = plugin;
        this.markerSet = dynmapAPI.getMarkerAPI().createMarkerSet("factions.markerset", "Factions", dynmapAPI.getMarkerAPI().getMarkerIcons(), false);

        if (markerSet != null) {
            generateFactionStyles();
            generateClaimsStyles();
        }
    }

    public AreaMarker getClaimMarker(Claim claim) {
        return claimsMarkersMap.get(claim);
    }

    public FactionStyles getFactionStyles(Faction faction) {
        return factionStylesMap.get(faction);
    }

    public void generateFactionStyles() {
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) createFactionStyles(faction);
    }

    public void createFactionStyles(Faction faction) {
        ChatColor color = faction.getColor();
        HexColors hexColor = HexColors.valueOf(color.name());
        int intColor = hexColor.getHexColor();
        AreaStyle claimStyle = new AreaStyle(0.2, intColor, 0.5, intColor, "Claim", faction.getName() + "'s claim");
        AreaStyle outpostStyle = new AreaStyle(0.5, intColor, 0.5, intColor, "Outpost", faction.getName() + "'s outpost");
        AreaStyle coreStyle = new AreaStyle(0.7, intColor, 0.5, intColor, "Core", faction.getName() + "'s core");

        FactionStyles factionStyles = new FactionStyles(claimStyle, outpostStyle, coreStyle);
        factionStylesMap.put(faction, factionStyles);
    }

    public void updateFactionClaimStyle(Faction faction, HexColors hexColor) {
        int intColor = hexColor.getHexColor();
        factionStylesMap.get(faction).setClaimStyle(new AreaStyle(0.5, intColor, 0.5, intColor, "Claim", faction.getName() + "'s claim"));
    }

    public void updateFactionOutpostStyle(Faction faction, HexColors hexColor) {
        int intColor = hexColor.getHexColor();
        factionStylesMap.get(faction).setClaimStyle(new AreaStyle(0.5, intColor, 0.5, intColor, "Outpost", faction.getName() + "'s outpost"));
    }

    public void updateFactionCoreStyle(Faction faction, HexColors hexColor) {
        int intColor = hexColor.getHexColor();
        factionStylesMap.get(faction).setClaimStyle(new AreaStyle(0.5, intColor, 0.5, intColor, "Core", faction.getName() + "'s core"));
    }

    public void removeFactionStyles(Faction faction) {
        factionStylesMap.remove(faction);
    }

    public void generateClaimsStyles() {
        for (Claim claim : plugin.getClaimsManager().getClaimsList().values()) createClaimStyle(claim);
    }

    public void createClaimStyle(Claim claim) {
        ArrayList<int[]> perimeter = getCorners(claim);
        double[] x = getAxisArray(perimeter, true);
        double[] z = getAxisArray(perimeter, false);

        AreaMarker areaMarker = markerSet.createAreaMarker(claim.getId() + ".marker", claim.getId().toString(), false, "world", x, z, false);
        claimsMarkersMap.put(claim, areaMarker);
        if (claim.getClaimType() == ClaimType.NEUTRAL) updateClaimStyle(claim, neutralOutpostStyle);
        else updateClaimStyle(claim);
    }

    public void updateClaimArea(Claim claim) {
        AreaMarker areaMarker = claimsMarkersMap.get(claim);
        ArrayList<int[]> perimeter = getCorners(claim);
        double[] x = getAxisArray(perimeter, true);
        double[] z = getAxisArray(perimeter, false);
        areaMarker.setCornerLocations(x, z);
    }

    public void updateClaimStyle(Claim claim) {
        AreaMarker areaMarker = claimsMarkersMap.get(claim);
        AreaStyle areaStyle = getFactionStyles(plugin.getFactionsManager().getFactionByUUID(claim.getOwner())).getFactionStyle(claim.getClaimType());
        areaMarker.setFillStyle(areaStyle.getFillOpacity(), areaStyle.getFillColor());
        areaMarker.setLabel(areaStyle.getLabel());
        areaMarker.setDescription(areaStyle.getDescription());
    }

    public void updateClaimStyle(Claim claim, AreaStyle areaStyle) {
        AreaMarker areaMarker = claimsMarkersMap.get(claim);
        areaMarker.setFillStyle(areaStyle.getFillOpacity(), areaStyle.getFillColor());
        areaMarker.setLabel(areaStyle.getLabel());
        areaMarker.setDescription(areaStyle.getDescription());
    }

    public void removeClaimStyle(Claim claim) {
        claimsMarkersMap.get(claim).deleteMarker();
        claimsMarkersMap.remove(claim);
    }

    public void updateFactionClaimStyles(Faction faction) {
        for (Claim claim : plugin.getClaimsManager().getClaimsList().values())
            if (claim.getOwner().equals(faction.getId())) updateClaimStyle(claim);
    }

    private ArrayList<int[]> getCorners(Claim claim) {
        ClaimType claimType = claim.getClaimType();
        int range = claimType.getRange();
        Chunk coreChunk = plugin.getServer().getWorld("world").getChunkAt(claim.getCoreLocation());

        int initX = coreChunk.getX() - range;
        int initZ = coreChunk.getZ() - range;
        for (Chunk chunk : claim.getChunksSet()) {
            if (chunk.getX() <= initX && chunk.getZ() <= initZ) {
                initX = chunk.getX();
                initZ = chunk.getZ();
            }
        }

        int curX = initX;
        int curZ = initZ;
        Direction direction = Direction.XPLUS;
        ArrayList<int[]> perimeter = new ArrayList<>();
        perimeter.add(new int[] { initX, initZ });
        while (curX != initX || curZ != initZ || direction != Direction.ZMINUS) {
            switch (direction) {
                case XPLUS:
                    if (!isClaimed(claim, coreChunk, curX + 1, curZ)) {
                        perimeter.add(new int[] { curX + 1, curZ });
                        direction = Direction.ZPLUS;
                        continue;
                    }
                    if (!isClaimed(claim, coreChunk, curX + 1, curZ - 1)) {
                        curX++;
                        continue;
                    }
                    perimeter.add(new int[] { curX + 1, curZ });
                    direction = Direction.ZMINUS;
                    curX++;
                    curZ--;
                    continue;
                case ZPLUS:
                    if (!isClaimed(claim, coreChunk, curX, curZ + 1)) {
                        perimeter.add(new int[] { curX + 1, curZ + 1 });
                        direction = Direction.XMINUS;
                        continue;
                    }
                    if (!isClaimed(claim, coreChunk, curX + 1, curZ + 1)) {
                        curZ++;
                        continue;
                    }
                    perimeter.add(new int[] { curX + 1, curZ + 1 });
                    direction = Direction.XPLUS;
                    curX++;
                    curZ++;
                    continue;
                case XMINUS:
                    if (!isClaimed(claim, coreChunk, curX - 1, curZ)) {
                        perimeter.add(new int[] { curX, curZ + 1 });
                        direction = Direction.ZMINUS;
                        continue;
                    }
                    if (!isClaimed(claim, coreChunk, curX - 1, curZ + 1)) {
                        curX--;
                        continue;
                    }
                    perimeter.add(new int[] { curX, curZ + 1 });
                    direction = Direction.ZPLUS;
                    curX--;
                    curZ++;
                    continue;
                case ZMINUS:
                    if (!isClaimed(claim, coreChunk, curX, curZ - 1)) {
                        perimeter.add(new int[] { curX, curZ });
                        direction = Direction.XPLUS;
                        continue;
                    }
                    if (!isClaimed(claim, coreChunk, curX - 1, curZ - 1)) {
                        curZ--;
                        continue;
                    }
                    perimeter.add(new int[] { curX, curZ });
                    direction = Direction.XMINUS;
                    curX--;
                    curZ--;
            }
        }
        return perimeter;
    }

    private double[] getAxisArray(ArrayList<int[]> perimeter, boolean axis) {
        int sz = perimeter.size();
        double[] toReturn = new double[sz];
        for (int i = 0; i < sz; i++) {
            int[] line = perimeter.get(i);
            if (axis) toReturn[i] = line[0] * 16.0D;
            else toReturn[i] = line[1] * 16.0D;
        }
        return toReturn;
    }

    private boolean isClaimed(Claim claim, Chunk coreChunk, int x, int z) {
        Chunk chunk = coreChunk.getWorld().getChunkAt(x, z);
        return claim.isChunkPartOfClaim(chunk);
    }
}
