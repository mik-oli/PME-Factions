package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.commandsManager.CommandsHandler;
import com.github.mikoli.krolikcraft.listeners.PlayerJoinListener;
import com.github.mikoli.krolikcraft.miningMonitor.MiningCounter;
import com.github.mikoli.krolikcraft.miningMonitor.MiningMonitor;
import com.github.mikoli.krolikcraft.staffWL.StaffWL;
import com.github.mikoli.krolikcraft.utils.ConfigUtil;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Krolikcraft extends JavaPlugin {

    private final MiningCounter miningCounter = new MiningCounter();
    private final ConfigUtil configUtil = new ConfigUtil(this);
    private final StaffWL staffWL = new StaffWL(this);
    private final CommandsHandler commandsHandler = new CommandsHandler(this);
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


    public MiningCounter getMiningCounter() {
        return miningCounter;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }

    public StaffWL getStaffWL() {
        return this.staffWL;
    }

    public CommandsHandler getCommandsHandler() {
        return this.commandsHandler;
    }


    private void setCommandsExecutors() {
        this.getCommand("staffwl").setExecutor(commandsHandler);
    }

    private final PluginManager pluginManager = this.getServer().getPluginManager();
    private void setEventsListeners() {
        pluginManager.registerEvents(miningMonitor, this);
        pluginManager.registerEvents(playerJoinListener, this);
    }
}
