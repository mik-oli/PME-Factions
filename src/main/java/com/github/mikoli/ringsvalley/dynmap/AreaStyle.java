package com.github.mikoli.ringsvalley.dynmap;

public class AreaStyle {

    private final double fillOpacity;
    private final int fillColor;
    private final double lineOpacity;
    private final int lineColor;
    private final String label;
    private final String description;

    public AreaStyle(double fillOpacity, int fillColor, double lineOpacity, int lineColor, String label, String description) {
        this.fillOpacity = fillOpacity;
        this.fillColor = fillColor;
        this.lineOpacity = lineOpacity;
        this.lineColor = lineColor;
        this.label = label;
        this.description = description;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public int getFillColor() {
        return fillColor;
    }

    public double getLineOpacity() {
        return lineOpacity;
    }

    public int getLineColor() {
        return lineColor;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
