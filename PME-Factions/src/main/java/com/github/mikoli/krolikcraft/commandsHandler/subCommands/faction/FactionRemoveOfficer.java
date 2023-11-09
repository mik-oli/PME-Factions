package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionRemoveOfficer extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.REQUESTFACTION);
            add(RequiredCmdArgs.TARGETPLAYER);
        }
    };

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "demote";
    }

    @Override
    public String getSyntax() {
        return "/faction demote <player>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin demote <faction> <player>";
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
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return this.requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.member.demote";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.LEADER;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {
        Faction faction = (Faction) args.get(0);
        UUID targetUUID = (UUID) args.get(1);
        if (FactionsUtils.getPlayersFaction(plugin, targetUUID) != faction) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-not-member"));
            return;
        }
        if (faction.getLeader().equals(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-promote-self"));
            return;
        }
        if (!faction.isOfficer(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-not-officer"));
            return;
        }

        faction.getOfficers().remove(targetUUID);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-demoted"));
    }
}
