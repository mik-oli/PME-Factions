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

public class WarPeace extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.FACTION);
        }
    };

    @Override
    public String getName() {
        return "peace";
    }

    @Override
    public String getSyntax() {
        return "/factions [<admin>] peace <faction> [<faction>]";
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
        return "pmefactions.faction.peace";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("war.peace");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {

        Faction faction1 = (Faction) args.get(0);
        Faction faction2 = (Faction) args.get(1);
        if (!faction1.getEnemies().contains(faction2.getId()) && !faction2.getEnemies().contains(faction1.getId())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-at-peace"));
            return;
        }

        faction1.getEnemies().remove(faction2.getId());
        faction2.getEnemies().remove(faction1.getId());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("signed-peace"));
    }
}
