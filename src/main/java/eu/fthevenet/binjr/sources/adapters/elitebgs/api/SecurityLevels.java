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

public enum SecurityLevels implements QueryParameters {
    ALL("All", null),
    ANARCHY("Anarchy", "$GAlAXY_MAP_INFO_state_anarchy;"),
    LAWLESS("Lawless", "$GALAXY_MAP_INFO_state_lawless;"),
    HIGH("High", "$SYSTEM_SECURITY_high;"),
    LOW("Low", "$SYSTEM_SECURITY_low;"),
    MEDIUM("Medium", "$SYSTEM_SECURITY_medium;");

    private final String label;
    private final String parameterValue;

    SecurityLevels(String label, String parameterValue) {
        this.label = label;
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public String getParameterName() {
        return PARAM_SECURITY;
    }
}
