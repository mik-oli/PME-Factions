package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.PMEFactions;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtils {

    public static Boolean hasData(PMEFactions plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.has(key, PersistentDataType.STRING);
    }

    public static String getData(PMEFactions plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.get(key, PersistentDataType.STRING);
    }

    public static void setData(PMEFactions plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer, String value) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.set(key, PersistentDataType.STRING, value);
    }

    public static void removeData(PMEFactions plugin, PersistentDataKeys inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.remove(key);
    }
}
