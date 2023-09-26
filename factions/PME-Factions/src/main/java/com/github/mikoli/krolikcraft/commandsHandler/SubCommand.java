package com.github.mikoli.krolikcraft.commandsHandler;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getSyntax();
    public abstract boolean playerOnly();
    public abstract void perform(CommandSender commandSender, String[] args);
}
