package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionRemoveMember extends SubCommand {

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
        return "remove";
    }

    @Override
    public String getSyntax() {
        return "/faction remove <player>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin remove <faction> <player>";
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
        return "pmefactions.faction.member.remove";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("faction-remove-member");
    }

    @Override
    public void perform(PMEFactions plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {
        Faction faction = (Faction) args.get(0);
        UUID targetUUID = (UUID) args.get(1);
        if (FactionsUtils.getPlayersFaction(plugin, targetUUID) != faction) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-not-member"));
            return;
        }
        if (faction.getLeader().equals(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-kick-leader"));
            return;
        }
        if ((commandSender instanceof Player) && Bukkit.getPlayer(commandSender.getName()).getUniqueId() == targetUUID) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-kick-leader"));
            return;
        }

        faction.removeMember(targetUUID);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("player-removed"));
    }
}
