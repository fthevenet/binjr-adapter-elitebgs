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
 * EBGSFactionsV5
 */


public class EBGSFactionsV5 {
  @SerializedName("_id")
  private String _id;

  @SerializedName("__v")
  private int _v ;

  @SerializedName("eddb_id")
  private int eddbId ;

  @SerializedName("name")
  private String name;

  @SerializedName("name_lower")
  private String nameLower;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("government")
  private String government;

  @SerializedName("allegiance")
  private String allegiance;

  @SerializedName("home_system_name")
  private String homeSystemName;

  @SerializedName("is_player_faction")
  private boolean isPlayerFaction;

  @SerializedName("faction_presence")
  private List<EBGSFactionPresenceV5> factionPresence;

  @SerializedName("history")
  private List<EBGSFactionHistoryV5> history;

  public EBGSFactionsV5 _id(String _id) {
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

  public EBGSFactionsV5 _v(int _v) {
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

  public EBGSFactionsV5 eddbId(int eddbId) {
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

  public EBGSFactionsV5 name(String name) {
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

  public EBGSFactionsV5 nameLower(String nameLower) {
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

  public EBGSFactionsV5 updatedAt(String updatedAt) {
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

  public EBGSFactionsV5 government(String government) {
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

  public EBGSFactionsV5 allegiance(String allegiance) {
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

  public EBGSFactionsV5 homeSystemName(String homeSystemName) {
    this.homeSystemName = homeSystemName;
    return this;
  }

   /**
   * Get homeSystemName
   * @return homeSystemName
  **/

  public String getHomeSystemName() {
    return homeSystemName;
  }

  public void setHomeSystemName(String homeSystemName) {
    this.homeSystemName = homeSystemName;
  }

  public EBGSFactionsV5 isPlayerFaction(boolean isPlayerFaction) {
    this.isPlayerFaction = isPlayerFaction;
    return this;
  }

   /**
   * Get isPlayerFaction
   * @return isPlayerFaction
  **/

  public boolean isIsPlayerFaction() {
    return isPlayerFaction;
  }

  public void setIsPlayerFaction(boolean isPlayerFaction) {
    this.isPlayerFaction = isPlayerFaction;
  }

  public EBGSFactionsV5 factionPresence(List<EBGSFactionPresenceV5> factionPresence) {
    this.factionPresence = factionPresence;
    return this;
  }

  public EBGSFactionsV5 addFactionPresenceItem(EBGSFactionPresenceV5 factionPresenceItem) {
    if (this.factionPresence == null) {
      this.factionPresence = new ArrayList<EBGSFactionPresenceV5>();
    }
    this.factionPresence.add(factionPresenceItem);
    return this;
  }

   /**
   * Get factionPresence
   * @return factionPresence
  **/

  public List<EBGSFactionPresenceV5> getFactionPresence() {
    return factionPresence;
  }

  public void setFactionPresence(List<EBGSFactionPresenceV5> factionPresence) {
    this.factionPresence = factionPresence;
  }

  public EBGSFactionsV5 history(List<EBGSFactionHistoryV5> history) {
    this.history = history;
    return this;
  }

  public EBGSFactionsV5 addHistoryItem(EBGSFactionHistoryV5 historyItem) {
    if (this.history == null) {
      this.history = new ArrayList<EBGSFactionHistoryV5>();
    }
    this.history.add(historyItem);
    return this;
  }

   /**
   * Get history
   * @return history
  **/

  public List<EBGSFactionHistoryV5> getHistory() {
    return history;
  }

  public void setHistory(List<EBGSFactionHistoryV5> history) {
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
    EBGSFactionsV5 ebGSFactionsV5 = (EBGSFactionsV5) o;
    return Objects.equals(this._id, ebGSFactionsV5._id) &&
        Objects.equals(this._v, ebGSFactionsV5._v) &&
        Objects.equals(this.eddbId, ebGSFactionsV5.eddbId) &&
        Objects.equals(this.name, ebGSFactionsV5.name) &&
        Objects.equals(this.nameLower, ebGSFactionsV5.nameLower) &&
        Objects.equals(this.updatedAt, ebGSFactionsV5.updatedAt) &&
        Objects.equals(this.government, ebGSFactionsV5.government) &&
        Objects.equals(this.allegiance, ebGSFactionsV5.allegiance) &&
        Objects.equals(this.homeSystemName, ebGSFactionsV5.homeSystemName) &&
        Objects.equals(this.isPlayerFaction, ebGSFactionsV5.isPlayerFaction) &&
        Objects.equals(this.factionPresence, ebGSFactionsV5.factionPresence) &&
        Objects.equals(this.history, ebGSFactionsV5.history);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, _v, eddbId, name, nameLower, updatedAt, government, allegiance, homeSystemName, isPlayerFaction, factionPresence, history);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSFactionsV5 {\n");
    
    sb.append("    _id: ").append(toIndentedString(_id)).append("\n");
    sb.append("    _v: ").append(toIndentedString(_v)).append("\n");
    sb.append("    eddbId: ").append(toIndentedString(eddbId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nameLower: ").append(toIndentedString(nameLower)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    government: ").append(toIndentedString(government)).append("\n");
    sb.append("    allegiance: ").append(toIndentedString(allegiance)).append("\n");
    sb.append("    homeSystemName: ").append(toIndentedString(homeSystemName)).append("\n");
    sb.append("    isPlayerFaction: ").append(toIndentedString(isPlayerFaction)).append("\n");
    sb.append("    factionPresence: ").append(toIndentedString(factionPresence)).append("\n");
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
