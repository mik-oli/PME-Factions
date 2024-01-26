package com.github.mikoli.krolikcraft.dynmap;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.Claim;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;

import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.HashMap;

public class MarkerApiManager {

    private final PMEFactions plugin;
    private final MarkerSet markerSet;
    private final HashMap<Faction, FactionStyles> factionStylesMap = new HashMap<>();
    private final AreaStyle neutralOutpostStyle = new AreaStyle(0.5, 0x05ff8f, 0.5, 0x05ff8f, "Neutral outpost", "Neutral outpost to capture.");
    private final HashMap<Claim, AreaMarker> claimsMarkersMap = new HashMap<>();

    public MarkerApiManager(PMEFactions plugin, DynmapAPI dynmapAPI) {
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
        AreaStyle claimStyle = new AreaStyle(0.5, intColor, 0.5, intColor, "Claim", faction.getName() + "'s claim");
        AreaStyle outpostStyle = new AreaStyle(0.5, 0xd48311, 0.5, 0xd48311, "Outpost", faction.getName() + "'s outpost");
        AreaStyle coreStyle = new AreaStyle(0.5, 0xd48311, 0.5, 0xd48311, "Core", faction.getName() + "'s core");

        FactionStyles factionStyles = new FactionStyles(claimStyle, outpostStyle, coreStyle);
        factionStylesMap.put(faction, factionStyles);
    }

    public void updateFactionClaimStyle(Faction faction, HexColors hexColor) {
        int intColor = hexColor.getHexColor();
        factionStylesMap.get(faction).setClaimStyle(new AreaStyle(0.5, intColor, 0.5, intColor, "Claim", faction.getName() + "'s claim"));
    }

    public void updateFactionOutpostStyle(Faction faction, HexColors hexColor) {
        int intColor = hexColor.getHexColor();
        factionStylesMap.get(faction).setClaimStyle(new AreaStyle(0.5, 0xd48311, 0.5, 0xd48311, "Outpost", faction.getName() + "'s outpost"));
    }

    public void updateFactionCoreStyle(Faction faction, HexColors hexColor) {
        int intColor = hexColor.getHexColor();
        factionStylesMap.get(faction).setClaimStyle(new AreaStyle(0.5, 0xd48311, 0.5, 0xd48311, "Core", faction.getName() + "'s core"));
    }

    public void removeFactionStyles(Faction faction) {
        factionStylesMap.remove(faction);
    }

    public void generateClaimsStyles() {
        for (Claim claim : plugin.getClaimsManager().getClaimsList().values()) createClaimStyle(claim);
    }

    public void createClaimStyle(Claim claim) {
        ClaimType claimType = claim.getClaimType();
        int range = claimType.getRange();
        Chunk coreChunk = plugin.getServer().getWorld("world").getChunkAt(claim.getCoreLocation());
        Chunk topLeftChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() - range, coreChunk.getZ() - range);
        Chunk bottomRightChunk = coreChunk.getWorld().getChunkAt(coreChunk.getX() + range, coreChunk.getZ() + range);
        if (!claim.getChunksSet().contains(topLeftChunk) || !claim.getChunksSet().contains(bottomRightChunk)) return; //TODO xDD??

        int topX = (topLeftChunk.getX()*16);
        int topZ = (topLeftChunk.getZ()*16);
        int bottomX = (bottomRightChunk.getX()*16)+15;
        int bottomZ = (bottomRightChunk.getZ()*16)+15;

        AreaStyle areaStyle = null;
        if (claimType == ClaimType.NEUTRAL) areaStyle = neutralOutpostStyle;
        else {
            Faction faction = plugin.getFactionsManager().getFactionByUUID(claim.getOwner());
            areaStyle = factionStylesMap.get(faction).getFactionStyle(claimType);
        }

        AreaMarker areaMarker = markerSet.createAreaMarker(claim.getId() + ".marker", claim.getId().toString(), false, "world",
                new double[] {topX, bottomX}, new double[] {topZ, bottomZ}, false);
        updateClaimStyle(areaMarker, areaStyle);
        claimsMarkersMap.put(claim, areaMarker);
    }

    public void updateClaimStyle(AreaMarker areaMarker, AreaStyle areaStyle) {
        areaMarker.setFillStyle(areaStyle.getFillOpacity(), areaStyle.getFillColor());
//        areaMarker.setLineStyle(areaStyle.getLineColor(), areaStyle.getLineOpacity(), areaStyle.getLineColor());
        areaMarker.setLabel(areaStyle.getLabel());
        areaMarker.setDescription(areaStyle.getDescription());
    }

    public void removeClaimStyle(Claim claim) {
        claimsMarkersMap.get(claim).deleteMarker();
        claimsMarkersMap.remove(claim);
    }

    public void updateFactionClaimStyles(Faction faction) {

        for (Claim claim : plugin.getClaimsManager().getClaimsList().values()) {
            if (claim.getOwner().equals(faction.getId())) updateClaimStyle(claimsMarkersMap.get(claim), factionStylesMap.get(faction).getFactionStyle(claim.getClaimType()));
        }
    }
}
