package com.github.mikoli.ringsvalley.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.ringsvalley.RVFactions;
import com.github.mikoli.ringsvalley.factionsLogic.claims.Claim;
import com.github.mikoli.ringsvalley.factionsLogic.claims.ClaimType;
import com.github.mikoli.ringsvalley.factionsLogic.claims.ClaimsManager;
import com.github.mikoli.ringsvalley.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.ringsvalley.factionsLogic.commands.ISubCommand;
import com.github.mikoli.ringsvalley.factionsLogic.factions.Faction;
import com.github.mikoli.ringsvalley.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ClaimChangeOwnerCmd extends ISubCommand {

    private final RVFactions plugin;

    public ClaimChangeOwnerCmd(RVFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "change-owner";
    }

    @Override
    public String getSyntax() {
        return "/factions claim-change-owner <faction>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin claim-change-owner <faction>";
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
        return true;
    }

    @Override
    public String getPermission() {
        return "pmefactions.claim.changeowner";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.ADMIN;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        Player player = Bukkit.getPlayer(commandSender.getName());
        ClaimsManager claimsManager = plugin.getClaimsManager();
        UUID claimId = claimsManager.getClaimId(player.getLocation().getChunk());
        Claim claim = claimsManager.getClaimsList().get(claimId);
        if (faction.getId().equals(claim.getOwner())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-change-owner"));
            return;
        }
        if (claim.getClaimType() == ClaimType.CORE) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-change-owner"));
            return;
        }
        claimsManager.changeOwnership(claim, faction);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
    }
}
