package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.miningMonitor.MiningCounter;
import com.github.mikoli.krolikcraft.miningMonitor.MiningMonitor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Krolikcraft extends JavaPlugin {

    private final MiningCounter miningCounter = new MiningCounter();

    @Override
    public void onEnable() {
        MiningMonitor miningMonitor = new MiningMonitor(this);
    }

    public MiningCounter getMiningCounter() {
        return miningCounter;
    }
}
