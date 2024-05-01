package com.github.mikoli.krolikcraft.factionsLogic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class TeamsManager {

    private ArrayList<Team> teamsArray = new ArrayList<>();

    public TeamsManager() {
        this.setUpTeams();
    }

    private void setUpTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for (ChatColor color : ChatColor.values()) {
            Team team = null;
            if (scoreboard.getTeam(color.toString()) == null) {
                team = scoreboard.registerNewTeam(color.toString());
                team.setColor(color);
            } else team = scoreboard.getTeam(color.toString());
            teamsArray.add(team);
        }
    }

    public ArrayList<Team> getTeamsArray() {
        return teamsArray;
    }

    public Team getTeamByColor(ChatColor color) {
        for (Team team : teamsArray) if (team.getColor().equals(color)) return team;
        return null;
    }

    public void addPlayerToTeam(Player player, Team team) {
        if (!team.hasEntry(player.getName())) team.addEntry(player.getName());
    }

    public void removePlayerFromTeam(Player player, Team team) {
        if (team.hasEntry(player.getName())) team.removeEntry(player.getName());
    }
}
