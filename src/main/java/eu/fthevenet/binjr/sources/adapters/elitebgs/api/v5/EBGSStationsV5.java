/*
 *    Copyright 2021 Frederic Thevenet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * Elite BGS API
 * An API for Elite BGS
 *
 * OpenAPI spec version: 5.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package eu.fthevenet.binjr.sources.adapters.elitebgs.api.v5;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * EBGSStationsV5
 */


public class EBGSStationsV5 {
  @SerializedName("_id")
  private String _id;

  @SerializedName("__v")
  private int _v;

  @SerializedName("eddb_id")
  private int eddbId;

  @SerializedName("name")
  private String name;

  @SerializedName("name_lower")
  private String nameLower;

  @SerializedName("name_aliases")
  private List<EBGSNameAliasV5> nameAliases;

  @SerializedName("market_id")
  private String marketId;

  @SerializedName("type")
  private String type;

  @SerializedName("system")
  private String system;

  @SerializedName("system_lower")
  private String systemLower;

  @SerializedName("system_id")
  private String systemId;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("government")
  private String government;

  @SerializedName("economy")
  private String economy;

  @SerializedName("all_economies")
  private List<EBGSAllEconomiesV5> allEconomies;

  @SerializedName("allegiance")
  private String allegiance;

  @SerializedName("state")
  private String state;

  @SerializedName("distance_from_star")
  private int distanceFromStar;

  @SerializedName("controlling_minor_faction_cased")
  private String controllingMinorFactionCased;

  @SerializedName("controlling_minor_faction")
  private String controllingMinorFaction;

  @SerializedName("controlling_minor_faction_id")
  private String controllingMinorFactionId;

  @SerializedName("services")
  private List<EBGSStationServicesV5> services;

  @SerializedName("history")
  private List<EBGSStationHistoryV5> history;

  public EBGSStationsV5 _id(String _id) {
    this._id = _id;
    return this;
  }

   /**
   * Get _id
   * @return _id
  **/

  public String getId() {
    return _id;
  }

  public void setId(String _id) {
    this._id = _id;
  }

  public EBGSStationsV5 _v(int _v) {
    this._v = _v;
    return this;
  }

   /**
   * Get _v
   * @return _v
  **/

  public int getV() {
    return _v;
  }

  public void setV(int _v) {
    this._v = _v;
  }

  public EBGSStationsV5 eddbId(int eddbId) {
    this.eddbId = eddbId;
    return this;
  }

   /**
   * Get eddbId
   * @return eddbId
  **/

  public int getEddbId() {
    return eddbId;
  }

  public void setEddbId(int eddbId) {
    this.eddbId = eddbId;
  }

  public EBGSStationsV5 name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EBGSStationsV5 nameLower(String nameLower) {
    this.nameLower = nameLower;
    return this;
  }

   /**
   * Get nameLower
   * @return nameLower
  **/

  public String getNameLower() {
    return nameLower;
  }

  public void setNameLower(String nameLower) {
    this.nameLower = nameLower;
  }

  public EBGSStationsV5 nameAliases(List<EBGSNameAliasV5> nameAliases) {
    this.nameAliases = nameAliases;
    return this;
  }

  public EBGSStationsV5 addNameAliasesItem(EBGSNameAliasV5 nameAliasesItem) {
    if (this.nameAliases == null) {
      this.nameAliases = new ArrayList<EBGSNameAliasV5>();
    }
    this.nameAliases.add(nameAliasesItem);
    return this;
  }

   /**
   * Get nameAliases
   * @return nameAliases
  **/

  public List<EBGSNameAliasV5> getNameAliases() {
    return nameAliases;
  }

  public void setNameAliases(List<EBGSNameAliasV5> nameAliases) {
    this.nameAliases = nameAliases;
  }

  public EBGSStationsV5 marketId(String marketId) {
    this.marketId = marketId;
    return this;
  }

   /**
   * Get marketId
   * @return marketId
  **/

  public String getMarketId() {
    return marketId;
  }

  public void setMarketId(String marketId) {
    this.marketId = marketId;
  }

  public EBGSStationsV5 type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public EBGSStationsV5 system(String system) {
    this.system = system;
    return this;
  }

   /**
   * Get system
   * @return system
  **/

  public String getSystem() {
    return system;
  }

  public void setSystem(String system) {
    this.system = system;
  }

  public EBGSStationsV5 systemLower(String systemLower) {
    this.systemLower = systemLower;
    return this;
  }

   /**
   * Get systemLower
   * @return systemLower
  **/

