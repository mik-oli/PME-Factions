package com.github.mikoli.rvfactions.placeholderAPI;

import com.github.mikoli.rvfactions.RVFactions;
import com.github.mikoli.rvfactions.factionsLogic.factions.Faction;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FactionsPlaceholders extends PlaceholderExpansion {

    private final RVFactions plugin;

    public FactionsPlaceholders(RVFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pmefactions";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        switch (params) {
            case "player_faction_name": return this.getPlayerFactionName(player);
            case "player_faction_shortcut": return this.getPlayerFactionShortcut(player);
            case "player_faction_color": return this.getPlayerFactionColor(player);
            default: return null;
        }
    }

    private String getPlayerFactionName(Player player) {
        Faction faction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
        if (faction == null) return "";
        else return faction.getName();
    }

    private String getPlayerFactionShortcut(Player player) {
        Faction faction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
        if (faction == null) return "";
        else return faction.getShortcut();
    }

    private String getPlayerFactionColor(Player player) {
        Faction faction = plugin.getFactionsManager().getPlayersFaction(player.getUniqueId());
        if (faction == null) return "";
        else return faction.getColor().toString();
    }
}
