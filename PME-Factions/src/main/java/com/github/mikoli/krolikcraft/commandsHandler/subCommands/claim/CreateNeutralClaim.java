package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateNeutralClaim extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "claim-neutral";
    }

    @Override
    public String getSyntax() {
        return "/claim claim-neutral";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim claim-neutral";
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
        return "pmefactions.admin";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.NULL;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        ClaimsManager claimsManager = plugin.getClaimsManager();
        Chunk chunk = player.getLocation().getChunk();
        ClaimType claimType = ClaimType.NEUTRAL;
        if (!claimsManager.checkIfCanCreateClaim(null, chunk, claimType,false)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }

        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();
        claimsManager.createClaim(null, chunk, claimType, blockBelow.getLocation());
        blockBelow.setType(Material.NOTE_BLOCK);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
