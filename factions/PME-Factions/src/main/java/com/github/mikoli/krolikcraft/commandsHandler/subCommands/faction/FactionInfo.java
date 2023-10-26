package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionInfo extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.TARGETFACTION);
        }
    };

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getSyntax() {
        return "/factions info <faction>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions info <faction>";
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
        return this.requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.info";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.NULL;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {
        Faction faction = (Faction) args.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&e========&bFaction Info&e========\n"));
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-name") + faction.getName() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-shortcut") + faction.getShortcut() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-core-loc") + faction.getCoreLocation().getX() + " " + faction.getCoreLocation().getY() + " " + faction.getCoreLocation().getZ() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-leader") + Bukkit.getOfflinePlayer(faction.getLeader()).getName() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-members"));
        for (UUID playerUUID : faction.getMembers()) {
            String playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
            stringBuilder.append(Utils.coloring("&a" + playerName + "&e, "));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append("\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-enemies"));
        for (UUID enemy : faction.getEnemies()) {
            stringBuilder.append(Utils.coloring("&a" + plugin.getFactionsHashMap().get(enemy).getName() + "&e, "));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 3);
        stringBuilder.append(Utils.pluginPrefix() + Utils.coloring("&e==============================="));

        commandSender.sendMessage(stringBuilder.toString());
    }
}
