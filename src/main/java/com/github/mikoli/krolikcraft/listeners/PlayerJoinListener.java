package com.github.mikoli.krolikcraft.listeners;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final Krolikcraft plugin;

    public PlayerJoinListener(Krolikcraft plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerPreJoinEvent(AsyncPlayerPreLoginEvent event) {
        if (!plugin.getStaffWL().getWhiteListStatus()) return;

        UUID uuid = event.getUniqueId();
        if (!plugin.getStaffWL().getWhitelistedPlayers().contains(uuid)) {
            //TODO message from config
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "Staff-only mode is currently enabled.");
        }
    }
}
