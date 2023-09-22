package com.github.mikoli.krolikcraft.utils;

import org.bukkit.persistence.PersistentDataType;

public enum PersistentDataKeys {

    FACTION(PersistentDataType.STRING),
    ISLEADER(PersistentDataType.BOOLEAN);

    private final PersistentDataType persistentDataType;

    PersistentDataKeys(PersistentDataType dataType) {
        this.persistentDataType = dataType;
    }

    public PersistentDataType getPersistentDataType() {
        return this.persistentDataType;
    }

}
