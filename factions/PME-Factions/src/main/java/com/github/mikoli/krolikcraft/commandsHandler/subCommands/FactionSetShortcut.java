package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class FactionSetShortcut extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.NAME);
        }
    };

    @Override
    public String getName() {
        return "setshortcut";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] setshortcut [<faction>] <shortcut>";
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
        return "pmefactions.faction.setshortcut";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("faction-set-shortcut");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {

        //TODO checking if name is correct length
        Faction faction = (Faction) args.get(0);
        String newShortcut = (String) args.get(1);

        for (Faction f : plugin.getFactionsHashMap().values()) {
            if (f.getShortcut().equals(newShortcut)){
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-exists"));
                return;
            }
        }
        faction.setShortcut(newShortcut);
    }
}
