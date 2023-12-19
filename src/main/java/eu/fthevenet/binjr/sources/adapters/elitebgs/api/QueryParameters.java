/*
 * Copyright 2019 Frederic Thevenet
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

package eu.fthevenet.binjr.sources.adapters.elitebgs.api;



import eu.binjr.core.data.adapters.NameValuePair;

import java.time.Instant;
import java.util.*;

public interface QueryParameters extends NameValuePair {
    String PARAM_ID = "id";
    String PARAM_GOVERNMENT = "government";
    String PARAM_SECURITY = "security";
    String PARAM_ALLEGIANCE = "allegiance";
    String PARAM_ECONOMY = "primaryEconomy";
    String PARAM_STATE = "state";
    String PARAM_TIMEMIN = "timeMin";
    String PARAM_TIMEMAX = "timeMax";
    String PARAM_PAGE = "page";
    String PARAM_BEGINS_WITH = "beginsWith";
    String PARAM_NAME = "name";
    String PARAM_MINIMAL = "minimal";


    Set<String> knownParameterNames = new HashSet<>(Arrays.asList(
            PARAM_ID,
            PARAM_GOVERNMENT,
            PARAM_SECURITY,
            PARAM_ALLEGIANCE,
            PARAM_ECONOMY,
            PARAM_STATE,
            PARAM_TIMEMIN,
            PARAM_TIMEMAX,
            PARAM_BEGINS_WITH,
            PARAM_NAME,
            PARAM_MINIMAL
    ));

    static boolean isParameterNameKnown(String name) {
        return knownParameterNames.contains(name);
    }

    /**
     * A utility function which prunes instances of {@link QueryParameters} with null or empty values and returns
     * the others as an array of {@link NameValuePair} instances.
     *
     * @param params the {@link QueryParameters} instance to convert as {@link NameValuePair}
     * @return An array of {@link NameValuePair} instances.
     */
    static List<NameValuePair> pruneParameters(Iterable<NameValuePair> params) {
        List<NameValuePair> paramList = new ArrayList<>();
        for (var p : params) {
            if (p.getValue() != null &&
                    !p.getValue().isBlank() &&
                    !p.getValue().equalsIgnoreCase("ALL")) {
                paramList.add(p);
            }
        }
        return paramList;
    }

    static List<NameValuePair> pruneParameters(NameValuePair... params) {
        return QueryParameters.pruneParameters(Arrays.asList(params));
    }

    static QueryParameters of(String name, String value) {
        if (!isParameterNameKnown(name)) {
            throw new IllegalArgumentException("Unknown parameter name: " + name);
        }
        return new BasicParameter(name, value);
    }

    static Optional<QueryParameters> attempt(String name, String value) {
        if (!isParameterNameKnown(name)) {
            return Optional.empty();
        }
        return Optional.of(new BasicParameter(name, value));
    }

    static QueryParameters name(String value) {
        return new BasicParameter(PARAM_NAME, value);
    }

    static QueryParameters id(String value) {
        return new BasicParameter(PARAM_ID, value);
    }

    static QueryParameters beginsWith(String beginsWith) {
        return new BasicParameter(PARAM_BEGINS_WITH, beginsWith);
    }

    static QueryParameters page(int value) {
        return new BasicParameter(PARAM_PAGE, Integer.toString(value));
    }

    static QueryParameters timeMin(Instant value) {
        return new BasicParameter(PARAM_TIMEMIN, Long.toString(value.toEpochMilli()));
    }

    static QueryParameters timeMax(Instant value) {
        return new BasicParameter(PARAM_TIMEMAX, Long.toString(value.toEpochMilli()));
    }

    static QueryParameters minimal(boolean value) {
        return new BasicParameter(PARAM_MINIMAL, Boolean.toString(value));
    }

    String getName();

    String getValue();

    class BasicParameter implements QueryParameters {

        private final String name;
        private final String value;

        BasicParameter(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
