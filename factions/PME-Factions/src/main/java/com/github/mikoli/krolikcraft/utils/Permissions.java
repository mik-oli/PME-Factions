package com.github.mikoli.krolikcraft.utils;

public enum Permissions {

    NULL("");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return "krolikcraft." + permission;
    }
}
