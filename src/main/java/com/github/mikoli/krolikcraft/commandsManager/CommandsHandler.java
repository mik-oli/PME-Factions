package com.github.mikoli.krolikcraft.commandsManager;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandsHandler implements CommandExecutor {

    private final Krolikcraft plugin;

    public CommandsHandler(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (command.getName().equals("staffwl")) {
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

    private boolean staffWLRemove(CommandSender commandSender, String[] args) {
        if (args.length < 2) return false;
        Player player = plugin.getServer().getPlayer(args[1]);
        if (plugin.getStaffWL().getWhitelistedPlayers().contains(player.getUniqueId())) {
            commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&cPlayer is not on the whitelist."));
        }
        else {
            plugin.getStaffWL().removeUserFromList(player);
            commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&ePlayer removed from the whitelist."));
        }

        return true;
    }

    private boolean staffWLAdd(CommandSender commandSender, String[] args) {
        if (args.length < 2) return false;
        Player player = plugin.getServer().getPlayer(args[1]);
        if (plugin.getStaffWL().getWhitelistedPlayers().contains(player.getUniqueId())) {
            commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&cPlayer is already on the whitelist."));
        }
        else {
            plugin.getStaffWL().addUserToList(player);
            commandSender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&ePlayer added to the whitelist."));
        }

        return true;
    }

    private boolean staffWLList(CommandSender commandSender) {
        StringBuilder list = new StringBuilder();
        list.append(Utils.pluginPrefix()).append(Utils.coloring("&6Whitelisted players: &e"));

        for (UUID uuid : plugin.getStaffWL().getWhitelistedPlayers()) {
            Player player = plugin.getServer().getPlayer(uuid);
            list.append(player).append(", ");
        }
        list.delete(list.length() - 2, list.length());
        commandSender.sendMessage(list.toString());

        return true;
    }

    private boolean staffWLOff(CommandSender sender) {
        if (!plugin.getStaffWL().getWhiteListStatus()) sender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&cStaff whitelist is currently disabled."));
        else plugin.getStaffWL().setWhiteListStatus(false);

        return true;
    }

    private boolean staffWLOn(CommandSender sender) {
        if (plugin.getStaffWL().getWhiteListStatus()) sender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&aStaff whitelist is currently enabled."));
        else plugin.getStaffWL().setWhiteListStatus(true);

        return true;
    }

    private boolean staffWLStatus(CommandSender sender) {
        boolean status = plugin.getStaffWL().getWhiteListStatus();
        if (status) sender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&aStaff whitelist is currently enabled."));
        else sender.sendMessage(Utils.pluginPrefix() + Utils.coloring("&cStaff whitelist is currently disabled."));

        return true;
    }
}
