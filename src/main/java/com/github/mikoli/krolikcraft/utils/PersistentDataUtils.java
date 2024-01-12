package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.PMEFactions;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public enum PersistentDataUtils {

    CLAIMFLAG("claim-flag"),
    CLAIMTYPE("claim-type"),
    CLAIMOWNER("claim-owner"),
    COREFLAG("core-flag");

    private final String key;

    PersistentDataUtils(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public static Boolean hasData(PMEFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.has(key, PersistentDataType.STRING);
    }

    public static String getData(PMEFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.get(key, PersistentDataType.STRING);
    }

    public static void setData(PMEFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer, String value) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.set(key, PersistentDataType.STRING, value);
    }

    public static void removeData(PMEFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.remove(key);
    }
}
