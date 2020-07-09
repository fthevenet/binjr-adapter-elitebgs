/*
 * Copyright 2019-2020 Frederic Thevenet
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

import eu.binjr.common.version.Version;
import eu.binjr.core.data.adapters.BaseDataAdapterInfo;
import eu.binjr.core.data.adapters.SourceLocality;

import java.util.prefs.Preferences;

public class EliteBgsAdapterInfo extends BaseDataAdapterInfo {

    public EliteBgsAdapterInfo(){
        super("Elite Dangerous BGS",
                "Elite Dangerous BGS Data Adapter",
                "Copyright © 2019-2020 Frederic Thevenet",
                "Apache-2.0",
                "https://github.com/fthevenet/binjr-adapter-elitebgs",
                EliteBgsAdapter.class,
                EliteBgsAdapterDialog.class,
                EliteBgsAdapterPreferences.getInstance(),
                SourceLocality.REMOTE,
                Version.parseVersion("3.0.0"));
    }
}
