package com.github.mikoli.krolikcraft.commands.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.commands.ISubCommand;
import com.github.mikoli.krolikcraft.events.FactionChangeEvent;
import com.github.mikoli.krolikcraft.events.FactionChangeType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.BukkitUtils;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class SetNameCmd extends ISubCommand {

    private final PMEFactions plugin;

    public SetNameCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "setname";
    }

    @Override
    public String getSyntax() {
        return "/factions setname <name>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin setname <faction> <name>";
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
            add(CommandsArgs.FACTION);
        }};
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
        return RankPermissions.LEADER;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = null;
        if (adminMode) faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        else faction = plugin.getFactionsManager().getPlayersFaction(Bukkit.getPlayer(commandSender.getName()).getUniqueId());
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

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
        BukkitUtils.callEvent(new FactionChangeEvent(faction, FactionChangeType.UPDATE_NAME));
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("name-changed"));
    }
}
