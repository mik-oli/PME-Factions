package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtil {

    private final PersistentDataType<String, String> dataType = PersistentDataType.STRING;
    private final Krolikcraft plugin;

    public PersistentDataUtil(Krolikcraft plugin) {
        this.plugin = plugin;
    }

    public String getPlayerData(Player player, PersistentDataKeys dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey.toString());
        return player.getPersistentDataContainer().get(key, dataType);
    }

    public void setPlayerData(Player player, PersistentDataKeys dataKey, String value) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey.toString());
        player.getPersistentDataContainer().set(key, dataType, value);
    }

    public boolean hasPlayerData(Player player, PersistentDataKeys dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey.toString());
        return player.getPersistentDataContainer().has(key, dataType);
    }
}
