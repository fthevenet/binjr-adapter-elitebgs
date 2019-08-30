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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface EBGSQueryParameters {
    String PARAM_ID = "id";
    String PARAM_GOVERNMENT = "government";
    String PARAM_SECURITY = "security";
    String PARAM_ALLEGIANCE = "allegiance";
    String PARAM_ECONOMY = "economy";
    String PARAM_TIMEMIN = "timemin";
    String PARAM_TIMEMAX = "timemax";
    String PARAM_PAGE = "page";
    String PARAM_BEGINS_WITH = "beginsWith";


    /**
     * A utility function which prunes instances of {@link EBGSQueryParameters} with null or empty values and returns
     * the others as an array of {@link NameValuePair} instances.
     *
     * @param params the {@link EBGSQueryParameters} instance to convert as {@link NameValuePair}
     * @return An array of {@link NameValuePair} instances.
     */
    static List<NameValuePair> toValuePairArray(Iterable<EBGSQueryParameters> params) {
        List<NameValuePair> paramList = new ArrayList<>();
        for (var p : params) {
            if (p.getParameterValue() != null &&
                    !p.getParameterValue().isBlank() &&
                    !p.getParameterValue().equalsIgnoreCase("ALL")) {
                paramList.add(p.toValuePair());
            }
        }
        return paramList;
    }

    static List<NameValuePair> toValuePairArray(EBGSQueryParameters... params) {
        return EBGSQueryParameters.toValuePairArray(Arrays.asList(params));
    }

    static EBGSQueryParameters id(String value) {
        return new BasicParameter(PARAM_ID, value);
    }

    static EBGSQueryParameters beginsWith(String beginsWith) {
        return new BasicParameter(PARAM_BEGINS_WITH, beginsWith);
    }

    static EBGSQueryParameters page(int value) {
        return new BasicParameter(PARAM_PAGE, Integer.toString(value));
    }

    static EBGSQueryParameters timeMin(Instant value) {
        return new BasicParameter(PARAM_TIMEMIN, Long.toString(value.toEpochMilli()));
    }

    static EBGSQueryParameters timeMax(Instant value) {
        return new BasicParameter(PARAM_TIMEMAX, Long.toString(value.toEpochMilli()));
    }

    default NameValuePair toValuePair() {
        return new BasicNameValuePair(getParameterName(), getParameterValue());
    }

    String getParameterName();

    String getParameterValue();

    class BasicParameter implements EBGSQueryParameters {

        private final String name;
        private final String value;

        BasicParameter(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getParameterName() {
            return this.name;
        }

        @Override
        public String getParameterValue() {
            return this.value;
        }
    }
}
