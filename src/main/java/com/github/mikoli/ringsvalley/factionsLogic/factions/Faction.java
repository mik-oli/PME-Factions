package com.github.mikoli.ringsvalley.factionsLogic.factions;

import com.github.mikoli.ringsvalley.factionsLogic.utils.FilesUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.UUID;

public class Faction {

    private final UUID id;
    private FilesUtils dataFile;
    private String name;
    private String shortcut;
    private ChatColor color;
    private Location coreLocation;
    private UUID leader;
    private final HashSet<UUID> officers = new HashSet<>();
    private final HashSet<UUID> members = new HashSet<>();
    private final HashSet<UUID> enemies = new HashSet<>();

    public Faction(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setDataFile(FilesUtils dataFile) {
        this.dataFile = dataFile;
    }

    public FilesUtils getDataFile() {
        return this.dataFile;
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

    public HashSet<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID player) {
        members.add(player);
    }

    public void removeMember(UUID player) {
        members.remove(player);
    }

    public boolean isMember(UUID player) {
        return members.contains(player);
    }

    public HashSet<UUID> getOfficers() {
        return officers;
    }

    public boolean isOfficer(UUID player) {
        return officers.contains(player);
    }

    public void addOfficer(UUID player) {
        officers.add(player);
    }

    public void removeOfficer(UUID player) {
        officers.remove(player);
    }

    public HashSet<UUID> getEnemies() {
        return enemies;
    }

    public void addEnemy(UUID faction) {
        enemies.add(faction);
    }

    public void removeEnemy(UUID faction) {
        enemies.remove(faction);
    }

    public boolean isAtWarWith(Faction faction) {
        return enemies.contains(faction.getId());
    }
}
