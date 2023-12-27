package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SetNameCmd extends SubCommand {

    private final PMEFactions plugin;

    public SetNameCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "setname";
    }

    @Override
    public String getSyntax() {
        return "/faction setname <name>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin setname <faction> <name>";
    }

    @Override
    public int getArgsLength() {
        return 1;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.setname";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = null;
        if (adminMode) faction = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        else faction = RequiredCmdArgs.getFactionByPlayer(plugin, Bukkit.getPlayer(commandSender.getName()));
        if (faction == null) return; //TODO return message

        String newName = null;
        if (adminMode) newName = args[2];
        else newName = args[1];

        if (newName.length() > plugin.getConfigUtils().getMaxLength("max-name-length")) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("name-to-long").replace("{0}", String.valueOf(plugin.getConfigUtils().getMaxLength("max-name-length"))));
            return;
        }
        for (Faction f : plugin.getFactionsManager().getFactionsList().values()) {
            if (f.getName().equalsIgnoreCase(newName)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("name-already-exists"));
                return;
            }
        }

        faction.setName(newName);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("name-changed"));
    }
}
