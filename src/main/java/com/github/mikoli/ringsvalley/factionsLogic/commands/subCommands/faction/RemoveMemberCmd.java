package com.github.mikoli.ringsvalley.factionsLogic.commands.subCommands.faction;

import com.github.mikoli.ringsvalley.RVFactions;
import com.github.mikoli.ringsvalley.factionsLogic.TeamsManager;
import com.github.mikoli.ringsvalley.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.ringsvalley.factionsLogic.commands.ISubCommand;
import com.github.mikoli.ringsvalley.factionsLogic.factions.Faction;
import com.github.mikoli.ringsvalley.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.ringsvalley.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

public class RemoveMemberCmd extends ISubCommand {

    private final RVFactions plugin;

    public RemoveMemberCmd(RVFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getSyntax() {
        return "/factions remove <player>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin remove <faction> <player>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.MEMBER_PLAYER);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
            add(CommandsArgs.MEMBER_PLAYER);
        }};
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.member.remove";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.OFFICER;
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
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-kick-leader"));
            return;
        }
        if (faction.getOfficers().contains(targetUUID)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-kick-leader"));
            return;
        }

        faction.removeMember(targetUUID);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("player-removed"));

        TeamsManager teamsManager = plugin.getTeamsManager();
        teamsManager.removePlayerFromTeam(targetUUID, teamsManager.getTeam(faction.getName()));
    }
}
