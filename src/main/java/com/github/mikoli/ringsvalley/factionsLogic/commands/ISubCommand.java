package com.github.mikoli.ringsvalley.factionsLogic.commands;

import com.github.mikoli.ringsvalley.factionsLogic.utils.RankPermissions;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public abstract class ISubCommand {

    public abstract String getName();
    public abstract String getSyntax();
    public abstract String getAdminSyntax();
    public abstract ArrayList<CommandsArgs> getArgs();
    public abstract ArrayList<CommandsArgs> getArgsAdmin();
    public abstract boolean playerOnly();
    public abstract String getPermission();
    public abstract RankPermissions requiredRank();
    public abstract void perform(CommandSender commandSender, boolean adminMode, String[] args);

    public int getLength(boolean adminMode) {
        if (adminMode) return getAdminSyntax().split(" ").length - 1;
        else return getSyntax().split(" ").length - 1;
    }
}
