package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionInfo extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
        }
    };

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getSyntax() {
        return "/factions info <faction>";
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
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {
        Faction faction = (Faction) args.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&e===============&bFaction Details&e===============\n"));
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&eName: &a" + faction.getName() + "\n"));
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&eLeader: &a" + Bukkit.getOfflinePlayer(faction.getLeader()).getName() + "\n"));
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&eMembers: "));
        for (UUID playerUUID : faction.getMembers()) {
            String playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
            stringBuilder.append(Utils.coloring("&a" + playerName + "&e, "));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("\n");
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&e============================================="));

        commandSender.sendMessage(stringBuilder.toString());
    }
}
