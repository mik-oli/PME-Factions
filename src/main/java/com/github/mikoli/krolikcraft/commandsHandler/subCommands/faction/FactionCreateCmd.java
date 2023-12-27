package com.github.mikoli.krolikcraft.commandsHandler.subCommands.faction;

import com.github.mikoli.krolikcraft.PMEFactions;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.PersistentDataKeys;
import com.github.mikoli.krolikcraft.utils.PersistentDataUtils;
import com.github.mikoli.krolikcraft.utils.RankPermissions;
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

public class FactionCreateCmd extends SubCommand {

    private final PMEFactions plugin;

    public FactionCreateCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTION;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getSyntax() {
        return "/faction create <name> <shortcut>";
    }

    @Override
    public String getAdminSyntax() {
        return "/faction-admin create <leader> <name> <shortcut>";
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
    public String getPermission() {
        return "pmefactions.faction.create";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        UUID leader = null;
        if (adminMode) leader = RequiredCmdArgs.getTargetPlayer(adminMode, args[1]);
        else leader = RequiredCmdArgs.getTargetPlayer(adminMode, args[1]);

        if (plugin.getFactionsManager().isPlayerInFaction(leader)) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-in-faction"));
            return;
        }

        String factionName = null;
        if (adminMode) factionName = args[2];
        else factionName = args[1];
        if (factionName.length() > plugin.getConfigUtils().getMaxLength("max-name-length")) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("name-to-long").replace("{0}", String.valueOf(plugin.getConfigUtils().getMaxLength("max-name-length"))));
            return;
        }
        for (Faction f : plugin.getFactionsManager().getFactionsList().values()) {
            if (f.getName().equalsIgnoreCase(factionName)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("name-already-exists"));
                return;
            }
        }

        String factionShortcut = null;
        if (adminMode) factionName = args[3];
        else factionName = args[2];
        if (factionShortcut.length() > plugin.getConfigUtils().getMaxLength("max-shortcut-length")) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-too-long").replace("{0}", String.valueOf(plugin.getConfigUtils().getMaxLength("max-shortcut-length"))));
            return;
        }
        for (Faction f : plugin.getFactionsManager().getFactionsList().values()) {
            if (f.getShortcut().equalsIgnoreCase(factionShortcut)) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("shortcut-already-exists"));
                return;
            }
        }

        if (adminMode) {
            ItemStack coreBlock = new ItemStack(Material.OCHRE_FROGLIGHT);
            ItemMeta meta = coreBlock.getItemMeta();
            meta.setDisplayName(Utils.coloring("&eFaction creation flag"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.coloring("&eName: &b") + factionName);
            lore.add(Utils.coloring("&eShortcut: &b") + factionShortcut);
            lore.add(Utils.coloring("&eLeader: &b") + leader);
            meta.setLore(lore);

            Player player = Bukkit.getPlayer(commandSender.getName());
            player.getInventory().addItem(coreBlock);

            PersistentDataUtils.setData(plugin, PersistentDataKeys.COREFLAG, meta.getPersistentDataContainer(), "true");
            coreBlock.setItemMeta(meta);
        }
        else {
            if (plugin.getFactionsManager().getPlayersFaction(leader) != null) {
                commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("target-in-faction"));
                return;
            }
            Location location = Bukkit.getPlayer(leader).getLocation();
            if (!plugin.getClaimsManager().checkIfCanCreateClaim(null, location.getChunk(), ClaimType.CORE, false)) return;
            plugin.getFactionsManager().createFaction(factionName, factionShortcut, leader, location);

            Block coreBlock = location.getBlock();
            coreBlock.setType(Material.OCHRE_FROGLIGHT);
            plugin.getClaimsManager().createClaim(plugin.getFactionsManager().getFactionFromName(factionName), coreBlock.getChunk(), ClaimType.CORE, location);
            Bukkit.getPlayer(leader).sendMessage(plugin.getConfigUtils().getLocalisation("faction-created"));
        }
    }
}
