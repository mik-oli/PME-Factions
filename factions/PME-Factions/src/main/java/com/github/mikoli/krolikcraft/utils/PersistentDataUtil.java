package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtil {

    private final Krolikcraft plugin;

    public PersistentDataUtil(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public Object getPlayerData(Player player, PersistentDataKeys dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey.toString());
        return player.getPersistentDataContainer().get(key, dataKey.getPersistentDataType());
    }

    public void setPlayerData(Player player, PersistentDataKeys dataKey, String value) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey.toString());
        player.getPersistentDataContainer().set(key, dataKey.getPersistentDataType(), value);
    }

    public boolean hasPlayerData(Player player, PersistentDataKeys dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey.toString());
        return player.getPersistentDataContainer().has(key, dataKey.getPersistentDataType());
    }
}
