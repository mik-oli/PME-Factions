package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimInfo extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

    @Override
    public String getName() {
        return "claim-info";
    }

    @Override
    public String getSyntax() {
        return "/factions claim-info";
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
        return "pmefactions.claim.info";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.ALL;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getChunk();

        if (plugin.getClaimsManager().isChunkClaimed(chunk))
            commandSender.sendMessage(Utils.coloring(Utils.pluginPrefix() + "&aThis chunk is not claimed by anyone."));
        else {
            UUID claimId = plugin.getClaimsManager().getClaimId(chunk);
            UUID claimOwner = plugin.getClaimsManager().getClaimsOwnerMap().get(claimId);
            String ownerName = plugin.getFactionsHashMap().get(claimOwner).getName();
            commandSender.sendMessage(Utils.coloring(Utils.pluginPrefix() + "&eThis chunk is not claimed by: &b" + ownerName + "&e."));
        }
    }
}
