package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.Claim;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClaimChangeOwnerCmd extends SubCommand {

    private final PMEFactions plugin;

    public ClaimChangeOwnerCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "change-owner";
    }

    @Override
    public String getSyntax() {
        return "/claim change-owner <faction>";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim change-owner <faction>";
    }

    @Override
    public int getArgsLength() {
        return 1;
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
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        if (faction == null) return; //TODO return message

        Player player = Bukkit.getPlayer(commandSender.getName());
        ClaimsManager claimsManager = plugin.getClaimsManager();
        UUID claimId = claimsManager.getClaimId(player.getLocation().getChunk());
        Claim claim = claimsManager.getClaimsList().get(claimId);
        if (claim.getClaimType() == ClaimType.CORE) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-change-owner"));
            return;
        }
        claimsManager.changeClaimOwner(claim, faction);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
    }
}
