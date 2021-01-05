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
 * EBGSConflictFactionV5
 */


public class EBGSConflictFactionV5 {
  @SerializedName("type")
  private String type;

  @SerializedName("status")
  private String status;

  @SerializedName("opponent_name")
  private String opponentName;

  @SerializedName("opponent_name_lower")
  private String opponentNameLower;

  @SerializedName("opponent_faction_id")
  private String opponentFactionId;

  @SerializedName("station_id")
  private String stationId;

  @SerializedName("stake")
  private String stake;

  @SerializedName("stake_lower")
  private String stakeLower;

  @SerializedName("days_won")
  private int daysWon;

  public EBGSConflictFactionV5 type(String type) {
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

  public EBGSConflictFactionV5 status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public EBGSConflictFactionV5 opponentName(String opponentName) {
    this.opponentName = opponentName;
    return this;
  }

   /**
   * Get opponentName
   * @return opponentName
  **/

  public String getOpponentName() {
    return opponentName;
  }

  public void setOpponentName(String opponentName) {
    this.opponentName = opponentName;
  }

  public EBGSConflictFactionV5 opponentNameLower(String opponentNameLower) {
    this.opponentNameLower = opponentNameLower;
    return this;
  }

   /**
   * Get opponentNameLower
   * @return opponentNameLower
  **/

  public String getOpponentNameLower() {
    return opponentNameLower;
  }

  public void setOpponentNameLower(String opponentNameLower) {
    this.opponentNameLower = opponentNameLower;
  }

  public EBGSConflictFactionV5 opponentFactionId(String opponentFactionId) {
    this.opponentFactionId = opponentFactionId;
    return this;
  }

   /**
   * Get opponentFactionId
   * @return opponentFactionId
  **/

  public String getOpponentFactionId() {
    return opponentFactionId;
  }

  public void setOpponentFactionId(String opponentFactionId) {
    this.opponentFactionId = opponentFactionId;
  }

  public EBGSConflictFactionV5 stationId(String stationId) {
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

  public EBGSConflictFactionV5 stake(String stake) {
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

  public EBGSConflictFactionV5 stakeLower(String stakeLower) {
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

  public EBGSConflictFactionV5 daysWon(int daysWon) {
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
    EBGSConflictFactionV5 ebGSConflictFactionV5 = (EBGSConflictFactionV5) o;
    return Objects.equals(this.type, ebGSConflictFactionV5.type) &&
        Objects.equals(this.status, ebGSConflictFactionV5.status) &&
        Objects.equals(this.opponentName, ebGSConflictFactionV5.opponentName) &&
        Objects.equals(this.opponentNameLower, ebGSConflictFactionV5.opponentNameLower) &&
        Objects.equals(this.opponentFactionId, ebGSConflictFactionV5.opponentFactionId) &&
        Objects.equals(this.stationId, ebGSConflictFactionV5.stationId) &&
        Objects.equals(this.stake, ebGSConflictFactionV5.stake) &&
        Objects.equals(this.stakeLower, ebGSConflictFactionV5.stakeLower) &&
        Objects.equals(this.daysWon, ebGSConflictFactionV5.daysWon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, status, opponentName, opponentNameLower, opponentFactionId, stationId, stake, stakeLower, daysWon);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSConflictFactionV5 {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    opponentName: ").append(toIndentedString(opponentName)).append("\n");
    sb.append("    opponentNameLower: ").append(toIndentedString(opponentNameLower)).append("\n");
    sb.append("    opponentFactionId: ").append(toIndentedString(opponentFactionId)).append("\n");
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