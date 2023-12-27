package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class FactionDeleteCmd extends SubCommand {

    private final PMEFactions plugin;

    public FactionDeleteCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getSyntax() {
        return "/factions delete";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin delete <faction>";
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
    public String getPermission() {
        return "pmefactions.faction.delete";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = null;
        if (adminMode) faction = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        else faction = plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());

        if (faction == null) return; //TODO return message

        if (adminMode || faction.getLeader().equals(Bukkit.getPlayer(commandSender.getName()).getUniqueId())) {
            plugin.getFactionsManager().removeFaction(faction);
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-deleted"));
        }
    }
}
