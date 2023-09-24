package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Faction {

    private final Krolikcraft plugin;
    private String name;
    private UUID leader;
    private final HashSet<UUID> members = new HashSet<>();

    public Faction(Krolikcraft plugin) {
        this.plugin = plugin;
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
