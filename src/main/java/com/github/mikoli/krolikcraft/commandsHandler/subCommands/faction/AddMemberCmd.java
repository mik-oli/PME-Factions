package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class AddMemberCmd extends SubCommand {

    private final PMEFactions plugin;

    public AddMemberCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getSyntax() {
        return "/factions add <player>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin add <faction> <player>";
    }

    @Override
    public int getArgsLength() {
        return 1;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.member.add";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = null;
        if (adminMode) faction = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        else faction = RequiredCmdArgs.getFactionByPlayer(plugin, Bukkit.getPlayer(commandSender.getName()));
        if (faction == null) return; //TODO return message

        UUID targetUUID = null;
        if (adminMode) targetUUID = RequiredCmdArgs.getTargetPlayer(adminMode, args[2]);
        else targetUUID = RequiredCmdArgs.getTargetPlayer(adminMode, args[1]);

        if (plugin.getFactionsManager().isPlayerInFaction(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-in-faction"));
            return;
        }

        faction.addMember(targetUUID);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("player-added"));
    }
}
