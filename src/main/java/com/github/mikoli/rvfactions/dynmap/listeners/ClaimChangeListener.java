package com.github.mikoli.rvfactions.dynmap.listeners;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.Claim;
import com.github.mikoli.rvfactions.dynmap.MarkerApiManager;
import com.github.mikoli.rvfactions.factionsLogic.events.ClaimChangeEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClaimChangeListener implements Listener {

    private final RVFactions plugin;

    public ClaimChangeListener(RVFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClaimChangeEvent(ClaimChangeEvent event) {

        MarkerApiManager markerApi = plugin.getMarkerApiManager();
        Claim claim = event.getClaim();
        switch (event.getChangeType()) {
            case CREATE:
                markerApi.createClaimStyle(claim);
                break;
            case CHANGE_OWNER:
                markerApi.updateClaimStyle(claim);
                break;
            case REMOVE:
                markerApi.removeClaimStyle(claim);
                break;
            case ADD_CHUNK:
                markerApi.updateClaimArea(claim);
        }
    }
}
