package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim.*;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction.*;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.other.FactionsListCmd;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.other.GetClaimFlagCmd;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
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
        if (subCommand.requiredRank() == RankPermissions.NULL && !commandSender.hasPermission(subCommand.getPermission())) {
            commandSender.sendMessage(config.getLocalisation("cmd-no-permission"));
            return true;
        } else if (subCommand.requiredRank() != RankPermissions.NULL && !RankPermissions.hasPlayerPermission(plugin, commandSender, subCommand.requiredRank(), adminMode)) {
            commandSender.sendMessage(config.getLocalisation("cmd-no-permission"));
            return true;
        }


        subCommand.perform(commandSender, adminMode, args);
        return true;
    }

    private void loadSubCommands() {
        subCommands.add(new ClaimAddChunkCmd(plugin));
        subCommands.add(new ClaimChangeOwnerCmd(plugin));
        subCommands.add(new ClaimCmd(plugin));
        subCommands.add(new ClaimInfoCmd(plugin));
        subCommands.add(new ClaimNeutralCmd(plugin));
        subCommands.add(new GetClaimIdCmd(plugin));
        subCommands.add(new UnclaimCmd(plugin));
        subCommands.add(new AddMemberCmd(plugin));
        subCommands.add(new AddOfficerCmd(plugin));
        subCommands.add(new FactionCreateCmd(plugin));
        subCommands.add(new FactionDeleteCmd(plugin));
        subCommands.add(new FactionInfoCmd(plugin));
        subCommands.add(new RemoveMemberCmd(plugin));
        subCommands.add(new RemoveOfficerCmd(plugin));
        subCommands.add(new SetColorCmd(plugin));
        subCommands.add(new SetNameCmd(plugin));
        subCommands.add(new SetShortcutCmd(plugin));
        subCommands.add(new WarDeclareCmd(plugin));
        subCommands.add(new WarPeaceCmd(plugin));
        subCommands.add(new FactionsListCmd(plugin));
        subCommands.add(new GetClaimFlagCmd(plugin));
    }

    private boolean returnSyntax(CommandSender commandSender, String error, String syntax) {
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation(error));
        commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&e" + syntax));
        return true;
    }
}
