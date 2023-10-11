package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
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

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.TARGETPLAYER);
            add(RequiredCmdArgs.ADMINMODE);
            add(RequiredCmdArgs.NAME);
            add(RequiredCmdArgs.SHORTCUT);
        }
    };

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getSyntax() {
        return "/factions [admin] create [<leader>] <name> <shortcut>";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public ArrayList<RequiredCmdArgs> requiredArguments() {
        return requiredArgs;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {
        UUID leader = (UUID) args.get(0);
        //TODO checking if player can create faction

        String factionName = (String) args.get(2);
        String factionShortcut = (String) args.get(3);
        //TODO checking if name is longer and shorter than value in config
        //TODO checking if shortcut is longer and shorter than value in config

        if (FactionsUtils.getPlayersFaction(plugin, leader) != null) return; //TODO error player is in faction

        boolean adminMode = (boolean) args.get(1);
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
            if (!plugin.getClaimsManager().checkIfCanCreateClaim(null, location.getChunk(), ClaimType.CORE, false)) return;
            FactionsUtils.createFaction(plugin, factionName, leader, location);
            Block coreBlock = location.getBlock();
            coreBlock.setType(Material.END_CRYSTAL);
            PersistentDataUtils.setData(plugin, PersistentDataKeys.COREBLOCK, PersistentDataUtils.getBlockContainer(coreBlock), "true");
            plugin.getClaimsManager().createClaim(FactionsUtils.getFactionFromName(plugin, factionName), coreBlock.getChunk(), ClaimType.CORE);
        }
    }
}
