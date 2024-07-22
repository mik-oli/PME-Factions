package com.github.mikoli.krolikcraft.factionsLogic;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.factions.Faction;
import com.github.mikoli.krolikcraft.factionsLogic.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.UUID;

public class TeamsManager {

    private final PMEFactions plugin;
    private ArrayList<Team> teamsArray = new ArrayList<>();

    public TeamsManager(PMEFactions plugin) {
        this.plugin = plugin;
        this.setUpTeams();
    }

    private void setUpTeams() {
        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            Team team = null;
            if (scoreboard.getTeam(faction.getName()) == null) team = scoreboard.registerNewTeam(faction.getName());
            else team = scoreboard.getTeam(faction.getName());
            team.setPrefix(BukkitUtils.coloring("&e[&" + faction.getColor().getChar() + faction.getShortcut() + "&e]&r "));
            for (UUID uuid : faction.getMembers()) {
                addPlayerToTeam(uuid, team);
            }
            teamsArray.add(team);
        }
    }

    public ArrayList<Team> getTeamsArray() {
        return teamsArray;
    }

    public Team getTeam(String name) {
        for (Team team : teamsArray) if (team.getName().equals(name)) return team;
        return null;
    }

    public void addPlayerToTeam(UUID playerUUID, Team team) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
            if (!team.hasEntry(offlinePlayer.getName())) team.addEntry(offlinePlayer.getName());
        }
        else if (!team.hasEntry(player.getName())) team.addEntry(player.getName());
    }

    public void removePlayerFromTeam(UUID playerUUID, Team team) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
            if (team.hasEntry(offlinePlayer.getName())) team.removeEntry(offlinePlayer.getName());
        }
        else if (team.hasEntry(player.getName())) team.removeEntry(player.getName());
    }
}
