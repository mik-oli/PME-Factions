package com.github.mikoli.ringsvalley.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.ringsvalley.RVFactions;
import com.github.mikoli.ringsvalley.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.ringsvalley.factionsLogic.commands.ISubCommand;
import com.github.mikoli.ringsvalley.factionsLogic.events.ClaimChangeEvent;
import com.github.mikoli.ringsvalley.factionsLogic.events.ClaimChangeType;
import com.github.mikoli.ringsvalley.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.ringsvalley.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ClaimAddChunkCmd extends ISubCommand {

    private final RVFactions plugin;

    public ClaimAddChunkCmd(RVFactions plugin) {
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

        plugin.getClaimsManager().getClaim(claimId).addChunkToClaim(player.getLocation().getChunk());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
        BukkitUtils.callEvent(new ClaimChangeEvent(plugin.getClaimsManager().getClaim(claimId), ClaimChangeType.ADD_CHUNK));
    }
}
