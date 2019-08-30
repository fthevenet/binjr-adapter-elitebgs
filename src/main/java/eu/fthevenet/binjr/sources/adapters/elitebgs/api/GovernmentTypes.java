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

public enum GovernmentTypes implements QueryParameters {
    ALL("All", null),
    ANARCHY("Anarchy","$government_Anarchy;"),
    COMMUNISM("Communism","$government_Communism;"),
    CONFEDERACY("Confederacy","$government_Confederacy;"),
    COOPERATIVE("Cooperative","$government_Cooperative;"),
    CORPORATE("Corporate","$government_Corporate;"),
    DEMOCRACY("Democracy","$government_Democracy;"),
    DICTATORSHIP("Dictatorship","$government_Dictatorship;"),
    FEUDAL("Feudal","$government_Feudal;"),
    IMPERIAL("Imperial","$government_Imperial;"),
    NONE("None","$government_None;"),
    PATRONAGE("Patronage","$government_Patronage;"),
    PRISON_COLONY("Prison Colony","$government_PrisonColony;"),
    THEOCRACY("Theocracy","$government_Theocracy;"),
    ENGINEER("Engineer","$government_Engineer;");

    private final String label;
    private final String parameterValue;


    GovernmentTypes(String label, String parameterValue){
        this.label = label;
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.label;
    }



    @Override
    public String getParameterName() {
        return PARAM_GOVERNMENT;
    }

    @Override
    public String getParameterValue() {
        return this.parameterValue;
    }
}
