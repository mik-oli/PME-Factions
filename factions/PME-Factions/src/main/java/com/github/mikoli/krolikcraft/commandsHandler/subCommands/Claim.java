package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
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
import java.util.List;

public class Claim extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.CLAIMTYPE);
        }
    };

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] claim [<faction>] [<claim type>]";
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
    public String getPermission() {
        return "pmefactions.claim.claim";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return config.getPermission("claim");
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {

        Faction faction = (Faction) args.get(0);
        Player player = Bukkit.getPlayer(commandSender.getName());

        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = player.getLocation().getChunk();
        ClaimType claimType = (ClaimType) args.get(1);
        if (!claimsManager.checkIfCanCreateClaim(faction, chunk, claimType,false)) return;
        claimsManager.createClaim(faction, chunk, claimType);

        Block blockBelow = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        blockBelow.setType(Material.NOTE_BLOCK);
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMBLOCK, PersistentDataUtils.getBlockContainer(blockBelow), "true");
    }
}
