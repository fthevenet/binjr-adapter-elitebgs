/*
 * Copyright 2019-2021 Frederic Thevenet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.fthevenet.binjr.sources.adapters.elitebgs;


import eu.binjr.common.preferences.ObservablePreference;
import eu.binjr.core.data.adapters.DataAdapter;
import eu.binjr.core.data.adapters.DataAdapterPreferences;
import javafx.scene.paint.Color;

public class EliteBgsAdapterPreferences extends DataAdapterPreferences {

    public final ObservablePreference<Number> fetchReadAheadDays = integerPreference("fetchReadAheadDays", 10);

    public final ObservablePreference<Number> fetchReadBehindDays = integerPreference("fetchReadBehindDays", 10);

    public ObservablePreference<Number> defaultTimeRangeHours = integerPreference("defaultTimeRangeHours", 24 * 30);

    public ObservablePreference<Color> lastFactionColor =
            objectPreference(Color.class, "lastFactionColor", Color.ORANGE, Color::toString, Color::valueOf);

    public ObservablePreference<FactionBrowsingMode> lastSelectedBrowsingMode =
            enumPreference(FactionBrowsingMode.class, "lastSelectedBrowsingMode", FactionBrowsingMode.BROWSE_BY_FACTIONS);

//    private EliteBgsAdapterPreferences() {
//        super(EliteBgsAdapter.class);
//    }
    public EliteBgsAdapterPreferences(Class<? extends DataAdapter<?>> dataAdapterClass) {
        super(dataAdapterClass);
    }

//
//    public static EliteBgsAdapterPreferences getInstance() {
//        return EliteBgsAdapterPreferencesHolder.instance;
//    }
//
//    private static class EliteBgsAdapterPreferencesHolder {
//        private final static EliteBgsAdapterPreferences instance = new EliteBgsAdapterPreferences();
//    }
}
