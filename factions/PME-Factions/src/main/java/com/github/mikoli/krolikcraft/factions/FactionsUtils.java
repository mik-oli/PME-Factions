package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;

import com.github.mikoli.krolikcraft.utils.FilesUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FactionsUtils {

    public static boolean isPlayerInFaction(Krolikcraft plugin, UUID playerUUID) {
        for (Faction faction : plugin.getFactionsHashMap().values()) {
           if (faction.getMembers().contains(playerUUID)) return true;
        }
        return false;
    }

    public static Faction getPlayersFaction(Krolikcraft plugin, UUID playerUUID) {
        for (Faction faction : plugin.getFactionsHashMap().values()) {
            if (faction.isMember(playerUUID)) {
                return faction;
            }
        }
        return null;
    }

    public static Faction getFactionFromName(Krolikcraft plugin, String name) {
        for (UUID uuid : plugin.getFactionsHashMap().keySet()) {
            if (plugin.getFactionsHashMap().get(uuid).getName().equals(name)) {
                return plugin.getFactionsHashMap().get(uuid);
            }
        }
        return null;
    }

    public static void createFaction(Krolikcraft plugin, String name, String shortcut, UUID leader, Location coreLocation) {
        Faction faction = new Faction(plugin);
        faction.setName(name);
        faction.setShortcut(shortcut);
        faction.setColor(ChatColor.WHITE);
        faction.setLeader(leader);
        faction.addMember(leader);

        UUID factionId = UUID.randomUUID();
        while (plugin.getFactionsHashMap().containsKey(factionId)) {
            factionId = UUID.randomUUID();
        }
        faction.setId(factionId);
        faction.setCoreLocation(coreLocation);
        plugin.getFactionsHashMap().put(factionId, faction);
    }

    public static boolean hasPlayerPermission(Krolikcraft plugin, CommandSender commandSender, CommandsPermissions permissions, boolean adminMode) {
        if ((adminMode || permissions == CommandsPermissions.ADMIN) && commandSender.hasPermission("pmefactions.admin")) return true;

        UUID playerUUID = Bukkit.getPlayer(commandSender.getName()).getUniqueId();
        Faction faction = getPlayersFaction(plugin, playerUUID);
        if (faction == null) return false;

        if (permissions == CommandsPermissions.MEMBER && faction.isMember(playerUUID)) return true;
        else if (permissions == CommandsPermissions.OFFICER && faction.isOfficer(playerUUID)) return true;
        else if (permissions == CommandsPermissions.LEADER && faction.getLeader().equals(playerUUID)) return true;

        return false;
    }

    public static void removeFaction(Krolikcraft plugin, Faction faction) {

        ClaimsManager claimsManager = plugin.getClaimsManager();
        for (UUID id : claimsManager.getClaimsOwnerMap().keySet()) {
            if (claimsManager.getClaimsOwnerMap().get(id) != faction.getId()) continue;
            claimsManager.getClaimsList().remove(id);
            claimsManager.getClaimsOwnerMap().remove(id);
            claimsManager.getClaimsChunksMap().remove(id);
            claimsManager.getClaimsTypesMap().remove(id);
            claimsManager.getClaimCoreLocation().remove(id);
        }
        Block coreBlock = faction.getCoreLocation().getBlock();
        coreBlock.setType(Material.AIR);

        plugin.getFactionsHashMap().remove(faction.getId());
        if (plugin.getFactionsFilesHashMap().get(faction.getId()) != null) plugin.getFactionsFilesHashMap().get(faction.getId()).deleteFile();
        plugin.getFactionsFilesHashMap().remove(faction.getId());
    }
}
