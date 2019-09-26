package eu.fthevenet.binjr.sources.adapters.elitebgs;

import eu.binjr.common.preferences.Preference;
import eu.binjr.core.data.adapters.DataAdapterPreferences;

public class EliteBgsAdapterPreferences extends DataAdapterPreferences {

    public final Preference<Number> fetchReadAheadDays = integerPreference("fetchReadAheadDays", 10);

    public final Preference<Number> fetchReadBehindDays = integerPreference("fetchReadBehindDays", 10);

    public EliteBgsAdapterPreferences() {
        super(EliteBgsAdapter.class);
    }
}
