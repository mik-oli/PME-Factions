package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtils {

    public static Boolean hasData(Krolikcraft plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.has(key, PersistentDataType.STRING);
    }

    public static String getData(Krolikcraft plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.get(key, PersistentDataType.STRING);
    }

    public static void setData(Krolikcraft plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer, String value) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.set(key, PersistentDataType.STRING, value);
    }

    public static void removeData(Krolikcraft plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.remove(key);
    }

    public static PersistentDataContainer getItemContainer(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer();
    }
}
