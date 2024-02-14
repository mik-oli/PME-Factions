package com.github.mikoli.krolikcraft.factionsLogic.utils;

public enum Permissions {

    ADMIN("admin");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return "pmefactions." + this.permission;
    }
}
