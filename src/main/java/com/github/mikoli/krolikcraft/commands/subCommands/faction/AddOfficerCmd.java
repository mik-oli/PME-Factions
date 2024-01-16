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

public class AddOfficerCmd extends ISubCommand {

   private final PMEFactions plugin;

   public AddOfficerCmd(PMEFactions plugin) {
       this.plugin = plugin;
   }

    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public String getSyntax() {
        return "/factions promote <player>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin promote <faction> <player>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.OFFICER_PLAYER);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
            add(CommandsArgs.OFFICER_PLAYER);
        }};
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.member.promote";
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

        UUID targetUUID = null;
        if (adminMode) targetUUID = BukkitUtils.getPlayerUUID(args[2]);
        else targetUUID = BukkitUtils.getPlayerUUID(args[1]);
        if (targetUUID == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-player-not-found"));
            return;
        }

        if (plugin.getFactionsManager().getPlayersFaction(targetUUID) != faction) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-not-member"));
            return;
        }
        if (faction.getLeader().equals(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-promote-self"));
            return;
        }
        if (faction.isOfficer(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-already-officer"));
            return;
        }

        faction.getOfficers().add(targetUUID);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("faction-promoted"));
    }
}
