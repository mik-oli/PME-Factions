package com.github.mikoli.krolikcraft.commands.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.Claim;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.commands.SubCommand;
import com.github.mikoli.krolikcraft.utils.BukkitUtils;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ClaimInfoCmd extends SubCommand {
    private final PMEFactions plugin;

    public ClaimInfoCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "claim-info";
    }

    @Override
    public String getSyntax() {
        return "/factions claim-info";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions claim-info";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.NONE);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.NONE);
        }};
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
        return RankPermissions.NULL;
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
            Claim claim = claimsManager.getClaim(claimId);
            UUID claimOwner = claim.getOwner();
            String ownerName = (claimOwner != null) ? plugin.getFactionsManager().getFactionsList().get(claimOwner).getName() : "none";
            String claimType = claim.getClaimType().name();
            Location location = claim.getCoreLocation();
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed-by") + ownerName);
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claim-type") + claimType);
            commandSender.sendMessage(BukkitUtils.coloring(BukkitUtils.pluginPrefix() + "&eClaim block (X, Y, Z): &b" + (int)location.getX() + " " + (int)location.getY() + " " + (int)location.getZ() + "&e."));
        }
    }
}
