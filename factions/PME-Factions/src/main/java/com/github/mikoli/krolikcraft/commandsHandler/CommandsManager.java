package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim.*;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction.*;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.other.FactionsList;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.other.GetClaimFlag;
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
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        boolean adminMode = command.getName().contains("-admin") || command.getName().equals("factions");
        if (args.length == 0) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-not-found"));
            return false;
        }

        SubCommand subCommand = null;
        for (SubCommand subCmd : subCommands) {
            if ((command.getName().equalsIgnoreCase(subCmd.getBaseCmd().name()) || (command.getName() + "-admin").equalsIgnoreCase(subCmd.getBaseCmd().name())) && subCmd.getName().equalsIgnoreCase(args[0])) subCommand = subCmd;
        }

        if (subCommand == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-not-found"));
            return false;
        }
        if (adminMode && args.length < subCommand.getArgsLength() + 2) {
            return this.returnSyntax(commandSender, "cmd-wrong-args", subCommand.getSyntax());
        } else if (args.length < subCommand.getArgsLength() + 1) {
            return this.returnSyntax(commandSender, "cmd-wrong-args", subCommand.getSyntax());
        }
        if (subCommand.playerOnly() && !(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-player-only"));
            return true;
        }
        if (subCommand.requiredPermission(plugin.getConfigUtils()) == CommandsPermissions.NULL && !commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
            return true;
        } else if (subCommand.requiredPermission(plugin.getConfigUtils()) != CommandsPermissions.NULL && !FactionsUtils.hasPlayerPermission(plugin, commandSender, subCommand.requiredPermission(plugin.getConfigUtils()), adminMode)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
            return true;
        }

        Faction requestFaction = null;
        Faction targetFaction = null;
        UUID targetPlayer = null;
        ClaimType claimType = null;
        String name = null;
        String shortcut = null;
        ChatColor color = null;
        UUID uuid = null;

        if (subCommand.requiredArguments().contains(RequiredCmdArgs.REQUESTFACTION)) {
            requestFaction = this.getRequestFaction(adminMode, commandSender, args);
            if (requestFaction == null) return this.returnSyntax(commandSender, "cmd-faction-not-found", subCommand.getSyntax());
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETFACTION)) {
            targetFaction = this.getTargetFaction(adminMode, args);
            if (targetFaction == null) return this.returnSyntax(commandSender, "cmd-faction-not-found", subCommand.getSyntax());
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
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.UUID)) {
            uuid = this.getUUID(args);
            if (uuid == null) return this.returnSyntax(commandSender, "cmd-wrong-args", subCommand.getSyntax());
        }

        List<Object> argsToPass = new ArrayList<>();
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.REQUESTFACTION)) argsToPass.add(requestFaction);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETFACTION)) argsToPass.add(targetFaction);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETPLAYER)) argsToPass.add(targetPlayer);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE)) argsToPass.add(claimType);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.UUID)) argsToPass.add(uuid);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.NAME)) argsToPass.add(name);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.SHORTCUT)) argsToPass.add(shortcut);
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.COLOR)) argsToPass.add(color);
        if (argsToPass.size() < subCommand.requiredArguments().size()) return this.returnSyntax(commandSender, "cmd-wrong-args", subCommand.getSyntax());

        subCommand.perform(plugin, commandSender, adminMode, argsToPass);
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
        subCommands.add(new FactionAddOfficer());
        subCommands.add(new FactionRemoveOfficer());
        subCommands.add(new CreateNeutralClaim());
    }

    private boolean returnSyntax(CommandSender commandSender, String error, String syntax) {
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation(error));
        commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&e" + syntax));
        return true;
    }

    private Faction getRequestFaction(boolean admin, CommandSender commandSender, String[] args) {
        Faction faction;
        if (admin) faction = FactionsUtils.getFactionFromName(plugin, args[1]);
        else faction = FactionsUtils.getPlayersFaction(plugin, Bukkit.getPlayer(commandSender.getName()).getUniqueId());

        return faction;
    }

    private Faction getTargetFaction(boolean admin, String[] args) {
        Faction faction;
        if (admin) {
            faction = FactionsUtils.getFactionFromName(plugin, args[2]);
        } else {
            faction = FactionsUtils.getFactionFromName(plugin, args[1]);
        }
        return faction;
    }

    private UUID getTargetPlayer(boolean admin, String[] args) {
        UUID uuid = null;
        OfflinePlayer offlinePlayer;

        if (admin) offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(args[2]));
        else offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

        if (offlinePlayer.hasPlayedBefore()) uuid = offlinePlayer.getUniqueId();
        return uuid;
    }

    private ClaimType getClaimType(boolean admin, String[] args) {
        ClaimType claimType;
        try {
            if (admin) claimType = ClaimType.valueOf(args[2]);
            else claimType = ClaimType.valueOf(args[1]);
        } catch (Exception exception) {
            claimType = ClaimType.CLAIM_5x5;
        }

        return claimType;
    }

    private ChatColor getFactionColor(boolean admin, String[] args) {
        ChatColor color = null;
        if (admin) {
            try {
                color = ChatColor.valueOf(args[2]);
            } catch (IllegalArgumentException ignored) {}
        } else {
            try {
                color = ChatColor.valueOf(args[1]);
            } catch (IllegalArgumentException ignored) {}
        }
        return color;
    }

    private UUID getUUID(String[] args) {
        UUID uuid = null;
        try {
            uuid = UUID.fromString(args[1]);
        } catch (IllegalArgumentException ignored) {}
        return uuid;
    }
}
