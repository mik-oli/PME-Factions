package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.*;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.Utils;

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
        if (subCommand == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-not-found"));
            return false;
        }
        if (subCommand.playerOnly() && !(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-player-only"));
            return true;
        }
        if (commandSender instanceof Player && !FactionsUtils.hasPlayerPermission(plugin, Bukkit.getPlayer(commandSender.getName()), subCommand.requiredPermission(plugin.getConfigUtils()), adminMode)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
            return true;
        }
        if (subCommand.requiredPermission(plugin.getConfigUtils()) == CommandsPermissions.NULL && !commandSender.hasPermission(subCommand.getPermission())) return true;

        Faction faction1 = null;
        Faction faction2 = null;
        UUID targetPlayer = null;
        ClaimType claimType = null;
        String name = null;
        String shortcut = null;
        ChatColor color = null;

        if (subCommand.requiredArguments().contains(RequiredCmdArgs.FACTION)) {
            faction1 = this.getFaction(adminMode, commandSender, args);
            if (faction1 == null) return this.returnSyntax(commandSender, "cmd-faction-not-found", subCommand.getSyntax());

            int i = 0;
            for (RequiredCmdArgs requiredArgs : subCommand.requiredArguments()) {
                if (requiredArgs == RequiredCmdArgs.FACTION) i++;
            }
            if (i == 2) faction2 = this.getSecondFaction(adminMode, args);
            if (faction2 == null) return this.returnSyntax(commandSender, "cmd-faction-not-found", subCommand.getSyntax());
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETPLAYER)) {
            targetPlayer = this.getTargetPlayer(adminMode, args);
            if (targetPlayer == null) return this.returnSyntax(commandSender, "cmd-player-not-found", subCommand.getSyntax());
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE)) {
            claimType = this.getClaimType(adminMode, args);
            if (claimType == null) return this.returnSyntax(commandSender, "cmd-claim-type-not-found", subCommand.getSyntax());
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.COLOR)) {
            color = this.getFactionColor(adminMode, args);
            if (color == null) return this.returnSyntax(commandSender, "cmd-color-not-found", subCommand.getSyntax());
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.NAME) && subCommand.requiredArguments().contains(RequiredCmdArgs.SHORTCUT)) {
            name = args[args.length - 2];
            shortcut = args[args.length - 1];
        }
        else if (subCommand.requiredArguments().contains(RequiredCmdArgs.NAME)) {
            name = args[args.length - 1];
        }
        else if (subCommand.requiredArguments().contains(RequiredCmdArgs.SHORTCUT)) {
            shortcut = args[args.length - 1];
        }

        List<Object> argsToPass = new ArrayList<>();
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.FACTION)) argsToPass.add(faction1.getId());
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETPLAYER)) argsToPass.add(targetPlayer);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE)) argsToPass.add(claimType);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.ADMINMODE)) argsToPass.add(adminMode);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.NAME)) argsToPass.add(name);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.SHORTCUT)) argsToPass.add(shortcut);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.COLOR)) argsToPass.add(color);
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
        subCommands.add(new FactionDelete());
    }

    private boolean returnSyntax(CommandSender commandSender, String error, String syntax) {
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation(error));
        commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&e" + syntax));
        return true;
    }

    private Faction getFaction(boolean admin, CommandSender commandSender, String[] args) {
        Faction faction;
        if (admin) {
            faction = FactionsUtils.getFactionFromName(plugin, args[2]);
        } else {
            faction = FactionsUtils.getPlayersFaction(plugin, Bukkit.getPlayer(commandSender.getName()).getUniqueId());
        }
        return faction;
    }

    private Faction getSecondFaction(boolean admin, String[] args) {
        Faction faction;
        if (admin) {
            faction = FactionsUtils.getFactionFromName(plugin, args[3]);
        } else {
            faction = FactionsUtils.getFactionFromName(plugin, args[1]);
        }
        return faction;
    }

    private UUID getTargetPlayer(boolean admin, String[] args) {
        UUID uuid = null;
        if (admin) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(args[3]));
            if (offlinePlayer.hasPlayedBefore()) uuid = offlinePlayer.getUniqueId();
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(args[1]));
            if (offlinePlayer.hasPlayedBefore()) uuid = offlinePlayer.getUniqueId();
        }
        return uuid;
    }

    private ClaimType getClaimType(boolean admin, String[] args) {
        ClaimType claimType = null;
        if (admin) {
            try {
                claimType = ClaimType.valueOf(args[3]);
            } catch (IllegalArgumentException ignored) {}
        } else {
            try {
                claimType = ClaimType.valueOf(args[1]);
            } catch (IllegalArgumentException ignored) {}
        }
        return claimType;
    }

    private ChatColor getFactionColor(boolean admin, String[] args) {
        ChatColor color = null;
        if (admin) {
            try {
                color = ChatColor.valueOf(args[3]);
            } catch (IllegalArgumentException ignored) {}
        } else {
            try {
                color = ChatColor.valueOf(args[1]);
            } catch (IllegalArgumentException ignored) {}
        }
        return color;
    }
}
