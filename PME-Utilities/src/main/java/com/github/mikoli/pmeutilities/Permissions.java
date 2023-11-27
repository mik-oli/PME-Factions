package com.github.mikoli.pmeutilities;

public enum Permissions {

    BYPASS_ITEM_LIMIT("pmeutils.itemlimit.bypass");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
