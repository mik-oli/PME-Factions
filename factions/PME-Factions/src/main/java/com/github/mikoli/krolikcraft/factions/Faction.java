package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;

import java.util.HashSet;
import java.util.UUID;

public class Faction {

    private final Krolikcraft plugin;
    private UUID id;
    private String name;
    private UUID leader;
    private final HashSet<UUID> members = new HashSet<>();

    public Faction(Krolikcraft plugin) {
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

    public void setLeader(UUID player) {
        leader = player;
    }

    public UUID getLeader() {
        return leader;
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

    public HashSet<UUID> getMembers() {
        return members;
    }
}
