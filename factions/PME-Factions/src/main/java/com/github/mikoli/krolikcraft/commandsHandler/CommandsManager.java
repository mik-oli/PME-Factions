package com.github.mikoli.krolikcraft.commandsHandler;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.subCommands.*;
import com.github.mikoli.krolikcraft.factions.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class CommandsManager implements CommandExecutor {

    private final Krolikcraft plugin;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandsManager(Krolikcraft plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
        this.loadSubCommands();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        String cmd;
        if (args[0].equals("admin")) {
            cmd = args[2];
        }
        else {
            cmd = args[0];
        }

        SubCommand subCommand = null;
        for (SubCommand subCmd : subCommands) {
            if (subCmd.getName().equalsIgnoreCase(cmd)) subCommand = subCmd;
        }
        if (subCommand == null) return false; //TODO command not found
        if (subCommand.playerOnly() && !(commandSender instanceof Player)) return true; //todo player only command message

        Faction faction = null;
        UUID targetPlayer = null;
        ClaimType claimType = null;
        boolean adminMode = false;

        for (String arg : args) {
            faction = FactionsUtils.getFactionFromName(plugin, arg);
            try {
                claimType = ClaimType.valueOf(arg);
                targetPlayer = UUID.fromString(arg);
            } catch (IllegalArgumentException ignored) {}
            adminMode = arg.equalsIgnoreCase("admin");
        }

        //TODO return error and syntax
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.FACTION) && faction == null) {
            return false;
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.TARGETPLAYER) && targetPlayer == null) {
            return false;
        }
        if (subCommand.requiredArguments().contains(RequiredCmdArgs.CLAIMTYPE) && claimType == null) {
            return false;
        }

        String[] argsToPass = new String[] {faction.getId().toString(), targetPlayer.toString(), claimType.name(), String.valueOf(adminMode)};
        subCommand.perform(plugin, commandSender, argsToPass);

        return true;
    }

    private void loadSubCommands() {
        subCommands.add(new Claim());
        subCommands.add(new ClaimChangeOwner());
    }
}
