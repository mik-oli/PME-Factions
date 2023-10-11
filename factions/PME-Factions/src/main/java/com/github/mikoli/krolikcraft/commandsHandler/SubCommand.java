package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getSyntax();
    public abstract boolean playerOnly();
    public abstract ArrayList<RequiredCmdArgs> requiredArguments();
    public abstract void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args);
}
