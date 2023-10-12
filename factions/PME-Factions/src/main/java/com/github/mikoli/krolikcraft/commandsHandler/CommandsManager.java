package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.*;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandsManager implements CommandExecutor {

    private final Krolikcraft plugin;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandsManager(Krolikcraft plugin) {
        this.plugin = plugin;
        this.loadSubCommands();
        plugin.getCommand("factions").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        String cmd;
        boolean adminMode = false;
        if (args[0].equals("admin")) {
            cmd = args[2];
            adminMode = true;
        }
        else cmd = args[0];

        SubCommand subCommand = null;
        for (SubCommand subCmd : subCommands) {
            if (subCmd.getName().equalsIgnoreCase(cmd)) subCommand = subCmd;
        }
        if (subCommand == null) return false; //TODO command not found
        if (subCommand.playerOnly() && !(commandSender instanceof Player)) return true; //todo player only command message
        if (commandSender instanceof Player && !FactionsUtils.hasPlayerPermission(plugin, Bukkit.getPlayer(commandSender.getName()), subCommand.requiredPermission(plugin.getConfigUtils()), adminMode)) return true;
        if (subCommand.requiredPermission(plugin.getConfigUtils()) == CommandsPermissions.NULL && !commandSender.hasPermission(subCommand.getPermission())) return true;

        Faction faction = null;
        UUID targetPlayer = null;
        ClaimType claimType = null;
        String name = null;
        String shortcut = null;
        ChatColor color = null;

        for (String arg : args) {
            if (faction == null) {
                faction = FactionsUtils.getFactionFromName(plugin, arg);
                continue;
            }
            try {
                if (claimType == null) {
                    claimType = ClaimType.valueOf(arg);
                    continue;
                }
                if (targetPlayer == null) {
                    targetPlayer = UUID.fromString(arg);
                    continue;
                }
                if (color == null) {
                    color = ChatColor.valueOf(arg);
                    continue;
                }
            } catch (IllegalArgumentException ignored) {}
            if (arg.length() < 4) {
                shortcut = arg;
                continue;
            }
            name = arg;
        }

        //TODO return error and syntax
        List<Object> argsToPass = new ArrayList<>();
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.FACTION) && faction != null) argsToPass.add(faction.getId());
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETPLAYER) && targetPlayer != null) argsToPass.add(targetPlayer);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE) && claimType != null) argsToPass.add(claimType);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.ADMINMODE)) argsToPass.add(adminMode);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.NAME) && name != null) argsToPass.add(name);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.SHORTCUT) && shortcut != null) argsToPass.add(shortcut);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.COLOR) && color != null) argsToPass.add(color);
        if (argsToPass.size() < subCommand.requiredArguments().size()) return false;

        subCommand.perform(plugin, commandSender, argsToPass);
        return true;
    }

    private void loadSubCommands() {
        subCommands.add(new Claim());
        subCommands.add(new ClaimChangeOwner());
        subCommands.add(new ClaimInfo());
        subCommands.add(new FactionAddMember());
        subCommands.add(new FactionInfo());
        subCommands.add(new FactionRemoveMember());
        subCommands.add(new FactionsList());
        subCommands.add(new GetClaimFlag());
        subCommands.add(new Unclaim());
        subCommands.add(new FactionCreate());
        subCommands.add(new FactionSetName());
        subCommands.add(new FactionSetShortcut());
    }
}
