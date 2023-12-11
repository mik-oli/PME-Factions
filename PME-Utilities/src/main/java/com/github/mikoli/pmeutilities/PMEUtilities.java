package com.github.mikoli.pmeutilities;

import com.github.mikoli.pmeutilities.customCraftings.CustomRecipes;
import com.github.mikoli.pmeutilities.listeners.*;
import com.github.mikoli.pmeutilities.ringsMechanic.RingsManager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PMEUtilities extends JavaPlugin {

    private final RingsManager ringsManager = new RingsManager(this);
    private final CustomRecipes customRecipes = new CustomRecipes(this);

    //Events classes
    private final SwapHandItemsListener swapHandItemsListener = new SwapHandItemsListener(this);
    private final InvDragListener invDragListener = new InvDragListener(this);
    private final PlayerJoinListener playerJoinListener = new PlayerJoinListener(this);
    private final InvClickListener invClickListener = new InvClickListener(this);
    private final ItemPickUpListener itemPickUpListener = new ItemPickUpListener(this);

    @Override
    public void onEnable() {
        this.setEventsListeners();
        ringsManager.runTask();
        customRecipes.registerArmorsCrafting();
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
        pluginManager.registerEvents(itemPickUpListener, this);
    }
}
