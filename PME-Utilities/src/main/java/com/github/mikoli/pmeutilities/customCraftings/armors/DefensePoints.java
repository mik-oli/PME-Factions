package com.github.mikoli.pmeutilities.customCraftings.armors;

public enum DefensePoints {

    LEATHER(1, 3, 2, 1),
    IRON(2, 6, 5, 2),
    CHAIN(2, 5, 4, 1),
    GOLD(2, 5, 3, 1),
    DIAMOND(3, 8, 6, 3);

    private final int helmet, chestplate, leggings, boots;

    DefensePoints(int helmet, int chestplate, int leggings, int boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public int getHelmet() {
        return helmet;
    }

    public int getChestplate() {
        return chestplate;
    }

    public int getLeggings() {
        return leggings;
    }

    public int getBoots() {
        return boots;
    }
}
