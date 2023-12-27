package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.utils.RankPermissions;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract BaseCommand getBaseCmd();
    public abstract String getName();
    public abstract String getSyntax();
    public abstract String getAdminSyntax();
    public abstract int getArgsLength();
    public abstract boolean playerOnly();
    public abstract String getPermission();
    public abstract RankPermissions requiredRank();
    public abstract void perform(CommandSender commandSender, boolean adminMode, String[] args);
}
