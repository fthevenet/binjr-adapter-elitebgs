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

public class EBGSSystemsV4 {
    public String _id;
    public int __v;
    public int eddb_id;
    public String name;
    public String name_lower;
    public  double x;
    public  double y;
    public double z;
    public long population;
    public  String government;
    public String allegiance;
    public String state;
    public String security;
    public String primary_economy;
    public boolean needs_permit;
    public String updated_at;
    public String controlling_minor_faction;
    public String reserve_type;

    public EBGSSystemPresenceV4[] factions;
    public EBGSSystemHistoryV4[] history;
}
