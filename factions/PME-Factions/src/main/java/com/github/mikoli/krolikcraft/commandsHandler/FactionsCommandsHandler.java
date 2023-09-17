package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FactionsCommandsHandler implements CommandExecutor {

    private final Krolikcraft plugin;

    public FactionsCommandsHandler(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (command.getName().equals("factions")) {
            if (args.length == 0) return false;
            else {
                String cmd = args[0];
                switch (cmd.toLowerCase()) {
                    case "status": return staffWLStatus(commandSender);
                    case "on": return staffWLOn(commandSender);
                    case "off": return staffWLOff(commandSender);
                    case "list": return staffWLList(commandSender);
                    case "add": return staffWLAdd(commandSender, args);
                    case "remove": return staffWLRemove(commandSender, args);
                    default: return false;
                }
            }
        }

        return false;
    }
}
