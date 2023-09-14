package com.github.mikoli.krolikcraft.staffWL;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffWL implements Listener {

    private final Krolikcraft plugin;
    private boolean whiteListStatus;
    private final List<UUID> whitelistedPlayers = new ArrayList<>();

    public StaffWL(Krolikcraft plugin) {
        this.plugin = plugin;
        this.loadData();
    }

    public boolean getWhiteListStatus() {
        return this.whiteListStatus;
    }

    public void setWhiteListStatus(boolean status) {
        this.whiteListStatus = status;
    }

    public List<UUID> getWhitelistedPlayers() {
        return this.whitelistedPlayers;
    }

    public boolean isPlayerWhitelisted(Player player) {
        return this.whitelistedPlayers.contains(player.getUniqueId());
    }

    public void addUserToList(Player player) {
        this.whitelistedPlayers.add(player.getUniqueId());
    }

    public void removeUserFromList(Player player) {
        this.whitelistedPlayers.remove(player.getUniqueId());
    }

    public void loadData() {
        this.loadWLStatus();
        this.loadWhitelistedPlayers();
    }

    public void savaData() {
        this.savaWLStatus();
        this.saveWhitelistedPlayers();
    }

    private void loadWLStatus() {
        whiteListStatus = this.plugin.getConfig().getBoolean("status");
    }

    private void loadWhitelistedPlayers() {
        for (String s : this.plugin.getConfig().getStringList("whitelisted-users")) {
            this.whitelistedPlayers.add(UUID.fromString(s));
        }
    }

    private void savaWLStatus() {
        this.plugin.getConfig().set("status", this.whiteListStatus);
    }

    private void saveWhitelistedPlayers() {
        this.plugin.getConfig().set("whitelisted-users", this.whitelistedPlayers);
    }
}
