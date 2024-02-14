package com.github.mikoli.krolikcraft;

import com.github.mikoli.krolikcraft.claims.ClaimsDataHandler;
import com.github.mikoli.krolikcraft.claims.ClaimsManager;
import com.github.mikoli.krolikcraft.commands.CommandCompleter;
import com.github.mikoli.krolikcraft.commands.CommandsManager;
import com.github.mikoli.krolikcraft.dynmap.MarkerApiManager;
import com.github.mikoli.krolikcraft.dynmap.listeners.ClaimChangeListener;
import com.github.mikoli.krolikcraft.dynmap.listeners.FactionChangeListener;
import com.github.mikoli.krolikcraft.factions.FactionsDataHandler;
import com.github.mikoli.krolikcraft.factions.FactionsManager;
import com.github.mikoli.krolikcraft.listeners.BlockBreakListener;
import com.github.mikoli.krolikcraft.listeners.BlockPlaceListener;
import com.github.mikoli.krolikcraft.listeners.InteractListener;
import com.github.mikoli.krolikcraft.placeholderAPI.FactionsPlaceholders;
import com.github.mikoli.krolikcraft.utils.BukkitUtils;
import com.github.mikoli.krolikcraft.utils.ConfigUtils;
import com.github.mikoli.krolikcraft.utils.FilesUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.dynmap.DynmapAPI;

import java.io.IOException;
import java.util.Arrays;

public final class PMEFactions extends JavaPlugin {

    private final ConfigUtils configUtils = new ConfigUtils(this);
    private final FilesUtils claimsFilesUtil = new FilesUtils(this, "claims");
    private final ClaimsManager claimsManager = new ClaimsManager(this);
    private final FactionsManager factionsManager = new FactionsManager(this);
    private final CommandsManager commandsManager = new CommandsManager(this);
    private final CommandCompleter commandCompleter = new CommandCompleter(this);
    private MarkerApiManager markerApiManager = null;
    private DynmapAPI dynmapAPI = null;

    @Override
    public void onLoad() {
        dynmapAPI = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
    }

    @Override
    public void onEnable() {

        saveDefaultConfig();
        try {
            ClaimsDataHandler.loadClaimsData(claimsFilesUtil, claimsManager);
            FactionsDataHandler.loadFactionsData(this);
        } catch (IOException e) {
            BukkitUtils.consoleError(Arrays.toString(e.getStackTrace()));
        }

        if (dynmapAPI != null) markerApiManager = new MarkerApiManager(this, dynmapAPI);

        this.setEventsListeners();
        this.setCommandsExecutors();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new FactionsPlaceholders(this).register();
        }
    }

    @Override
    public void onDisable() {

        try {
            ClaimsDataHandler.saveClaimsData(claimsFilesUtil, claimsManager);
            FactionsDataHandler.saveFactionsData(this);
        } catch (IOException e) {
            BukkitUtils.consoleError(Arrays.toString(e.getStackTrace()));
        }
    }

    public ConfigUtils getConfigUtils() {
        return this.configUtils;
    }

    public ClaimsManager getClaimsManager() {
        return this.claimsManager;
    }

    public FactionsManager getFactionsManager() {
        return this.factionsManager;
    }

    public FilesUtils getClaimsFilesUtil() {
        return this.claimsFilesUtil;
    }

    public CommandsManager getCommandsManager() {
        return this.commandsManager;
    }

    public MarkerApiManager getMarkerApiManager() {
        return this.markerApiManager;
    }

    //Private methods
    private final PluginManager pluginManager = this.getServer().getPluginManager();
    private void setEventsListeners() {
        pluginManager.registerEvents(new BlockPlaceListener(this), this);
        pluginManager.registerEvents(new BlockBreakListener(this), this);
        pluginManager.registerEvents(new InteractListener(this), this);
//        pluginManager.registerEvents(otherListeners, this);
        if (markerApiManager != null) {
            pluginManager.registerEvents(new ClaimChangeListener(this), this);
            pluginManager.registerEvents(new FactionChangeListener(this), this);
        }
    }

    private void setCommandsExecutors() {
        this.getCommand("factions").setExecutor(commandsManager);
        this.getCommand("factions").setTabCompleter(commandCompleter);
        this.getCommand("factions-admin").setExecutor(commandsManager);
        this.getCommand("factions-admin").setTabCompleter(commandCompleter);
    }
}
