package com.github.mikoli.krolikcraft.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.factionsLogic.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GetClaimIdCmd extends ISubCommand {

    private final PMEFactions plugin;

    public GetClaimIdCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "claim-id";
    }

    @Override
    public String getSyntax() {
        return "/factions claim-id";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin claim-id";
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
        return "pmefactions.claim.getid";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.ADMIN;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        Chunk chunk = player.getLocation().getChunk();
        commandSender.sendMessage(BukkitUtils.pluginPrefix() + BukkitUtils.coloring("&eClaim id: &b" + plugin.getClaimsManager().getClaimId(chunk)));
    }
}
