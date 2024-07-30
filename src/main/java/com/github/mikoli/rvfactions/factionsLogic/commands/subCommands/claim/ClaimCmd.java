package com.github.mikoli.rvfactions.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimType;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimsManager;
import com.github.mikoli.rvfactions.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.rvfactions.factionsLogic.commands.ISubCommand;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;
import com.github.mikoli.rvfactions.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClaimCmd extends ISubCommand {

    private final RVFactions plugin;

    public ClaimCmd(RVFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getSyntax() {
        return "/factions claim [<claim_type>]";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin claim <faction> <claim_type>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.CLAIM_TYPE);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
            add(CommandsArgs.CLAIM_TYPE);
        }};
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String getPermission() {
        return "pmefactions.claim.claim";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.OFFICER;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Faction faction = null;
        if (adminMode) faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        else faction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        ClaimType claimType = null;
        if (args.length == this.getLength(adminMode)) {
            if (adminMode) claimType = ClaimType.getClaimType(args[2]);
            else claimType = ClaimType.getClaimType(args[1]);
        }
        if (claimType == null) claimType = ClaimType.CLAIM_3x3;
        if (claimType == ClaimType.NEUTRAL) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-no-permission"));
            return;
        }

        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = player.getLocation().getChunk();
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, claimType,true)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }

        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
        blockBelow.setType(Material.CRYING_OBSIDIAN);
        claimsManager.createClaim(faction, claimType, blockBelow.getLocation());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
