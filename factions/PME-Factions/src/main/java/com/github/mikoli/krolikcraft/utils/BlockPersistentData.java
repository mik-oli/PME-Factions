package com.github.mikoli.krolikcraft.utils;

import com.github.mikoli.krolikcraft.Krolikcraft;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockPersistentData {

    public static Boolean hasBlockData(Krolikcraft plugin, PersistentDataKeys inputKey, Block targetBlock) {
        TileState state = (TileState) targetBlock.getState();
        PersistentDataContainer dataContainer = state.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());

        return dataContainer.has(key, PersistentDataType.STRING);
    }

    public static String getBlockData(Krolikcraft plugin, PersistentDataKeys inputKey, Block targetBlock) {
        TileState state = (TileState) targetBlock.getState();
        PersistentDataContainer dataContainer = state.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());

        return dataContainer.get(key, PersistentDataType.STRING);
    }

    public static void setBlockData(Krolikcraft plugin, PersistentDataKeys inputKey, Block targetBlock, String value) {
        TileState state = (TileState) targetBlock.getState();
        PersistentDataContainer dataContainer = state.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());

        dataContainer.set(key, PersistentDataType.STRING, value);
        state.update();
    }

    public static void removeBlockData(Krolikcraft plugin, PersistentDataKeys inputKey, Block targetBlock) {
        TileState state = (TileState) targetBlock.getState();
        PersistentDataContainer dataContainer = state.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, inputKey.getKey());

        dataContainer.remove(key);
        state.update();
    }
}
