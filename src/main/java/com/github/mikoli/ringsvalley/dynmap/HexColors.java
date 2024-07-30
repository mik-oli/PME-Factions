package com.github.mikoli.ringsvalley.dynmap;

public enum HexColors {

    BLACK(0x000000),
    DARK_BLUE(0x02008a),
    DARK_GREEN(0x007000),
    DARK_AQUA(0x006a70),
    DARK_RED(0x700000),
    DARK_PURPLE(0x440078),
    GOLD(0x8f6703),
    GRAY(0x787673),
    DARK_GRAY(0x545351),
    BLUE(0x3d65d4),
    GREEN(0x2bcc3e),
    AQUA(0x3cd5e6),
    RED(0xe34d4d),
    LIGHT_PURPLE(0xd117b5),
    YELLOW(0xd5db2c),
    WHITE(0xffffff);

    private final int hexColor;

    HexColors(int hexColor) {
        this.hexColor = hexColor;
    }

    public int getHexColor() {
        return this.hexColor;
    }
}
