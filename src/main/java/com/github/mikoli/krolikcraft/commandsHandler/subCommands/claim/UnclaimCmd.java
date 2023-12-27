package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.claims.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UnclaimCmd extends SubCommand {

    private final PMEFactions plugin;

    public UnclaimCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getSyntax() {
        return "/claim-admin unclaim";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim unclaim";
    }

    @Override
    public int getArgsLength() {
        return 0;
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String getPermission() {
        return "pmefactions.claim.unclaim";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getBlock().getChunk();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(chunk)) return; //TODO return message

        UUID claimId = claimsManager.getClaimId(chunk);
        if (!adminMode) {
            if (RequiredCmdArgs.getFactionByPlayer(plugin, player).getId().equals(claimsManager.getClaimsList().get(claimId).getClaimOwner())) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-unclaim"));
                return;
            }
        }

        claimsManager.removeClaim(claimId);
        LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("unclaimed"));
    }
}
