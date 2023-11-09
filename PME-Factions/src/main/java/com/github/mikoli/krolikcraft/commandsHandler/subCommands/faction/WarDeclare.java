package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class WarDeclare extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.REQUESTFACTION);
            add(RequiredCmdArgs.TARGETFACTION);
        }
    };

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "war";
    }

    @Override
    public String getSyntax() {
        return "/faction war <faction>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin war <faction 1> <faction 2>";
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
        return requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.war";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.LEADER;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Faction faction1 = (Faction) args.get(0);
        Faction faction2 = (Faction) args.get(1);
        if (faction1.getEnemies().contains(faction2.getId()) && faction2.getEnemies().contains(faction1.getId())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-at-war"));
            return;
        }
        faction1.getEnemies().add(faction2.getId());
        faction2.getEnemies().add(faction1.getId());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("war-declared"));
    }
}
