package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.*;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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

        Faction faction1 = null;
        UUID targetPlayer = null;
        ClaimType claimType = null;
        String name = null;
        String shortcut = null;
        ChatColor color = null;
        Faction faction2 = null;

        //TODO return error and syntax
        for (String arg : args) {
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.FACTION)) {
                if (faction1 == null) {
                    if (adminMode) faction1 = FactionsUtils.getFactionFromName(plugin, arg);
                    else faction1 = FactionsUtils.getPlayersFaction(plugin, Bukkit.getPlayer(commandSender.getName()).getUniqueId());

                    if (faction1 == null) return false; //TODO faction not found
                    continue;
                }
            }
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETPLAYER)) {
                if (targetPlayer == null) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(arg));
                    if (!offlinePlayer.hasPlayedBefore()) return false; //TODO player not found
                    targetPlayer = offlinePlayer.getUniqueId();
                    continue;
                }
            }
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE)) {
                if (claimType == null) {
                    try {
                        claimType = ClaimType.valueOf(arg);
                    } catch (IllegalArgumentException ignored) {}

                    if (claimType == null) return false; //TODO claim not found
                    continue;
                }
            }
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE)) {
                if (claimType == null) {
                    try {
                        claimType = ClaimType.valueOf(arg);
                    } catch (IllegalArgumentException ignored) {}

                    if (claimType == null) return false; //TODO claim not found
                    continue;
                }
            }
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.NAME)) {
                if (name == null) name = args[args.length - 2];
                continue;
            }
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.SHORTCUT)) {
                if (shortcut == null) shortcut = args[args.length - 1];
                continue;
            }
            if (subCommand.requiredArguments().contains(RequiredCmdArgs.COLOR)) {
                if (color == null) {
                    try {
                        color = ChatColor.valueOf(arg);
                    } catch (IllegalArgumentException ignored) {}
                    if (color == null) return false; //TODO color not found
                }
            }
        }

        List<Object> argsToPass = new ArrayList<>();
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.FACTION) && faction1 != null) argsToPass.add(faction1.getId());
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
        subCommands.add(new FactionSetColor());
        subCommands.add(new ClaimGetId());
        subCommands.add(new AddChunkToClaim());
        subCommands.add(new WarDeclare());
        subCommands.add(new WarPeace());
    }
}
