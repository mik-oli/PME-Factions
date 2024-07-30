package com.github.mikoli.ringsvalley.factionsLogic.events;

import com.github.mikoli.ringsvalley.factionsLogic.factions.Faction;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Faction faction;
    FactionChangeType factionChangeType;

    public FactionChangeEvent(Faction faction, FactionChangeType factionChangeType) {
        this.faction = faction;
        this.factionChangeType = factionChangeType;
    }

    public Faction getFaction() {
        return faction;
    }

    public FactionChangeType getChangeType() {
        return factionChangeType;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
