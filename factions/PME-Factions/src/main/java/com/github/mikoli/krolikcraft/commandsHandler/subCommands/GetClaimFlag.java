package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GetClaimFlag extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.FACTION);
            add(RequiredCmdArgs.CLAIMTYPE);
        }
    };

    @Override
    public String getName() {
        return "get-claim-flag";
    }

    @Override
    public String getSyntax() {
        return "/factions admin <faction> get-claim-flag <claim type>";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return this.requiredArgs;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, String[] args) {
        Faction faction = plugin.getFactionsHashMap().get(args[0]);
        ClaimType claimType = ClaimType.valueOf(args[2]);
        Player player = Bukkit.getPlayer(commandSender.getName());
        ItemStack item = player.getInventory().getItemInMainHand();
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMFLAG, PersistentDataUtils.getItemContainer(item), "true");
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMTYPE, PersistentDataUtils.getItemContainer(item), claimType.name());
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMOWNER, PersistentDataUtils.getItemContainer(item), faction.getId().toString());
    }
}
