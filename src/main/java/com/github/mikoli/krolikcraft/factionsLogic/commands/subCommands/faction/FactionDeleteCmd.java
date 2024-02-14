package com.github.mikoli.krolikcraft.factionsLogic.commands.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.factionsLogic.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factionsLogic.factions.Faction;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class FactionDeleteCmd extends ISubCommand {

    private final PMEFactions plugin;

    public FactionDeleteCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "destroy";
    }

    @Override
    public String getSyntax() {
        return "/factions destroy";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin destroy <faction>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.NONE);
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
        return "pmefactions.faction.delete";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.LEADER;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = null;
        if (adminMode) faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        else faction = plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        if (adminMode || faction.getLeader().equals(Bukkit.getPlayer(commandSender.getName()).getUniqueId())) {
            plugin.getFactionsManager().removeFaction(faction);
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-deleted"));
        }
    }
}
