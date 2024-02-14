package com.github.mikoli.krolikcraft.factionsLogic.commands.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.krolikcraft.factionsLogic.commands.ISubCommand;
import com.github.mikoli.krolikcraft.factionsLogic.factions.Faction;
import com.github.mikoli.krolikcraft.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class SetShortcutCmd extends ISubCommand {

    private final PMEFactions plugin;

    public SetShortcutCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "setshortcut";
    }

    @Override
    public String getSyntax() {
        return  "/factions setshortcut <shortcut>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin setshortcut <faction> <shortcut>";
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
        return "pmefactions.faction.setshortcut";
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

        String newShortcut = null;
        if (adminMode) newShortcut = args[2];
        else newShortcut = args[1];

        if (newShortcut.length() > plugin.getConfigUtils().getMaxLength("max-shortcut-length")) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-too-long").replace("{0}", String.valueOf(plugin.getConfigUtils().getMaxLength("max-shortcut-length"))));
            return;
        }
        for (Faction f : plugin.getFactionsManager().getFactionsList().values()) {
            if (f.getShortcut().equalsIgnoreCase(newShortcut)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-already-exists"));
                return;
            }
        }

        faction.setShortcut(newShortcut);
        commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-changed"));
    }
}
