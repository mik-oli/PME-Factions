package com.github.mikoli.krolikcraft.commands.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.commands.SubCommand;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class UnclaimCmd extends SubCommand {

    private final PMEFactions plugin;

    public UnclaimCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getSyntax() {
        return "/factions unclaim";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin unclaim";
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
        return "pmefactions.claim.unclaim";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.OFFICER;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getBlock().getChunk();
        ClaimsManager claimsManager = plugin.getClaimsManager();
        if (!claimsManager.isChunkClaimed(chunk)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("not-claimed"));
            return;
        }

        UUID claimId = claimsManager.getClaimId(chunk);
        if (!adminMode) {
            if (!plugin.getFactionsManager().getPlayersFaction(player.getUniqueId()).getId().equals(claimsManager.getClaim(claimId).getOwner())) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-unclaim"));
                return;
            }
        }

        claimsManager.removeClaim(claimId);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("unclaimed"));
    }
}
