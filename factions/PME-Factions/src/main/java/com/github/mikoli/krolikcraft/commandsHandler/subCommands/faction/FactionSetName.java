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

public class FactionSetName extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.REQUESTFACTION);
            add(RequiredCmdArgs.NAME);
        }
    };

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "setname";
    }

    @Override
    public String getSyntax() {
        return "/faction setname <name>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin setname <faction> <name>";
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
        return "pmefactions.faction.setname";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("faction-set-name");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

    //TODO checking if name is correct length
        Faction faction = (Faction) args.get(0);
        String newName = (String) args.get(1);

        for (Faction f : plugin.getFactionsHashMap().values()) {
            if (f.getName().equals(newName)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-exists"));
                return;
            }
        }
        faction.setName(newName);
    }
}
