package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
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
        return "/factions [admin] add [<faction>] <player>";
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
        return "pmefactions.faction.member.add";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("faction-add-member");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {

        Faction faction = (Faction) args.get(0);
        UUID targetUUID = (UUID) args.get(1);
        if (FactionsUtils.isPlayerInFaction(plugin, targetUUID)) return; //TODO target is faction member message

        faction.addMember(targetUUID);
        commandSender.sendMessage(Utils.coloring(Utils.pluginPrefix() + "&aPlayer added to faction."));
    }
}
