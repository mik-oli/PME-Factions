package com.github.mikoli.pmeutilities;

import com.github.mikoli.pmeutilities.listeners.InvClickListener;
import com.github.mikoli.pmeutilities.listeners.InvDragListener;
import com.github.mikoli.pmeutilities.listeners.PlayerJoinListener;
import com.github.mikoli.pmeutilities.listeners.SwapHandItemsListener;
import com.github.mikoli.pmeutilities.ringsMechanic.RingsManager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PMEUtilities extends JavaPlugin {

    private final RingsManager ringsManager = new RingsManager(this);

    //Events classes
    private final SwapHandItemsListener swapHandItemsListener = new SwapHandItemsListener(this);
    private final InvDragListener invDragListener = new InvDragListener(this);
    private final PlayerJoinListener playerJoinListener = new PlayerJoinListener(this);
    private final InvClickListener invClickListener = new InvClickListener(this);

    @Override
    public void onEnable() {
        this.setEventsListeners();
        ringsManager.runTask();
    }

    @Override
    public void onDisable() {

    }

    public RingsManager getRingsManager() {
        return this.ringsManager;
    }

    private final PluginManager pluginManager = this.getServer().getPluginManager();
    private void setEventsListeners() {
        pluginManager.registerEvents(swapHandItemsListener, this);
        pluginManager.registerEvents(invDragListener, this);
        pluginManager.registerEvents(playerJoinListener, this);
        pluginManager.registerEvents(invClickListener, this);
    }
}
