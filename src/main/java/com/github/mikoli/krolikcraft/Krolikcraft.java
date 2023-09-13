package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.miningMonitor.MiningCounter;
import com.github.mikoli.krolikcraft.miningMonitor.MiningMonitor;
import com.github.mikoli.krolikcraft.utils.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class Krolikcraft extends JavaPlugin {

    private final MiningCounter miningCounter = new MiningCounter();
    private final ConfigUtil configUtil = new ConfigUtil(this);

    @Override
    public void onEnable() {
        MiningMonitor miningMonitor = new MiningMonitor(this);
        this.saveDefaultConfig();
    }

    public MiningCounter getMiningCounter() {
        return miningCounter;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }
}
