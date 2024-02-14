package com.github.mikoli.krolikcraft.factionsLogic.commands.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.factionsLogic.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factionsLogic.events.FactionChangeEvent;
import com.github.mikoli.krolikcraft.factionsLogic.events.FactionChangeType;
import com.github.mikoli.krolikcraft.factionsLogic.factions.Faction;
import com.github.mikoli.krolikcraft.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class SetColorCmd extends ISubCommand {

    private final PMEFactions plugin;

    public SetColorCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "setcolor";
    }

    @Override
    public String getSyntax() {
        return "/factions setcolor <color>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin setcolor <faction> <color>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.COLOR);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
            add(CommandsArgs.COLOR);
        }};
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.setcolor";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.LEADER;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        ChatColor color = null;
        if (adminMode) color = BukkitUtils.getColor(args[2]);
        else color = BukkitUtils.getColor(args[1]);

        Faction faction = null;
        if (adminMode) faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        else faction = plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        faction.setColor(color);
        BukkitUtils.callEvent(new FactionChangeEvent(faction, FactionChangeType.UPDATE_COLOR));
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("color-changed"));
    }
}
