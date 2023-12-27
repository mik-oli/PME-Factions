package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class WarDeclareCmd extends SubCommand {

    private final PMEFactions plugin;

    public WarDeclareCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

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
    public String getPermission() {
        return "pmefactions.faction.war";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction1 = null;
        if (adminMode) faction1 = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        else faction1 = RequiredCmdArgs.getFactionByPlayer(plugin, Bukkit.getPlayer(commandSender.getName()));
        if (faction1 == null) return; //TODO return message

        Faction faction2 = null;
        if (adminMode) faction2 = RequiredCmdArgs.getFactionByName(plugin, args[2]);
        else faction2 = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        if (faction2 == null) return; //TODO return message

        if (faction1.getEnemies().contains(faction2.getId()) && faction2.getEnemies().contains(faction1.getId())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-at-war"));
            return;
        }
        faction1.getEnemies().add(faction2.getId());
        faction2.getEnemies().add(faction1.getId());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("war-declared"));
    }
}
