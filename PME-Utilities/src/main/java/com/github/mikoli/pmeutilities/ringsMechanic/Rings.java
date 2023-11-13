package com.github.mikoli.pmeutilities.ringsMechanic;

import org.bukkit.potion.PotionEffectType;

public enum Rings {

    MINJE(101, PotionEffectType.SPEED, 1),
    ATJA(102, PotionEffectType.INCREASE_DAMAGE, 1),
    NELDE(103, PotionEffectType.JUMP, 2),
    KANTA(104, PotionEffectType.REGENERATION, 1),
    LOSTA(105, PotionEffectType.DAMAGE_RESISTANCE, 1),
    ENKWE(106, PotionEffectType.INVISIBILITY, 1),
    OTSO(107, PotionEffectType.NIGHT_VISION, 1),
    TOLTO(108, PotionEffectType.ABSORPTION, 5),
    NERTE(109, PotionEffectType.FIRE_RESISTANCE, 1);
//    ASH_NAZG()

    private final int modelDataId;
    private final PotionEffectType effectType;
    private final int effectStrength;

    Rings(int modelDataId, PotionEffectType effect, int strength) {
        this.modelDataId = modelDataId;
        this.effectType = effect;
        this.effectStrength = strength;
    }

    public int getModelDataId() {
        return modelDataId;
    }

    public PotionEffectType getEffectType() {
        return this.effectType;
    }

    public int getEffectStrength() {
        return effectStrength;
    }

    public static Rings getRingFromModelId(int id) {
        for (Rings ring : Rings.values()) {
            if (ring.getModelDataId() == id) return ring;
        }
        return null;
    }
}
