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

import java.util.Objects;
/**
 * EBGSConflictSystemFactionV5
 */


public class EBGSConflictSystemFactionV5 {
  @SerializedName("faction_id")
  private String factionId;

  @SerializedName("name")
  private String name;

  @SerializedName("name_lower")
  private String nameLower;

  @SerializedName("station_id")
  private String stationId;

  @SerializedName("stake")
  private String stake;

  @SerializedName("stake_lower")
  private String stakeLower;

  @SerializedName("days_won")
  private int daysWon;

  public EBGSConflictSystemFactionV5 factionId(String factionId) {
    this.factionId = factionId;
    return this;
  }

   /**
   * Get factionId
   * @return factionId
  **/

  public String getFactionId() {
    return factionId;
  }

  public void setFactionId(String factionId) {
    this.factionId = factionId;
  }

  public EBGSConflictSystemFactionV5 name(String name) {
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

  public EBGSConflictSystemFactionV5 nameLower(String nameLower) {
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

  public EBGSConflictSystemFactionV5 stationId(String stationId) {
    this.stationId = stationId;
    return this;
  }

   /**
   * Get stationId
   * @return stationId
  **/

  public String getStationId() {
    return stationId;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public EBGSConflictSystemFactionV5 stake(String stake) {
    this.stake = stake;
    return this;
  }

   /**
   * Get stake
   * @return stake
  **/

  public String getStake() {
    return stake;
  }

  public void setStake(String stake) {
    this.stake = stake;
  }

  public EBGSConflictSystemFactionV5 stakeLower(String stakeLower) {
    this.stakeLower = stakeLower;
    return this;
  }

   /**
   * Get stakeLower
   * @return stakeLower
  **/

  public String getStakeLower() {
    return stakeLower;
  }

  public void setStakeLower(String stakeLower) {
    this.stakeLower = stakeLower;
  }

  public EBGSConflictSystemFactionV5 daysWon(int daysWon) {
    this.daysWon = daysWon;
    return this;
  }

   /**
   * Get daysWon
   * @return daysWon
  **/

  public int getDaysWon() {
    return daysWon;
  }

  public void setDaysWon(int daysWon) {
    this.daysWon = daysWon;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EBGSConflictSystemFactionV5 ebGSConflictSystemFactionV5 = (EBGSConflictSystemFactionV5) o;
    return Objects.equals(this.factionId, ebGSConflictSystemFactionV5.factionId) &&
        Objects.equals(this.name, ebGSConflictSystemFactionV5.name) &&
        Objects.equals(this.nameLower, ebGSConflictSystemFactionV5.nameLower) &&
        Objects.equals(this.stationId, ebGSConflictSystemFactionV5.stationId) &&
        Objects.equals(this.stake, ebGSConflictSystemFactionV5.stake) &&
        Objects.equals(this.stakeLower, ebGSConflictSystemFactionV5.stakeLower) &&
        Objects.equals(this.daysWon, ebGSConflictSystemFactionV5.daysWon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(factionId, name, nameLower, stationId, stake, stakeLower, daysWon);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSConflictSystemFactionV5 {\n");
    
    sb.append("    factionId: ").append(toIndentedString(factionId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nameLower: ").append(toIndentedString(nameLower)).append("\n");
    sb.append("    stationId: ").append(toIndentedString(stationId)).append("\n");
    sb.append("    stake: ").append(toIndentedString(stake)).append("\n");
    sb.append("    stakeLower: ").append(toIndentedString(stakeLower)).append("\n");
    sb.append("    daysWon: ").append(toIndentedString(daysWon)).append("\n");
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
