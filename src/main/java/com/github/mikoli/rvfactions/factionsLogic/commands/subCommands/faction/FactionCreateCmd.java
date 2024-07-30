package com.github.mikoli.rvfactions.factionsLogic.commands.subCommands.faction;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimType;
import com.github.mikoli.rvfactions.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.rvfactions.factionsLogic.commands.ISubCommand;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;
import com.github.mikoli.rvfactions.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.PersistentDataUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.RankPermissions;

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

public class FactionCreateCmd extends ISubCommand {

    private final RVFactions plugin;

    public FactionCreateCmd(RVFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getSyntax() {
        return "/factions create <name> <shortcut>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin create <leader> <name> <shortcut>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.NONE);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.ONLINE_PLAYER);
        }};
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
        return RankPermissions.NULL;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        UUID leader = null;
        if (adminMode) leader = BukkitUtils.getPlayerUUID(args[1]);
        else leader = BukkitUtils.getPlayerUUID(commandSender.getName());
        if (leader == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-player-not-found"));
            return;
        }

        if (plugin.getFactionsManager().getPlayersFaction(leader) != null) {
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
        if (adminMode) factionShortcut = args[3];
        else factionShortcut = args[2];
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
            meta.setDisplayName(BukkitUtils.coloring("&eFaction creation flag"));
            List<String> lore = new ArrayList<>();
            lore.add(BukkitUtils.coloring("&eName: &b") + factionName);
            lore.add(BukkitUtils.coloring("&eShortcut: &b") + factionShortcut);
            lore.add(BukkitUtils.coloring("&eLeader: &b") + leader);
            meta.setLore(lore);

            Player player = Bukkit.getPlayer(commandSender.getName());
            player.getInventory().addItem(coreBlock);

            PersistentDataUtils.setData(plugin, PersistentDataUtils.COREFLAG, meta.getPersistentDataContainer(), "true");
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
            plugin.getClaimsManager().createClaim(plugin.getFactionsManager().getFactionFromName(factionName), ClaimType.CORE, location);
            Bukkit.getPlayer(leader).sendMessage(plugin.getConfigUtils().getLocalisation("faction-created"));
        }
    }
}
