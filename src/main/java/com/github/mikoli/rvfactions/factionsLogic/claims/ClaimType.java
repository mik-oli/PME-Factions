package com.github.mikoli.rvfactions.factionsLogic.claims;

public enum ClaimType {

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

    public static ClaimType getClaimType(String arg) {
        try {
            return ClaimType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
