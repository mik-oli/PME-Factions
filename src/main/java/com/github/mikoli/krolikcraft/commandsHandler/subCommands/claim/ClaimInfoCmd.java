package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClaimInfoCmd extends SubCommand {

    private final PMEFactions plugin;

    public ClaimInfoCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "/claim info";
    }

    @Override
    public String getSyntax() {
        return "/claim info";
    }

    @Override
    public String getAdminSyntax() {
        return null;
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
        return "pmefactions.claim.info";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getChunk();

        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(chunk))
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("not-claimed"));
        else {
            UUID claimId = claimsManager.getClaimId(chunk);
            UUID claimOwner = claimsManager.getClaimsList().get(claimId).getClaimOwner();
            String ownerName = plugin.getFactionsManager().getFactionsList().get(claimOwner).getName();
            Location location = claimsManager.getClaimsList().get(claimId).getCoreLocation();
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed-by") + ownerName);
            commandSender.sendMessage(Utils.coloring(Utils.pluginPrefix() + "&eClaim block (X, Y, Z): &b" + (int)location.getX() + " " + (int)location.getY() + " " + (int)location.getZ() + "&e."));
        }
    }
}
