package eu.fthevenet.binjr.sources.adapters.elitebgs;

import javafx.scene.paint.Color;

public class EliteBgsAdapterParameters {
    private FactionBrowsingMode browsingMode;
    private String lookupValue;
    private Color selectedColor;

    public EliteBgsAdapterParameters() {
    }

    public FactionBrowsingMode getBrowsingMode() {
        return browsingMode;
    }

    public void setBrowsingMode(FactionBrowsingMode browsingMode) {
        this.browsingMode = browsingMode;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
}
