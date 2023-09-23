package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class Faction {

    private final Krolikcraft plugin;
    private String name;
    private Player leader;
    private final HashSet<Player> members = new HashSet<>();

    public Faction(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLeader(Player player) {
        leader = player;
    }

    public Player getLeader() {
        return leader;
    }

    public void addMember(Player player) {
        members.add(player);
    }

    public void removeMember(Player player) {
        members.remove(player);
    }

    public boolean isMember(Player player) {
        return members.contains(player);
    }

    public HashSet<Player> getMembers() {
        return members;
    }
}
