package com.github.mikoli.krolikcraft.miningMonitor;

import com.github.mikoli.krolikcraft.PMEAdmin;
import com.github.mikoli.krolikcraft.utils.Permissions;
import com.github.mikoli.krolikcraft.utils.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningMonitor implements Listener {

    private final PMEAdmin plugin;

    public MiningMonitor(PMEAdmin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player targetPlayer = event.getPlayer();
        if (!targetPlayer.hasPermission(Permissions.MININGMONITORTRACK.getPermission())) return;
        Block targetBlock = event.getBlock();
        if (plugin.getMiningCounter().wasCounted(targetBlock.getLocation())) return;

        Material ore = targetBlock.getType();
        //TODO ores from config
        if (ore.equals(Material.DIAMOND_ORE) || ore.equals(Material.EMERALD_ORE) || ore.equals(Material.GOLD_ORE)) {
            int blocksTotal = plugin.getMiningCounter().getTotalBlocks(targetBlock);
            String message = ("&c" + targetPlayer + " &efound &b" + blocksTotal + " " + ore);

            Utils.consoleInfo(Utils.coloring(message));
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (player.hasPermission(Permissions.MININGMONITORMONITOR.getPermission())) {
                    //TODO message from config + coloring
                    player.sendMessage(Utils.coloring(message));
                }
            }
        }
    }
}
