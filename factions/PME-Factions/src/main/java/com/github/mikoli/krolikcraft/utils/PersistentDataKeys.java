package com.github.mikoli.krolikcraft.utils;

public enum PersistentDataKeys {

    CLAIMFLAG("claim-flag"),
    CLAIMBLOCK("claim-block"),
    CLAIMTYPE("claim-type"),
    CLAIMOWNER("claim-owner");

    private final String key;

    PersistentDataKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
