package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class FactionsList extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getSyntax() {
        return "/factions list";
    }

    @Override
    public int getArgsLength() {
        return 0;
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
        return "pmefactions.faction.list";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.NULL;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("factions-list"));
        for (Faction faction : plugin.getFactionsHashMap().values()) {
            stringBuilder.append(faction.getName());
            stringBuilder.append(Utils.coloring("&e, &b"));
        }
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "&e.");
        commandSender.sendMessage(stringBuilder.toString());
    }
}
