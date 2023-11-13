package com.github.mikoli.pmeutilities.ringsMechanic;

import com.github.mikoli.pmeutilities.PMEUtilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class RingsManager {

    private final PMEUtilities plugin;
    private final HashMap<Player, Rings> playersWithRing = new HashMap<>();

    public RingsManager(PMEUtilities plugin) {
        this.plugin = plugin;
    }

    public boolean isItemRing(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        if (!meta.hasCustomModelData()) return false;
        return meta.getCustomModelData() >= 100 && meta.getCustomModelData() <= 199;
    }

    public Rings getRingFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return Rings.getRingFromModelId(meta.getCustomModelData());
    }

    public boolean hasPlayerRing(Player player) {
        return playersWithRing.containsKey(player) ;
    }

    public void addPlayerRing(Player player, Rings ring) {
        playersWithRing.put(player, ring);
    }

    public void removePlayerRing(Player player) {
        playersWithRing.remove(player);
    }

    public void playerAddEffect(Player player, Rings ring) {
        player.addPotionEffect(ring.getEffectType().createEffect(Integer.MAX_VALUE, ring.getEffectStrength()));
    }

    public void playerRemoveEffect(Player player, Rings ring) {
        player.removePotionEffect(ring.getEffectType());
    }

    public void runTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : playersWithRing.keySet()) {
                    if (!player.isOnline()) continue;
                    ItemStack item = player.getInventory().getItemInOffHand();
                    if (!isItemRing(item)) {
                        removePlayerRing(player);
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            if (effect.getDuration() > 18000) {
                                player.removePotionEffect(effect.getType());
                            }
                        }
                    } else if (isItemRing(item)) {
                        playerAddEffect(player, playersWithRing.get(player));
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 100L);
    }
}
