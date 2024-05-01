package com.github.mikoli.krolikcraft.factionsLogic.listeners;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.TeamsManager;
import com.github.mikoli.krolikcraft.factionsLogic.factions.FactionsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PMEFactions plugin;

    public PlayerJoinListener(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        FactionsManager factionsManager = plugin.getFactionsManager();
        Player player = event.getPlayer();
        if (factionsManager.getPlayersFaction(player.getUniqueId()) == null) return;

        TeamsManager teamsManager = plugin.getTeamsManager();
        teamsManager.addPlayerToTeam(player, teamsManager.getTeamByColor(factionsManager.getPlayersFaction(player.getUniqueId()).getColor()));
    }
}
