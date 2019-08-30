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

public enum EconomyTypes implements QueryParameters {
    ALL("All", null),
    AGRICULTURE("Agriculture", "$economy_Agri;"),
    COLONY("Colony", "$economy_Colony;"),
    EXTRACTION("Extraction", "$economy_Extraction;"),
    HIGH_TECH("High Tech", "$economy_HighTech;"),
    INDUSTRIAL("Industrial", "$economy_Industrial;"),
    MILITARY("Military", "$economy_Military;"),
    NONe("None", "$economy_None;"),
    REFINERY("Refinery", "$economy_Refinery;"),
    SERVICE("Service", "$economy_Service;"),
    TERRAFORMING("Terraforming", "$economy_Terraforming;"),
    TOURISM("Tourism", "$economy_Tourism;"),
    PRISON("Prison", "$economy_Prison;"),
    DAMAGED("Damaged", "$economy_Damaged;"),
    RESCUE("Rescue", "$economy_Rescue;"),
    REPAIR("Repair", "$economy_Repair;");

    private final String label;
    private final String parameterValue;

    EconomyTypes(String label, String parameterValue) {
        this.label = label;

        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public String getParameterName() {
        return PARAM_ECONOMY;
    }

    public String getParameterValue() {
        return parameterValue;
    }
}
