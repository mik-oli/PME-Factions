package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandsManager implements CommandExecutor {

    private final PMEFactions plugin;
    private final ConfigUtils config;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandsManager(PMEFactions plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigUtils();
        this.loadSubCommands();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

//        boolean adminMode = command.getName().contains("-admin") || command.getName().equals("factions");
        boolean adminMode = command.getName().contains("-admin");
        if (args.length == 0) {
            commandSender.sendMessage(config.getLocalisation("cmd-not-found"));
            return false;
        }

        SubCommand subCommand = null;
        for (SubCommand subCmd : subCommands) {
            String cmd = command.getName().replace("-admin", "");
            if ((cmd.equalsIgnoreCase(subCmd.getBaseCmd().name())) && subCmd.getName().equalsIgnoreCase(args[0])) subCommand = subCmd;
        }
        if (subCommand == null) {
            commandSender.sendMessage(config.getLocalisation("cmd-not-found"));
            return false;
        }
        if (adminMode && args.length < subCommand.getArgsLength() + 2) {
            return this.returnSyntax(commandSender, config.getLocalisation("cmd-wrong-args"), subCommand.getAdminSyntax());
        } else if (args.length < subCommand.getArgsLength() + 1) {
            return this.returnSyntax(commandSender, config.getLocalisation("cmd-wrong-args"), subCommand.getSyntax());
        }
        if (subCommand.playerOnly() && !(commandSender instanceof Player)) {
            commandSender.sendMessage(config.getLocalisation("cmd-player-only"));
            return true;
        }
        if (subCommand.requiredPermission() == CommandsPermissions.NULL && !commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(config.getLocalisation("cmd-no-permission"));
            return true;
        } else if (subCommand.requiredPermission() != CommandsPermissions.NULL && !CommandsPermissions.hasPlayerPermission(plugin, commandSender, subCommand.requiredPermission(), adminMode)) {
            commandSender.sendMessage(config.getLocalisation("cmd-no-permission"));
            return true;
        }


        subCommand.perform(commandSender, adminMode, args);
        return true;
    }

    private void loadSubCommands() {
//        subCommands.add(new Claim());
//        subCommands.add(new ClaimChangeOwner());
//        subCommands.add(new ClaimInfo());
//        subCommands.add(new FactionAddMember());
//        subCommands.add(new FactionInfo());
//        subCommands.add(new FactionRemoveMember());
//        subCommands.add(new FactionsList());
//        subCommands.add(new GetClaimFlag());
//        subCommands.add(new Unclaim());
//        subCommands.add(new FactionCreate());
//        subCommands.add(new FactionSetName());
//        subCommands.add(new FactionSetShortcut());
//        subCommands.add(new FactionSetColor());
//        subCommands.add(new ClaimGetId());
//        subCommands.add(new AddChunkToClaim());
//        subCommands.add(new WarDeclare());
//        subCommands.add(new WarPeace());
//        subCommands.add(new FactionDelete());
//        subCommands.add(new FactionAddOfficer());
//        subCommands.add(new FactionRemoveOfficer());
//        subCommands.add(new CreateNeutralClaim());
    }

    private boolean returnSyntax(CommandSender commandSender, String error, String syntax) {
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation(error));
        commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&e" + syntax));
        return true;
    }
}
