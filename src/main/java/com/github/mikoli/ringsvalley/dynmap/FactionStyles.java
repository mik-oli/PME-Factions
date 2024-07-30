package com.github.mikoli.ringsvalley.dynmap;

import com.github.mikoli.ringsvalley.factionsLogic.claims.ClaimType;

public class FactionStyles {

    private AreaStyle claimStyle;
    private AreaStyle outpostStyle;
    private AreaStyle coreStyle;

    public FactionStyles(AreaStyle claimStyle, AreaStyle outpostStyle, AreaStyle coreStyle) {
        this.claimStyle = claimStyle;
        this.outpostStyle = outpostStyle;
        this.coreStyle = coreStyle;
    }

    public void setClaimStyle(AreaStyle claimStyle) {
        this.claimStyle = claimStyle;
    }

    public AreaStyle getClaimStyle() {
        return claimStyle;
    }

    public void setOutpostStyle(AreaStyle outpostStyle) {
        this.outpostStyle = outpostStyle;
    }

    public AreaStyle getOutpostStyle() {
        return outpostStyle;
    }

    public void setCoreStyle(AreaStyle coreStyle) {
        this.coreStyle = coreStyle;
    }

    public AreaStyle getCoreStyle() {
        return coreStyle;
    }

    public AreaStyle getFactionStyle(ClaimType claimType) {
        switch (claimType) {
            case OUTPOST: return this.outpostStyle;
            case CORE: return this.coreStyle;
            default: return this.claimStyle;
        }
    }
}
