package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionDelete extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.TARGETPLAYER);
            add(RequiredCmdArgs.ADMINMODE);
        }
    };

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] delete [<faction>]";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.delete";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.LEADER;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {

        Faction faction = (Faction) args.get(0);
        if ((boolean) args.get(2) || FactionsUtils.getPlayersFaction(plugin, (UUID) args.get(1)).equals(faction.getLeader())) {
            FactionsUtils.removeFaction(plugin, faction);
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-deleted"));
        }
    }
}
