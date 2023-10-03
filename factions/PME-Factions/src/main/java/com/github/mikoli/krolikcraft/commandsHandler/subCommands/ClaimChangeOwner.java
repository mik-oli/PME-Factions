package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.factions.Faction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ClaimChangeOwner extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
        }
    };

    @Override
    public String getName() {
        return "claim-change-owner";
    }

    @Override
    public String getSyntax() {
        return "/factions admin claim-change-owner <new owner>";
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
        ClaimsManager claimsManager = plugin.getClaimsManager();
        UUID claimId = claimsManager.getClaimId(player.getLocation().getChunk());
        claimsManager.changeClaimOwner(claimId, faction);
        //TODO message faction changed
    }
}
