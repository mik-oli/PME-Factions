package com.github.mikoli.ringsvalley.factionsLogic;

import com.github.mikoli.ringsvalley.RVFactions;
import com.github.mikoli.ringsvalley.factionsLogic.factions.Faction;
import com.github.mikoli.ringsvalley.factionsLogic.utils.BukkitUtils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.UUID;

public class TeamsManager {

    private final RVFactions plugin;
    private final ArrayList<Team> teamsArray = new ArrayList<>();

    public TeamsManager(RVFactions plugin) {
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
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        if (!team.hasPlayer(offlinePlayer)) team.addPlayer(offlinePlayer);
    }

    public void removePlayerFromTeam(UUID playerUUID, Team team) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
        if (team.hasPlayer(offlinePlayer)) team.removePlayer(offlinePlayer);
    }
}
