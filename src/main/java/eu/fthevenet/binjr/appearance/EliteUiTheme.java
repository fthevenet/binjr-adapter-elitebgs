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

package eu.fthevenet.binjr.appearance;

import eu.binjr.core.appearance.UserInterfaceThemes;


public class EliteUiTheme implements UserInterfaceThemes {

    @Override
    public String getCssPath() {
        return "/eu/fthevenet/binjr/elitebgs/css/3300AD.css";
    }

    @Override
    public String name() {
        return "3300AD";
    }

    @Override
    public String toString() {
        return "3300 AD";
    }
}
