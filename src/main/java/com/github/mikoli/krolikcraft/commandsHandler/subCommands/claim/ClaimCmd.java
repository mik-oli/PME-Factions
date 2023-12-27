package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCmd extends SubCommand {

    private final PMEFactions plugin;

    public ClaimCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getSyntax() {
        return "/claim claim [<claim type>]";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim claim <faction> <claim type>";
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
        return "pmefactions.claim.claim";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Faction faction = null;
        if (adminMode) faction = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        else faction = RequiredCmdArgs.getFactionByPlayer(plugin, player);

        if (faction == null) return; //TODO return message

        ClaimsManager claimsManager = plugin.getClaimsManager();
        ClaimType claimType = null;
        if (adminMode) claimType = RequiredCmdArgs.getClaimType(args[2]);
        else claimType = RequiredCmdArgs.getClaimType(args[1]);

        if (claimType == ClaimType.NEUTRAL) return; //TODO return message - cant claim as neutral

        Chunk chunk = player.getLocation().getChunk();
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, claimType,true)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }

        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
        claimsManager.createClaim(faction, chunk, claimType, blockBelow.getLocation());
        blockBelow.setType(Material.NOTE_BLOCK);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
