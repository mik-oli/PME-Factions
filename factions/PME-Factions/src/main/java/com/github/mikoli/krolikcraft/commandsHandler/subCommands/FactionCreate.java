package com.github.mikoli.krolikcraft.commandsHandler.subCommands;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.FactionsUtils;
import com.github.mikoli.krolikcraft.utils.*;

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
    public int getArgsLength() {
        return 2;
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
    public String getPermission() {
        return "pmefactions.faction.create";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.NULL;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, List<Object> args) {
        UUID leader = (UUID) args.get(0);
        String factionName = (String) args.get(2);
        String factionShortcut = (String) args.get(3);
        //TODO checking if name is longer and shorter than value in config
        //TODO checking if shortcut is longer and shorter than value in config

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
            if (FactionsUtils.getPlayersFaction(plugin, leader) != null) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-in-faction"));
                return;
            }
            Location location = Bukkit.getPlayer(leader).getLocation();
            if (!plugin.getClaimsManager().checkIfCanCreateClaim(null, location.getChunk(), ClaimType.CORE, false)) return;
            FactionsUtils.createFaction(plugin, factionName, factionShortcut, leader, location);

//            EnderCrystal enderCrystal = (EnderCrystal) location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
//            enderCrystal.setShowingBottom(false);
//            Block coreBlock = location.subtract(0, 1, 0).getBlock();
//            coreBlock.setType(Material.OBSIDIAN);

            Block coreBlock = location.getBlock();
            coreBlock.setType(Material.GLOWSTONE);
            plugin.getClaimsManager().createClaim(FactionsUtils.getFactionFromName(plugin, factionName), coreBlock.getChunk(), ClaimType.CORE, location);
        }
    }
}
