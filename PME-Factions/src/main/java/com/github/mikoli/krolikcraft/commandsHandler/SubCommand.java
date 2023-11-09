package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    public abstract BaseCommand getBaseCmd();
    public abstract String getName();
    public abstract String getSyntax();
    public abstract String getAdminSyntax();
    public abstract int getArgsLength();
    public abstract boolean playerOnly();
    public abstract ArrayList<RequiredCmdArgs> requiredArguments();
    public abstract String getPermission();
    public abstract CommandsPermissions requiredPermission(ConfigUtils config);
    public abstract void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args);
}
