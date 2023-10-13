package com.github.mikoli.krolikcraft.claims;

import org.bukkit.ChatColor;

public enum ClaimType extends ChatColor {

    CLAIM_1x1(0),
    CLAIM_3x3(1),
    CLAIM_5x5(2),
    OUTPOST(1),
    NEUTRAL(1),
    CORE(2);

    private final int range;

    ClaimType(int key) {
        this.range = key;
    }

    public int getRange() {
        return this.range;
    }
}
