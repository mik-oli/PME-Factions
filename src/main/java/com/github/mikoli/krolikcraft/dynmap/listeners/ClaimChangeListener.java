package com.github.mikoli.krolikcraft.dynmap.listeners;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.Claim;
import com.github.mikoli.krolikcraft.dynmap.MarkerApiManager;
import com.github.mikoli.krolikcraft.events.ClaimChangeEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClaimChangeListener implements Listener {

    private final PMEFactions plugin;

    public ClaimChangeListener(PMEFactions plugin) {
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
                markerApi.updateClaimStyle(markerApi.getClaimMarker(claim), markerApi.getFactionStyles(plugin.getFactionsManager().getFactionByUUID(claim.getOwner())).getFactionStyle(claim.getClaimType()));
                break;
            case REMOVE:
                markerApi.removeClaimStyle(claim);
                break;
        }
    }
}
