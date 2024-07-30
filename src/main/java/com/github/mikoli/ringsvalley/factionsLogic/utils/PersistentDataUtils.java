package com.github.mikoli.ringsvalley.factionsLogic.utils;

import com.github.mikoli.ringsvalley.RVFactions;

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

    public static Boolean hasData(RVFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.has(key, PersistentDataType.STRING);
    }

    public static String getData(RVFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        return dataContainer.get(key, PersistentDataType.STRING);
    }

    public static void setData(RVFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer, String value) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.set(key, PersistentDataType.STRING, value);
    }

    public static void removeData(RVFactions plugin, PersistentDataUtils inputKey, PersistentDataContainer dataContainer) {
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());
        dataContainer.remove(key);
    }
}
