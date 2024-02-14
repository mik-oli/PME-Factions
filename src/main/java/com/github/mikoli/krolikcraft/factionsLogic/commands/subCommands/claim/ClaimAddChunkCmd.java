package com.github.mikoli.krolikcraft.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.factionsLogic.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ClaimAddChunkCmd extends ISubCommand {

    private final PMEFactions plugin;

    public ClaimAddChunkCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "add-chunk";
    }

    @Override
    public String getSyntax() {
        return "/factions add-chunk <claim_id>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin add-chunk <claim_id>";
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
        return "pmefactions.claim.addchunk";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.ADMIN;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        if (plugin.getClaimsManager().isChunkClaimed(player.getLocation().getChunk())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }

        UUID claimId = null;
        try {
            claimId = UUID.fromString(args[1]);
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claim-not-found"));
            return;
        }

        plugin.getClaimsManager().getClaimsList().get(claimId).addChunkToClaim(player.getLocation().getChunk());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
