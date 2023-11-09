package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimInfo extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getSyntax() {
        return "/claim info";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim-admin info";
    }

    @Override
    public int getArgsLength() {
        return 0;
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
        return CommandsPermissions.NULL;
    }

    @Override
    public void perform(PMEFactions plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getChunk();

        if (!plugin.getClaimsManager().isChunkClaimed(chunk))
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("not-claimed"));
        else {
            UUID claimId = plugin.getClaimsManager().getClaimId(chunk);
            UUID claimOwner = plugin.getClaimsManager().getClaimsOwnerMap().get(claimId);
            String ownerName = plugin.getFactionsHashMap().get(claimOwner).getName();
            Location location = plugin.getClaimsManager().getClaimCoreLocation().get(claimId);
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed-by") + ownerName);
            commandSender.sendMessage(Utils.coloring(Utils.pluginPrefix() + "&eClaim block (X, Y, Z): &b" + (int)location.getX() + " " + (int)location.getY() + " " + (int)location.getZ() + "&e."));
        }
    }
}
