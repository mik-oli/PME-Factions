package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.factions.LoadSaveClaimsData;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Unclaim extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.ADMINMODE);
        }
    };

    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] unclaim";
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
    public void perform(Krolikcraft plugin, CommandSender commandSender, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getBlock().getChunk();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(chunk)) return;

        UUID claimId = claimsManager.getClaimId(chunk);
        UUID playerUUID = player.getUniqueId();
        if (!args[3].equals("true")) {
            if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return; //TODO player is not in faction
            if (!FactionsUtils.getPlayersFaction(plugin, playerUUID).getLeader().equals(playerUUID)) return; //TODO checking if player can unclaim
            if (FactionsUtils.getPlayersFaction(plugin, playerUUID).getId() != claimsManager.getClaimsOwnerMap().get(claimId)) return;//TODO players and claims faction is different
        }

        claimsManager.removeClaim(claimId);
        LoadSaveClaimsData.deleteClaimFromFile(plugin.getClaimsFilesUtil(), claimId);
    }
}