  public String getSystemLower() {
    return systemLower;
  }

  public void setSystemLower(String systemLower) {
    this.systemLower = systemLower;
  }

  public EBGSStationsV5 systemId(String systemId) {
    this.systemId = systemId;
    return this;
  }

   /**
   * Get systemId
   * @return systemId
  **/

  public String getSystemId() {
    return systemId;
  }

  public void setSystemId(String systemId) {
    this.systemId = systemId;
  }

  public EBGSStationsV5 updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Get updatedAt
   * @return updatedAt
  **/

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public EBGSStationsV5 government(String government) {
    this.government = government;
    return this;
  }

   /**
   * Get government
   * @return government
  **/

  public String getGovernment() {
    return government;
  }

  public void setGovernment(String government) {
    this.government = government;
  }

  public EBGSStationsV5 economy(String economy) {
    this.economy = economy;
    return this;
  }

   /**
   * Get economy
   * @return economy
  **/

  public String getEconomy() {
    return economy;
  }

  public void setEconomy(String economy) {
    this.economy = economy;
  }

  public EBGSStationsV5 allEconomies(List<EBGSAllEconomiesV5> allEconomies) {
    this.allEconomies = allEconomies;
    return this;
  }

  public EBGSStationsV5 addAllEconomiesItem(EBGSAllEconomiesV5 allEconomiesItem) {
    if (this.allEconomies == null) {
      this.allEconomies = new ArrayList<EBGSAllEconomiesV5>();
    }
    this.allEconomies.add(allEconomiesItem);
    return this;
  }

   /**
   * Get allEconomies
   * @return allEconomies
  **/

  public List<EBGSAllEconomiesV5> getAllEconomies() {
    return allEconomies;
  }

  public void setAllEconomies(List<EBGSAllEconomiesV5> allEconomies) {
    this.allEconomies = allEconomies;
  }

  public EBGSStationsV5 allegiance(String allegiance) {
    this.allegiance = allegiance;
    return this;
  }

   /**
   * Get allegiance
   * @return allegiance
  **/

  public String getAllegiance() {
    return allegiance;
  }

  public void setAllegiance(String allegiance) {
    this.allegiance = allegiance;
  }

