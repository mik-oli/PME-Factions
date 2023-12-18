package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class FactionSetColor extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.REQUESTFACTION);
            add(RequiredCmdArgs.COLOR);
        }
    };

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "setcolor";
    }

    @Override
    public String getSyntax() {
        return "/faction setcolor <color>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin setcolor <faction> <color>";
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
        return "pmefactions.faction.setcolor";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("faction-set-color");
    }

    @Override
    public void perform(PMEFactions plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        ChatColor color = ChatColor.WHITE;
        try {
            color = ChatColor.valueOf((String) args.get(1));
        } catch (IllegalArgumentException ignore) {}
        Faction faction = (Faction) args.get(0);
        faction.setColor(color);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("color-changed"));
    }
}
