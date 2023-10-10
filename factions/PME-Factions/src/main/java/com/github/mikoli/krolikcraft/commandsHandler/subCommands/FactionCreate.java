package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionCreate extends SubCommand {

    @Override
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return null;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] create [<leader>] <name>";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, String[] args) {
        UUID leader = UUID.fromString(args[0]);
        //TODO checking if player can create faction

        String factionName = args[1];
        String factionShortcut = null;
        //TODO checking if name is longer and shorter than value in config
        //TODO checking if shortcut is longer and shorter than value in config

        if (FactionsUtils.getPlayersFaction(plugin, leader) != null) return; //TODO error player is in faction

        boolean adminMode = Boolean.getBoolean(args[2]);
        if (adminMode) {
            ItemStack coreBlock = new ItemStack(Material.END_CRYSTAL);
            ItemMeta meta = coreBlock.getItemMeta();
            meta.setDisplayName(Utils.coloring("&eFaction creation flag"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.coloring("&eName: &b") + factionName);
            lore.add(Utils.coloring("&eShortcut: &b") + factionShortcut);
            lore.add(Utils.coloring("&eLeader: &b") + leader);
            meta.setLore(lore);

            Player player = Bukkit.getPlayer(commandSender.getName());
            player.getInventory().addItem(coreBlock);

            PersistentDataUtils.setData(plugin, PersistentDataKeys.COREFLAG, PersistentDataUtils.getItemContainer(coreBlock), "true");
        }
        else {
            Location location = Bukkit.getPlayer(leader).getLocation();
            FactionsUtils.createFaction(plugin, factionName, leader, location);
            Block coreBlock = location.getBlock();
            coreBlock.setType(Material.END_CRYSTAL);
            PersistentDataUtils.setData(plugin, PersistentDataKeys.COREBLOCK, PersistentDataUtils.getBlockContainer(coreBlock), "true");
        }
    }
}
