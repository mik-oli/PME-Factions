package com.github.mikoli.krolikcraft.commandsHandler.subCommands.other;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.command.CommandSender;

public class FactionsListCmd extends SubCommand {

    private final PMEFactions plugin;

    public FactionsListCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTIONS;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getSyntax() {
        return "/factions list";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions list";
    }

    @Override
    public int getArgsLength() {
        return 0;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.list";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("factions-list"));
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            stringBuilder.append(faction.getName());
            stringBuilder.append(Utils.coloring("&e, &b"));
        }
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), Utils.coloring("&e."));
        commandSender.sendMessage(stringBuilder.toString());
    }
}
