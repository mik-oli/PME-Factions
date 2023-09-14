package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.commandsManager.StaffWLCommandsHandler;
import com.github.mikoli.krolikcraft.listeners.PlayerJoinListener;
import com.github.mikoli.krolikcraft.miningMonitor.MiningCounter;
import com.github.mikoli.krolikcraft.miningMonitor.MiningMonitor;
import com.github.mikoli.krolikcraft.staffWL.StaffWL;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Krolikcraft extends JavaPlugin {

    private final MiningCounter miningCounter = new MiningCounter();
    private final StaffWL staffWL = new StaffWL(this);
    private final StaffWLCommandsHandler staffWLCommandsHandler = new StaffWLCommandsHandler(this);
    private final MiningMonitor miningMonitor = new MiningMonitor(this);
    private final PlayerJoinListener playerJoinListener = new PlayerJoinListener(this);

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.setCommandsExecutors();
        this.setEventsListeners();

        staffWL.loadData();
    }

    @Override
    public void onDisable() {
        staffWL.savaData();

        this.saveConfig();
    }

    public FileConfiguration getConfig() {
        return this.getConfig();
    }

    public MiningCounter getMiningCounter() {
        return miningCounter;
    }

    public StaffWL getStaffWL() {
        return this.staffWL;
    }

    public StaffWLCommandsHandler getStaffWLCommandsHandler() {
        return this.staffWLCommandsHandler;
    }


    private void setCommandsExecutors() {
        this.getCommand("staffwl").setExecutor(staffWLCommandsHandler);
    }

    private final PluginManager pluginManager = this.getServer().getPluginManager();
    private void setEventsListeners() {
        pluginManager.registerEvents(miningMonitor, this);
        pluginManager.registerEvents(playerJoinListener, this);
    }
}
