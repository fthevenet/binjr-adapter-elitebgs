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

public enum StateTypes implements QueryParameters {
    ALL("All", null),
    BOOM("Boom", "Boom"),
    BUST("Bust", "Bust"),
    CIVIL_UNREST("Civil Unrest", "CivilUnrest"),
    CIVIL_WAR("Civil War", "CivilWar"),
    ELECTION("Election", "Election"),
    EXPANSION("Expansion", "Expansion"),
    FAMINE("Famine", "Famine"),
    INVESTMENT("Investment", "Investment"),
    LOCKDOWN("Lockdown", "Lockdown"),
    NONE("None", "None"),
    OUTBREAK("Outbreak", "Outbreak"),
    RETREAT("Retreat", "Retreat"),
    WAR("War", "War");

    private final String label;
    private final String parameterValue;

    StateTypes(String label, String parameterValue) {
        this.label = label;
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.label;
    }

    @Override
    public String getName() {
        return PARAM_STATE;
    }

    @Override
    public String getValue() {
        return parameterValue;
    }
}
