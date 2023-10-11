package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Claim extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.CLAIMTYPE);
            add(RequiredCmdArgs.ADMINMODE);
        }
    };

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] [<faction>] claim [<claim type>]";
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

        Faction faction = plugin.getFactionsHashMap().get(args[0]);
        Player player = Bukkit.getPlayer(commandSender.getName());
        UUID playerUUID = player.getUniqueId();
        if (!args[3].equals("true")) {
            if (!FactionsUtils.isPlayerInFaction(plugin, playerUUID)) return;
            if (!FactionsUtils.getPlayersFaction(plugin, playerUUID).getLeader().equals(playerUUID)) return; //TODO checking if player can claim
        }

        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = player.getLocation().getChunk();
        ClaimType claimType = ClaimType.valueOf(args[2]);
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, claimType,false)) return;
        claimsManager.createClaim(faction, chunk, claimType);

        Block blockBelow = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        blockBelow.setType(Material.NOTE_BLOCK);
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(blockBelow), "true");
    }
}
