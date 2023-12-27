package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class SetShortcutCmd extends SubCommand {

    private final PMEFactions plugin;

    public SetShortcutCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "setshortcut";
    }

    @Override
    public String getSyntax() {
        return  "/faction setshortcut <shortcut>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin setshortcut <faction> <shortcut>";
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
        return "pmefactions.faction.setshortcut";
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
