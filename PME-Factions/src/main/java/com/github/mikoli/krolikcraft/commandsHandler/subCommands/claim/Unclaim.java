package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.claims.LoadSaveClaimsData;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Unclaim extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

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
        return "/claim unclaim";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim-admin unclaim";
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
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return this.requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.claim.unclaim";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("unclaim");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getBlock().getChunk();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(chunk)) return;

        UUID claimId = claimsManager.getClaimId(chunk);
        UUID playerUUID = player.getUniqueId();
        if (adminMode) {
            if (FactionsUtils.getPlayersFaction(plugin, playerUUID).getId() != claimsManager.getClaimsOwnerMap().get(claimId)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-unclaim"));
                return;
            }
        }

        claimsManager.removeClaim(claimId);
        LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("unclaimed"));
    }
}
