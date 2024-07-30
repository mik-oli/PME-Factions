package com.github.mikoli.rvfactions.factionsLogic.commands;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.ConfigUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.RankPermissions;
import com.github.mikoli.rvfactions.factionsLogic.commands.subCommands.faction.*;
import com.github.mikoli.rvfactions.factionsLogic.commands.subCommands.claim.*;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandsManager implements CommandExecutor {

    private final RVFactions plugin;
    private final ConfigUtils config;
    private final ArrayList<ISubCommand> subCommands = new ArrayList<>();

    public CommandsManager(RVFactions plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigUtils();
        this.loadSubCommands();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        boolean adminMode = command.getName().contains("-admin");
        if (args.length == 0) {
            commandSender.sendMessage(config.getLocalisation("cmd-not-found"));
            return false;
        }
        ISubCommand subCommand = this.getSubCommand(args[0]);
        if (subCommand == null) {
            commandSender.sendMessage(config.getLocalisation("cmd-not-found"));
            return false;
        }
        if (args.length < subCommand.getLength(adminMode)) {
            if (adminMode) return this.returnSyntax(commandSender, "cmd-wrong-args", subCommand.getAdminSyntax());
            else return this.returnSyntax(commandSender, "cmd-wrong-args", subCommand.getSyntax());
        }
        if (subCommand.playerOnly() && !(commandSender instanceof Player)) {
            commandSender.sendMessage(config.getLocalisation("cmd-player-only"));
            return true;
        }
        if (commandSender instanceof Player) {
            if (!RankPermissions.hasPlayerPermission(plugin, Bukkit.getPlayer(commandSender.getName()), subCommand.requiredRank(), adminMode) && !commandSender.hasPermission(subCommand.getPermission())) {
                commandSender.sendMessage(config.getLocalisation("cmd-no-permission"));
                return true;
            }
        }

        subCommand.perform(commandSender, adminMode, args);
        return true;
    }

    protected ArrayList<ISubCommand> getSubCommands() {
        return this.subCommands;
    }

    protected ISubCommand getSubCommand(String str) {
        for (ISubCommand subCmd : subCommands)
            if (subCmd.getName().equalsIgnoreCase(str)) return subCmd;
        return null;
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
        commandSender.sendMessage(config.getLocalisation(error));
        commandSender.sendMessage(BukkitUtils.pluginPrefix() + BukkitUtils.coloring("&e" + syntax.replace("_", " ")));
        return true;
    }
}
