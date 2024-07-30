package com.github.mikoli.rvfactions.factionsLogic.utils;

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
