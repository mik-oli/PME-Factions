package com.github.mikoli.ringsvalley.dynmap.listeners;

import com.github.mikoli.ringsvalley.RVFactions;
import com.github.mikoli.ringsvalley.dynmap.HexColors;
import com.github.mikoli.ringsvalley.dynmap.MarkerApiManager;
import com.github.mikoli.ringsvalley.factionsLogic.events.FactionChangeEvent;
import com.github.mikoli.ringsvalley.factionsLogic.factions.Faction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionChangeListener implements Listener {

    private final RVFactions plugin;

    public FactionChangeListener(RVFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFactionChangeEvent(FactionChangeEvent event) {

        MarkerApiManager markerApi = plugin.getMarkerApiManager();
        Faction faction = event.getFaction();
        switch (event.getChangeType()) {
            case CREATE:
                markerApi.createFactionStyles(faction);
                break;
            case UPDATE_COLOR:
                markerApi.updateFactionClaimStyle(faction, HexColors.valueOf(faction.getColor().name()));
                break;
            case UPDATE_NAME:
                this.updateFactionName(faction);
                break;
            case REMOVE:
                markerApi.removeFactionStyles(faction);
                break;
        }
    }

    private void updateFactionName(Faction faction) {
        MarkerApiManager markerApi = plugin.getMarkerApiManager();
        markerApi.updateFactionClaimStyle(faction, HexColors.valueOf(faction.getColor().name()));
        markerApi.updateFactionOutpostStyle(faction, HexColors.valueOf(faction.getColor().name()));
        markerApi.updateFactionCoreStyle(faction, HexColors.valueOf(faction.getColor().name()));
        markerApi.updateFactionClaimStyles(faction);
    }
}
