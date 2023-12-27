package com.github.mikoli.krolikcraft.commandsHandler.subCommands.claim;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClaimAddChunkCmd extends SubCommand {

    private final PMEFactions plugin;

    public ClaimAddChunkCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.CLAIM;
    }

    @Override
    public String getName() {
        return "add-chunk";
    }

    @Override
    public String getSyntax() {
        return "/claim add-chunk <claim id>";
    }

    @Override
    public String getAdminSyntax() {
        return "/claim add-chunk <claim id>";
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
    public String getPermission() {
        return "pmefactions.claim.addchunk";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Player player = Bukkit.getPlayer(commandSender.getName());
        if (plugin.getClaimsManager().isChunkClaimed(player.getLocation().getChunk())) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("already-claimed"));
            return;
        }

        UUID claimId = RequiredCmdArgs.getUUID(args[1]);
        plugin.getClaimsManager().getClaimsList().get(claimId).addChunkToClaim(player.getLocation().getChunk());
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("claimed"));
    }
}
