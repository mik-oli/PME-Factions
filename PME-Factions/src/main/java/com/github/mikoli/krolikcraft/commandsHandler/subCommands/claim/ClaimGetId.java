package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClaimGetId extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {};

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "get-id";
    }

    @Override
    public String getSyntax() {
        return "/claim get-id";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim-admin get-id";
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
        return requiredArgs;
    }

    @Override
    public String getPermission() {
        return "pmefactions.claim.getid";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.ADMIN;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Location location = player.getLocation();
        commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&eClaim id: &b" + plugin.getClaimsManager().getClaimId(location.getChunk()).toString()));
    }
}
