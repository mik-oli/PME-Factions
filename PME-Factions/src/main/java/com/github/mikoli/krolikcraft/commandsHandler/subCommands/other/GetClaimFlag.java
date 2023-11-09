package com.github.mikoli.krolikcraft.commandsHandler.subCommands.other;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.commandsHandler.BaseCommand;
import com.github.mikoli.krolikcraft.commandsHandler.RequiredCmdArgs;
import com.github.mikoli.krolikcraft.commandsHandler.SubCommand;
import com.github.mikoli.krolikcraft.claims.ClaimType;
import com.github.mikoli.krolikcraft.factions.Faction;
import com.github.mikoli.krolikcraft.utils.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.List;

public class GetClaimFlag extends SubCommand {

    private final ArrayList<RequiredCmdArgs> requiredArgs = new ArrayList<RequiredCmdArgs>() {
        {
            add(RequiredCmdArgs.REQUESTFACTION);
            add(RequiredCmdArgs.CLAIMTYPE);
        }
    };

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
        return "/factions-admin get-claim-flag <faction> <claim type>";
    }

    @Override
    public int getArgsLength() {
        return 0;
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
    public String getPermission() {
        return "pmefactions.claim.getflag";
    }

    @Override
    public CommandsPermissions requiredPermission(ConfigUtils config) {
        return CommandsPermissions.NULL;
    }

    @Override
    public void perform(Krolikcraft plugin, CommandSender commandSender, boolean adminMode, List<Object> args) {

        Faction faction = (Faction) args.get(0);
        ClaimType claimType = (ClaimType) args.get(1);
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
