package eu.fthevenet.binjr.sources.adapters.elitebgs;

import eu.binjr.common.preferences.Preference;
import eu.binjr.core.data.adapters.DataAdapterPreferences;

import java.util.TreeMap;

public class EliteBgsAdapterPreferences extends DataAdapterPreferences {

    public final Preference<Number> fetchReadAheadDays = integerPreference("fetchReadAheadDays", 10);

    public final Preference<Number> fetchReadBehindDays = integerPreference("fetchReadBehindDays", 10);

    public Preference<Number> defaultTimeRangeHours = integerPreference("defaultTimeRangeHours", 24*30);

    public EliteBgsAdapterPreferences() {
        super(EliteBgsAdapter.class);
    }
}
