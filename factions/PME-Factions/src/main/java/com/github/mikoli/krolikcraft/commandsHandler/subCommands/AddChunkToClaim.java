package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddChunkToClaim extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

    @Override
    public String getName() {
        return "claim-add-chunk";
    }

    @Override
    public String getSyntax() {
        return "/factions claim-add-chunk <claim id>";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.claim.addchunk";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.ADMIN;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {

        UUID claimId = (UUID) args.get(0);
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (plugin.getClaimsManager().isChunkClaimed(player.getLocation().getChunk())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }
        plugin.getClaimsManager().addChunkToClaim(claimId, player.getLocation().getChunk());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
