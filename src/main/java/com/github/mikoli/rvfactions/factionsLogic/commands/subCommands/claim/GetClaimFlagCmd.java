package com.github.mikoli.rvfactions.factionsLogic.commands.subCommands.claim;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.claims.ClaimType;
import com.github.mikoli.rvfactions.factionsLogic.commands.CommandsArgs;
import com.github.mikoli.rvfactions.factionsLogic.commands.ISubCommand;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;
import com.github.mikoli.rvfactions.factionsLogic.utils.BukkitUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.PersistentDataUtils;
import com.github.mikoli.rvfactions.factionsLogic.utils.RankPermissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.List;

public class GetClaimFlagCmd extends ISubCommand {

    private final RVFactions plugin;

    public GetClaimFlagCmd(RVFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "get-flag";
    }

    @Override
    public String getSyntax() {
        return "/factions get-flag <faction> <claim_type>";
    }

    @Override
    public String getAdminSyntax() {
        return "/factions-admin get-flag <faction> <claim_type>";
    }

    @Override
    public ArrayList<CommandsArgs> getArgs() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
            add(CommandsArgs.CLAIM_TYPE);
        }};
    }

    @Override
    public ArrayList<CommandsArgs> getArgsAdmin() {
        return new ArrayList<CommandsArgs>() {{
            add(CommandsArgs.FACTION);
            add(CommandsArgs.CLAIM_TYPE);
        }};
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
        return RankPermissions.ADMIN;
    }

    @Override
    public void perform(CommandSender commandSender, boolean adminMode, String[] args) {

        Faction faction = plugin.getFactionsManager().getFactionFromName(args[1]);
        if (faction == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-faction-not-found"));
            return;
        }

        ClaimType claimType = ClaimType.getClaimType(args[2]);
        if (claimType == null) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("cmd-claim-type-not-found"));
            return;
        }

        Player player = Bukkit.getPlayer(commandSender.getName());
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null || !item.getData().getItemType().isBlock()) {
            commandSender.sendMessage(plugin.getConfigUtils().getLocalisation("item-needed"));
            return;
        }

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        PersistentDataUtils.setData(plugin, PersistentDataUtils.CLAIMFLAG, dataContainer, "true");
        PersistentDataUtils.setData(plugin, PersistentDataUtils.CLAIMTYPE, dataContainer, claimType.name());
        PersistentDataUtils.setData(plugin, PersistentDataUtils.CLAIMOWNER, dataContainer, faction.getId().toString());

        itemMeta.setDisplayName(BukkitUtils.coloring("&eClaim Flag"));
        List<String> lore = new ArrayList<>();
        lore.add(BukkitUtils.coloring("&eOwner: &a") + faction.getName());
        lore.add(BukkitUtils.coloring("&eType: &a") + claimType.name());
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
}