  public EBGSStationsV5 state(String state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public EBGSStationsV5 distanceFromStar(int distanceFromStar) {
    this.distanceFromStar = distanceFromStar;
    return this;
  }

   /**
   * Get distanceFromStar
   * @return distanceFromStar
  **/

  public int getDistanceFromStar() {
    return distanceFromStar;
  }

  public void setDistanceFromStar(int distanceFromStar) {
    this.distanceFromStar = distanceFromStar;
  }

  public EBGSStationsV5 controllingMinorFactionCased(String controllingMinorFactionCased) {
    this.controllingMinorFactionCased = controllingMinorFactionCased;
    return this;
  }

   /**
   * Get controllingMinorFactionCased
   * @return controllingMinorFactionCased
  **/

  public String getControllingMinorFactionCased() {
    return controllingMinorFactionCased;
  }

  public void setControllingMinorFactionCased(String controllingMinorFactionCased) {
    this.controllingMinorFactionCased = controllingMinorFactionCased;
  }

  public EBGSStationsV5 controllingMinorFaction(String controllingMinorFaction) {
    this.controllingMinorFaction = controllingMinorFaction;
    return this;
  }

   /**
   * Get controllingMinorFaction
   * @return controllingMinorFaction
  **/

  public String getControllingMinorFaction() {
    return controllingMinorFaction;
  }

  public void setControllingMinorFaction(String controllingMinorFaction) {
    this.controllingMinorFaction = controllingMinorFaction;
  }

  public EBGSStationsV5 controllingMinorFactionId(String controllingMinorFactionId) {
    this.controllingMinorFactionId = controllingMinorFactionId;
    return this;
  }

   /**
   * Get controllingMinorFactionId
   * @return controllingMinorFactionId
  **/

  public String getControllingMinorFactionId() {
    return controllingMinorFactionId;
  }

  public void setControllingMinorFactionId(String controllingMinorFactionId) {
    this.controllingMinorFactionId = controllingMinorFactionId;
  }

  public EBGSStationsV5 services(List<EBGSStationServicesV5> services) {
    this.services = services;
    return this;
  }

  public EBGSStationsV5 addServicesItem(EBGSStationServicesV5 servicesItem) {
    if (this.services == null) {
      this.services = new ArrayList<EBGSStationServicesV5>();
    }
    this.services.add(servicesItem);
    return this;
  }

   /**
   * Get services
   * @return services
  **/

  public List<EBGSStationServicesV5> getServices() {
    return services;
  }

  public void setServices(List<EBGSStationServicesV5> services) {
    this.services = services;
  }

  public EBGSStationsV5 history(List<EBGSStationHistoryV5> history) {
    this.history = history;
    return this;
  }

  public EBGSStationsV5 addHistoryItem(EBGSStationHistoryV5 historyItem) {
    if (this.history == null) {
      this.history = new ArrayList<EBGSStationHistoryV5>();
    }
    this.history.add(historyItem);
    return this;
  }

   /**
   * Get history
   * @return history
  **/

  public List<EBGSStationHistoryV5> getHistory() {
    return history;
  }

  public void setHistory(List<EBGSStationHistoryV5> history) {
    this.history = history;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EBGSStationsV5 ebGSStationsV5 = (EBGSStationsV5) o;
    return Objects.equals(this._id, ebGSStationsV5._id) &&
        Objects.equals(this._v, ebGSStationsV5._v) &&
        Objects.equals(this.eddbId, ebGSStationsV5.eddbId) &&
        Objects.equals(this.name, ebGSStationsV5.name) &&
        Objects.equals(this.nameLower, ebGSStationsV5.nameLower) &&
        Objects.equals(this.nameAliases, ebGSStationsV5.nameAliases) &&
        Objects.equals(this.marketId, ebGSStationsV5.marketId) &&
        Objects.equals(this.type, ebGSStationsV5.type) &&
        Objects.equals(this.system, ebGSStationsV5.system) &&
        Objects.equals(this.systemLower, ebGSStationsV5.systemLower) &&
        Objects.equals(this.systemId, ebGSStationsV5.systemId) &&
        Objects.equals(this.updatedAt, ebGSStationsV5.updatedAt) &&
        Objects.equals(this.government, ebGSStationsV5.government) &&
        Objects.equals(this.economy, ebGSStationsV5.economy) &&
        Objects.equals(this.allEconomies, ebGSStationsV5.allEconomies) &&
        Objects.equals(this.allegiance, ebGSStationsV5.allegiance) &&
        Objects.equals(this.state, ebGSStationsV5.state) &&
        Objects.equals(this.distanceFromStar, ebGSStationsV5.distanceFromStar) &&
        Objects.equals(this.controllingMinorFactionCased, ebGSStationsV5.controllingMinorFactionCased) &&
        Objects.equals(this.controllingMinorFaction, ebGSStationsV5.controllingMinorFaction) &&
        Objects.equals(this.controllingMinorFactionId, ebGSStationsV5.controllingMinorFactionId) &&
        Objects.equals(this.services, ebGSStationsV5.services) &&
        Objects.equals(this.history, ebGSStationsV5.history);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, _v, eddbId, name, nameLower, nameAliases, marketId, type, system, systemLower, systemId, updatedAt, government, economy, allEconomies, allegiance, state, distanceFromStar, controllingMinorFactionCased, controllingMinorFaction, controllingMinorFactionId, services, history);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSStationsV5 {\n");
    
    sb.append("    _id: ").append(toIndentedString(_id)).append("\n");
    sb.append("    _v: ").append(toIndentedString(_v)).append("\n");
    sb.append("    eddbId: ").append(toIndentedString(eddbId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nameLower: ").append(toIndentedString(nameLower)).append("\n");
    sb.append("    nameAliases: ").append(toIndentedString(nameAliases)).append("\n");
    sb.append("    marketId: ").append(toIndentedString(marketId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    systemLower: ").append(toIndentedString(systemLower)).append("\n");
    sb.append("    systemId: ").append(toIndentedString(systemId)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    government: ").append(toIndentedString(government)).append("\n");
    sb.append("    economy: ").append(toIndentedString(economy)).append("\n");
    sb.append("    allEconomies: ").append(toIndentedString(allEconomies)).append("\n");
    sb.append("    allegiance: ").append(toIndentedString(allegiance)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    distanceFromStar: ").append(toIndentedString(distanceFromStar)).append("\n");
    sb.append("    controllingMinorFactionCased: ").append(toIndentedString(controllingMinorFactionCased)).append("\n");
    sb.append("    controllingMinorFaction: ").append(toIndentedString(controllingMinorFaction)).append("\n");
    sb.append("    controllingMinorFactionId: ").append(toIndentedString(controllingMinorFactionId)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
    sb.append("    history: ").append(toIndentedString(history)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
