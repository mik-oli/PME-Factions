package com.github.mikoli.krolikcraft.commands.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.BukkitUtils;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

public class FactionInfoCmd extends ISubCommand {

    private final PMEFactions plugin;

    public FactionInfoCmd(PMEFactions plugin) {
        this.plugin = plugin;
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
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
        }};
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.info";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.NULL;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BukkitUtils.pluginPrefix() + BukkitUtils.coloring("&e========&bFaction Info&e========\n"));
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-name") + faction.getName() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-shortcut") + faction.getShortcut() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-core-loc")
                + (int)faction.getCoreLocation().getX() + " "
                + (int)faction.getCoreLocation().getY() + " "
                + (int)faction.getCoreLocation().getZ() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-leader") + Bukkit.getOfflinePlayer(faction.getLeader()).getName() + "\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-members"));
        for (UUID playerUUID : faction.getMembers()) {
            String playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
            stringBuilder.append(BukkitUtils.coloring("&a" + playerName + "&e, "));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append("\n");
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("faction-enemies"));
        for (UUID enemy : faction.getEnemies()) {
            stringBuilder.append(BukkitUtils.coloring("&a" + plugin.getFactionsManager().getFactionsList().get(enemy).getName() + "&e, "));
        }
        stringBuilder.append("\n");
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append(BukkitUtils.pluginPrefix() + BukkitUtils.coloring("&e=========================="));

        commandSender.sendMessage(stringBuilder.toString());
    }
}
