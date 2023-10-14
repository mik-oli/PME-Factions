package com.github.mikoli.krolikcraft.factions;

import com.github.mikoli.krolikcraft.Krolikcraft;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.utils.CommandsPermissions;

import com.github.mikoli.krolikcraft.utils.FilesUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    public static boolean hasPlayerPermission(Krolikcraft plugin, Player player, CommandsPermissions permissions, boolean adminMode) {
        if ((adminMode || permissions == CommandsPermissions.ADMIN) && player.hasPermission("pmefactions.admin")) return true;

        Faction faction = getPlayersFaction(plugin, player.getUniqueId());
        if (faction == null) return false;
        if (permissions == CommandsPermissions.MEMBER && faction.isMember(player.getUniqueId())) return true;
//        else if (permissions == CommandsPermissions.OFFICER && faction.getLeader() == player.getUniqueId()) return true;
        else if (permissions == CommandsPermissions.LEADER && faction.getLeader() == player.getUniqueId()) return true;
        else return false;
    }

    public static void removeFaction(Krolikcraft plugin, Faction faction) {
        plugin.getFactionsHashMap().remove(faction.getId());
        plugin.getFactionsFilesHashMap().remove(faction.getId());

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
        plugin.getFactionsFilesHashMap().get(faction.getId()).deleteFile();
        plugin.getFactionsFilesHashMap().remove(faction.getId());
    }
}
