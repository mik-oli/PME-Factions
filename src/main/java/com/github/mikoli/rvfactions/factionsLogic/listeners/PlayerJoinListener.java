package com.github.mikoli.rvfactions.factionsLogic.listeners;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.TeamsManager;
import com.github.mikoli.rvfactions.factionsLogic.factions.FactionsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final RVFactions plugin;

    public PlayerJoinListener(RVFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        FactionsManager factionsManager = plugin.getFactionsManager();
        Player player = event.getPlayer();
        if (factionsManager.getPlayersFaction(player.getUniqueId()) == null) return;

        TeamsManager teamsManager = plugin.getTeamsManager();
        teamsManager.addPlayerToTeam(player.getUniqueId(), teamsManager.getTeam(factionsManager.getPlayersFaction(player.getUniqueId()).getName()));
    }
}
