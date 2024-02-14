package com.github.mikoli.krolikcraft.commands;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class CommandCompleter implements TabCompleter {

    private final PMEFactions plugin;

    public CommandCompleter(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) return null;
        boolean adminMode = command.getName().contains("-admin");
        if (args.length == 1) return getCommands(commandSender, adminMode);
        else if (args.length > 1) {
            ISubCommand subCommand = plugin.getCommandsManager().getSubCommand(args[0]);
            if (subCommand == null) return null;
            if (subCommand.getArgs().contains(CommandsArgs.NONE)) return null;

            Faction faction = null;
            if (subCommand.getArgs().contains(CommandsArgs.FACTION)) {
                if (adminMode) faction = plugin.getFactionsManager().getFactionFromName(args[1]);
                else faction = plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());
            }

            CommandsArgs arg = null;
            if (adminMode && (args.length - 1) <= subCommand.getArgsAdmin().size()) arg = subCommand.getArgsAdmin().get(args.length - 2);
            else if (!adminMode && (args.length - 1) <= subCommand.getArgs().size()) arg = subCommand.getArgs().get(args.length - 2);
            else return null;

            switch (arg) {
                case FACTION: return this.getFactions();
                case CLAIM_TYPE: return this.getClaimTypes();
                case COLOR: return this.getColors();
                case ONLINE_PLAYER: return this.getOnlinePlayers();
                case MEMBER_PLAYER: return this.getFactionMembers(faction);
                case OFFICER_PLAYER: return this.getFactionOfficers(faction);
                default: return null;
            }
        }
        return null;
    }

    private List<String> getCommands(CommandSender commandSender, boolean adminMode) {
        List<String> toReturn = new ArrayList<>();
        for (ISubCommand subCommand : plugin.getCommandsManager().getSubCommands()) {
            if (RankPermissions.hasPlayerPermission(plugin, Bukkit.getPlayer(commandSender.getName()), subCommand.requiredRank(), adminMode))
                toReturn.add(subCommand.getName());
        }
        return toReturn;
    }

    private List<String> getFactions() {
        List<String> toReturn = new ArrayList<>();
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            toReturn.add(faction.getName());
        }
        return toReturn;
    }

    private List<String> getClaimTypes() {
        List<String> toReturn = new ArrayList<>();
        Stream.of(ClaimType.values()).forEach(type -> toReturn.add(type.name()));
        return toReturn;
    }

    private List<String> getColors() {
        List<String> toReturn = new ArrayList<>();
        Stream.of(ChatColor.values()).forEach(color -> toReturn.add(color.name()));
        return toReturn;
    }

    private List<String> getOnlinePlayers() {
        List<String> toReturn = new ArrayList<>();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            toReturn.add(player.getName());
        }
        return toReturn;
    }

    private List<String> getFactionMembers(Faction faction) {
        if (faction == null) return null;
        List<String> toReturn = new ArrayList<>();
        for (UUID uuid : faction.getMembers()) {
            toReturn.add(Bukkit.getOfflinePlayer(uuid).getName());
        }
        return toReturn;
    }

    private List<String> getFactionOfficers(Faction faction) {
        if (faction == null) return null;
        List<String> toReturn = new ArrayList<>();
        for (UUID uuid : faction.getOfficers()) {
            toReturn.add(Bukkit.getOfflinePlayer(uuid).getName());
        }
        return toReturn;
    }
}
