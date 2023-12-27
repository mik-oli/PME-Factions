package com.github.mikoli.krolikcraft.commandsHandler.subCommands.other;

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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.List;

public class GetClaimFlagCmd extends SubCommand {

    private final PMEFactions plugin;

    public GetClaimFlagCmd(PMEFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseCommand getBaseCmd() {
        return BaseCommand.FACTIONS;
    }

    @Override
    public String getName() {
        return "get-claim-flag";
    }

    @Override
    public String getSyntax() {
        return "/factions get-claim-flag <faction> <claim type>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions get-claim-flag <faction> <claim type>";
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
        return "pmefactions.claim.getflag";
    }

    @Override
    public RankPermissions requiredRank() {
        return null;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = null;
        if (adminMode) faction = RequiredCmdArgs.getFactionByName(plugin, args[1]);
        else faction = RequiredCmdArgs.getFactionByPlayer(plugin, Bukkit.getPlayer(commandSender.getName()));
        if (faction == null) return; //TODO return message

        ClaimType claimType = null;
        if (adminMode) claimType = RequiredCmdArgs.getClaimType(args[2]);
        else claimType = RequiredCmdArgs.getClaimType(args[1]);

        Player player = Bukkit.getPlayer(commandSender.getName());
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null || !item.getData().getItemType().isBlock()) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("item-needed"));
            return;
        }

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMFLAG, dataContainer, "true");
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMTYPE, dataContainer, claimType.name());
        PersistentDataUtils.setData(plugin, PersistentDataKeys.CLAIMOWNER, dataContainer, faction.getId().toString());

        itemMeta.setDisplayName(Utils.coloring("&eClaim Flag"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.coloring("&eOwner: &a") + faction.getName());
        lore.add(Utils.coloring("&eType: &a") + claimType.name());
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}
