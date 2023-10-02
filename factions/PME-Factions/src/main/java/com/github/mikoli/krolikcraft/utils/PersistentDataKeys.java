package com.github.mikoli.krolikcraft.utils;

public enum PersistentDataKeys {

    CLAIMFLAG("claim-flag"),
    OWNER("claim-owner");

    private final String key;

    PersistentDataKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
