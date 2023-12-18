package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.PMEFactions;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.UUID;

public class Faction {

    private final PMEFactions plugin;
    private UUID id;
    private String name;
    private String shortcut;
    private ChatColor color;
    private UUID leader;
    private Location coreLocation;
    private final HashSet<UUID> officers = new HashSet<>();
    private final HashSet<UUID> members = new HashSet<>();
    private final HashSet<UUID> enemies = new HashSet<>();

    public Faction(PMEFactions plugin) {
        this.plugin = plugin;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setLeader(UUID player) {
        leader = player;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setCoreLocation(Location location) {
        this.coreLocation = location;
    }

    public Location getCoreLocation() {
         return this.coreLocation;
    }

    public void addMember(UUID player) {
        members.add(player);
    }

    public void removeMember(UUID player) {
        members.remove(player);
    }

    public boolean isOfficer(UUID player) {
        return officers.contains(player);
    }

    public HashSet<UUID> getOfficers() {
        return officers;
    }

    public boolean isMember(UUID player) {
        return members.contains(player);
    }

    public HashSet<UUID> getMembers() {
        return members;
    }

    public HashSet<UUID> getEnemies() {
        return enemies;
    }

    public boolean isAtWarWith(Faction faction) {
        return enemies.contains(faction.id);

    }
}
