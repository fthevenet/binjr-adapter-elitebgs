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

import eu.binjr.common.version.Version;
import eu.binjr.core.data.adapters.AdapterMetadata;
import eu.binjr.core.data.adapters.BaseDataAdapterInfo;
import eu.binjr.core.data.adapters.SourceLocality;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;

import java.util.prefs.Preferences;

@AdapterMetadata(
        name = "Elite Dangerous BGS",
        description = "Elite Dangerous BGS Data Adapter",
        copyright = "Copyright © 2019-2021 Frederic Thevenet",
        license = "Apache-2.0",
        siteUrl = "https://github.com/fthevenet/binjr-adapter-elitebgs",
        adapterClass = EliteBgsAdapter.class,
        dialogClass = EliteBgsAdapterDialog.class,
        preferencesClass = EliteBgsAdapterPreferences.class,
        sourceLocality = SourceLocality.REMOTE,
        version = "1.1.0",
        apiLevel = "3.0.0")
public class EliteBgsAdapterInfo extends BaseDataAdapterInfo {

    public EliteBgsAdapterInfo() throws CannotInitializeDataAdapterException {
        super(EliteBgsAdapterInfo.class);
    }

}
