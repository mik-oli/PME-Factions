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

public class FactionSetShortcut extends SubCommand {

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
        return "setshortcut";
    }

    @Override
    public String getSyntax() {
        return "/faction setshortcut <shortcut>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin setshortcut <faction> <shortcut>";
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
        return "pmefactions.faction.setshortcut";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("faction-set-shortcut");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Faction faction = (Faction) args.get(0);
        String newShortcut = (String) args.get(1);

        if (newShortcut.length() > plugin.getConfigUtils().getMaxLength("max-shortcut-length")) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-too-long").replace("{0}", String.valueOf(plugin.getConfigUtils().getMaxLength("max-shortcut-length"))));
            return;
        }
        for (Faction f : plugin.getFactionsHashMap().values()) {
            if (f.getShortcut().equalsIgnoreCase(newShortcut)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-already-exists"));
                return;
            }
        }

        for (Faction f : plugin.getFactionsHashMap().values()) {
            if (f.getShortcut().equals(newShortcut)){
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-exists"));
                return;
            }
        }
        faction.setShortcut(newShortcut);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-changed"));
    }
}
