package com.github.mikoli.rvfactions.factionsLogic.commands.subCommands.faction;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.rvfactions.factionsLogic.commands.ISubCommand;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;
import com.github.mikoli.rvfactions.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.RankPermissions;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class FactionsListCmd extends ISubCommand {

    private final RVFactions plugin;

    public FactionsListCmd(RVFactions plugin) {
        this.plugin = plugin;
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
        return false;
    }

    @Override
    public String getPermission() {
        return "pmefactions.faction.list";
    }

    @Override
    public RankPermissions requiredRank() {
        return RankPermissions.NULL;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(plugin.getConfigUtils().getLocalisation("factions-list"));
        for (Faction faction : plugin.getFactionsManager().getFactionsList().values()) {
            stringBuilder.append(faction.getName());
            stringBuilder.append(BukkitUtils.coloring("&e, &b"));
        }
        if (stringBuilder.lastIndexOf(",") != -1) stringBuilder.replace(stringBuilder.lastIndexOf(","), stringBuilder.length(), BukkitUtils.coloring("&e."));
        else stringBuilder.append(BukkitUtils.coloring("&e."));
        commandSender.sendMessage(stringBuilder.toString());
    }
}
