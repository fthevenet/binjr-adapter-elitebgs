package eu.fthevenet.binjr.sources.adapters.elitebgs;

import eu.binjr.common.preferences.Preference;
import eu.binjr.core.data.adapters.DataAdapterPreferences;
import javafx.scene.paint.Color;

public class EliteBgsAdapterPreferences extends DataAdapterPreferences {

    public final Preference<Number> fetchReadAheadDays = integerPreference("fetchReadAheadDays", 10);

    public final Preference<Number> fetchReadBehindDays = integerPreference("fetchReadBehindDays", 10);

    public Preference<Number> defaultTimeRangeHours = integerPreference("defaultTimeRangeHours", 24*30);

    public Preference<Color> lastFactionColor =
            objectPreference(Color.class, "lastFactionColor", Color.ORANGE, Color::toString, Color::valueOf);

    public Preference<FactionBrowsingMode> lastSelectedBrowsingMode =
            enumPreference(FactionBrowsingMode.class,"lastSelectedBrowsingMode", FactionBrowsingMode.BROWSE_BY_FACTIONS);

    private EliteBgsAdapterPreferences() {
        super(EliteBgsAdapter.class);
    }


    public static EliteBgsAdapterPreferences getInstance() {
        return EliteBgsAdapterPreferencesHolder.instance;
    }

    private static class EliteBgsAdapterPreferencesHolder {
        private final static EliteBgsAdapterPreferences instance = new EliteBgsAdapterPreferences();
    }
}
