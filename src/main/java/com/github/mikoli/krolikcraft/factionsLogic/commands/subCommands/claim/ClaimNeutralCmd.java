package com.github.mikoli.krolikcraft.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.claims.ClaimType;
import com.github.mikoli.krolikcraft.factionsLogic.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.factionsLogic.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClaimNeutralCmd extends ISubCommand {

    private final PMEFactions plugin;

    public ClaimNeutralCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "claim-neutral";
    }

    @Override
    public String getSyntax() {
        return "/factions claim-neutral";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin claim-neutral";
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
        return "pmefactions.admin";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.ADMIN;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = player.getLocation().getChunk();
        ClaimType claimType = ClaimType.NEUTRAL;
        if (!claimsManager.checkIfCanCreateClaim(null, chunk, claimType,false)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }

        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
        claimsManager.createClaim(null, claimType, blockBelow.getLocation());
        blockBelow.setType(Material.NOTE_BLOCK);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
