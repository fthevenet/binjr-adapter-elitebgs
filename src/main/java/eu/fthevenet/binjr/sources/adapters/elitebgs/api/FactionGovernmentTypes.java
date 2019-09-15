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

public enum FactionGovernmentTypes implements QueryParameters {
    ALL("All", null),
    ANARCHY("Anarchy","Anarchy"),
    COMMUNISM("Communism","Communism"),
    CONFEDERACY("Confederacy","Confederacy"),
    COOPERATIVE("Cooperative","Cooperative"),
    CORPORATE("Corporate","Corporate"),
    DEMOCRACY("Democracy","Democracy"),
    DICTATORSHIP("Dictatorship","Dictatorship"),
    FEUDAL("Feudal","Feudal"),
    IMPERIAL("Imperial","Imperial"),
    NONE("None","None"),
    PATRONAGE("Patronage","Patronage"),
    PRISON_COLONY("Prison Colony","PrisonColony"),
    THEOCRACY("Theocracy","Theocracy"),
    ENGINEER("Engineer","Engineer");

    private final String label;
    private final String parameterValue;


    FactionGovernmentTypes(String label, String parameterValue){
        this.label = label;
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return this.label;
    }



    @Override
    public String getName() {
        return PARAM_GOVERNMENT;
    }

    @Override
    public String getValue() {
        return this.parameterValue;
    }
}
