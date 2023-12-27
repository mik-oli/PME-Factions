package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetClaimIdCmd extends SubCommand {

    private final PMEFactions plugin;

    public GetClaimIdCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "id";
    }

    @Override
    public String getSyntax() {
        return "/claim id";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim id";
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
    public String getPermission() {
        return "pmefactions.claim.getid";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Location location = player.getLocation();
        commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&eClaim id: &b" + plugin.getClaimsManager().getClaimId(location.getChunk()).toString()));
    }
}
