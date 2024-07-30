package com.github.mikoli.ringsvalley.factionsLogic.events;

import com.github.mikoli.ringsvalley.factionsLogic.claims.Claim;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClaimChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Claim claim;
    ClaimChangeType claimChangeType;

    public ClaimChangeEvent(Claim claim, ClaimChangeType claimChangeType) {
        this.claim = claim;
        this.claimChangeType = claimChangeType;
    }

    public Claim getClaim() {
        return claim;
    }

    public ClaimChangeType getChangeType() {
        return claimChangeType;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
