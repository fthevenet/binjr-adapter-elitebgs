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

package eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4;

public class EBGSFactionsV4 {
    public  String _id;
    public int __v;
    public int eddb_id;
    public String name;
    public String name_lower;
    public String updated_at;
    public String government;
    public String allegiance;
    public String home_system_name;
    public boolean is_player_faction;
    public EBGSFactionPresenceV4[] faction_presence;
    public EBGSFactionHistoryV4[] history;
}
