package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

public class FactionAddMember extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.TARGETPLAYER);
            add(RequiredCmdArgs.ADMINMODE);
        }
    };

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] [<faction>] add <player>";
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
    public void perform(Krolikcraft plugin, CommandSender commandSender, String[] args) {

        Faction faction = plugin.getFactionsHashMap().get(args[0]);
        UUID targetUUID = UUID.fromString(args[1]);
        if (FactionsUtils.isPlayerInFaction(plugin, targetUUID)) return; //TODO player is faction member

        faction.addMember(targetUUID);
        commandSender.sendMessage(Utils.coloring(Utils.pluginPrefix() + "&aPlayer added to faction."));
    }
}
