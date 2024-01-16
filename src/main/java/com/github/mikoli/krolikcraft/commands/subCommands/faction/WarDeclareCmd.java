package com.github.mikoli.krolikcraft.commands.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class WarDeclareCmd extends ISubCommand {

    private final PMEFactions plugin;

    public WarDeclareCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "war";
    }

    @Override
    public String getSyntax() {
        return "/factions war <faction>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin war <faction_1> <faction_2>";
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
            add(CommandsArgs.FACTION);
        }};
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
        return RankPermissions.LEADER;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction1 = null;
        if (adminMode) faction1 = plugin.getFactionsManager().getFactionFromName(args[1]);
        else faction1 = plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());
        if (faction1 == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        Faction faction2 = null;
        if (adminMode) faction2 = plugin.getFactionsManager().getFactionFromName(args[2]);
        else faction2 = plugin.getFactionsManager().getFactionFromName(args[1]);
        if (faction2 == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        if (faction1.isAtWarWith(faction2) && faction2.isAtWarWith(faction1)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-at-war"));
            return;
        }
        faction1.addEnemy(faction2.getId());
        faction2.addEnemy(faction1.getId());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("war-declared"));
    }
}
