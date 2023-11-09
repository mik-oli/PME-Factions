package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimChangeOwner extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.REQUESTFACTION);
        }
    };

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "change-owner";
    }

    @Override
    public String getSyntax() {
        return "/claim change-owner <faction>";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim-admin change-owner <faction>";
    }

    @Override
    public int getArgsLength() {
        return 1;
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
        return "pmefactions.claim.changeowner";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.ADMIN;
    }

    @Override
    public void perform(PMEFactions plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Faction faction = (Faction) args.get(0);
        Player player = Bukkit.getPlayer(commandSender.getName());
        ClaimsManager claimsManager = plugin.getClaimsManager();
        UUID claimId = claimsManager.getClaimId(player.getLocation().getChunk());
        if (claimsManager.getClaimsTypesMap().get(claimId) == ClaimType.CORE) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cant-change-owner"));
            return;
        }
        claimsManager.changeClaimOwner(claimId, faction);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("owner-changed"));
    }
}
