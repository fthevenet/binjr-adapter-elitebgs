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
 * EBGSFactionHistorySystemV5
 */


public class EBGSFactionHistorySystemV5 {
  @SerializedName("_id")
  private String _id;

  @SerializedName("__v")
  private int _v;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("updated_by")
  private String updatedBy;

  @SerializedName("faction_name")
  private String factionName;

  @SerializedName("faction_name_lower")
  private String factionNameLower;

  @SerializedName("faction_id")
  private String factionId;

  @SerializedName("state")
  private String state;

  @SerializedName("influence")
  private double influence;

  @SerializedName("happiness")
  private String happiness;

  @SerializedName("active_states")
  private List<EBGSStateActiveV5> activeStates;

  @SerializedName("pending_states")
  private List<EBGSStateV5> pendingStates;

  @SerializedName("recovering_states")
  private List<EBGSStateV5> recoveringStates;

  @SerializedName("conflicts")
  private List<EBGSConflictFactionV5> conflicts;

  @SerializedName("systems")
  private List<EBGSSystemRefV5> systems;

  public EBGSFactionHistorySystemV5 _id(String _id) {
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

  public EBGSFactionHistorySystemV5 _v(int _v) {
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

  public EBGSFactionHistorySystemV5 updatedAt(String updatedAt) {
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

  public EBGSFactionHistorySystemV5 updatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
    return this;
  }

   /**
   * Get updatedBy
   * @return updatedBy
  **/

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public EBGSFactionHistorySystemV5 factionName(String factionName) {
    this.factionName = factionName;
    return this;
  }

   /**
   * Get factionName
   * @return factionName
  **/

  public String getFactionName() {
    return factionName;
  }

  public void setFactionName(String factionName) {
    this.factionName = factionName;
  }

  public EBGSFactionHistorySystemV5 factionNameLower(String factionNameLower) {
    this.factionNameLower = factionNameLower;
    return this;
  }

   /**
   * Get factionNameLower
   * @return factionNameLower
  **/

  public String getFactionNameLower() {
    return factionNameLower;
  }

  public void setFactionNameLower(String factionNameLower) {
    this.factionNameLower = factionNameLower;
  }

  public EBGSFactionHistorySystemV5 factionId(String factionId) {
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

  public EBGSFactionHistorySystemV5 state(String state) {
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

  public EBGSFactionHistorySystemV5 influence(double influence) {
    this.influence = influence;
    return this;
  }

   /**
   * Get influence
   * @return influence
  **/

  public double getInfluence() {
    return influence;
  }

  public void setInfluence(double influence) {
    this.influence = influence;
  }

  public EBGSFactionHistorySystemV5 happiness(String happiness) {
    this.happiness = happiness;
    return this;
  }

   /**
   * Get happiness
   * @return happiness
  **/

  public String getHappiness() {
    return happiness;
  }

  public void setHappiness(String happiness) {
    this.happiness = happiness;
  }

  public EBGSFactionHistorySystemV5 activeStates(List<EBGSStateActiveV5> activeStates) {
    this.activeStates = activeStates;
    return this;
  }

  public EBGSFactionHistorySystemV5 addActiveStatesItem(EBGSStateActiveV5 activeStatesItem) {
    if (this.activeStates == null) {
      this.activeStates = new ArrayList<EBGSStateActiveV5>();
    }
    this.activeStates.add(activeStatesItem);
    return this;
  }

   /**
   * Get activeStates
   * @return activeStates
  **/

  public List<EBGSStateActiveV5> getActiveStates() {
    return activeStates;
  }

  public void setActiveStates(List<EBGSStateActiveV5> activeStates) {
    this.activeStates = activeStates;
  }

  public EBGSFactionHistorySystemV5 pendingStates(List<EBGSStateV5> pendingStates) {
    this.pendingStates = pendingStates;
    return this;
  }

  public EBGSFactionHistorySystemV5 addPendingStatesItem(EBGSStateV5 pendingStatesItem) {
    if (this.pendingStates == null) {
      this.pendingStates = new ArrayList<EBGSStateV5>();
    }
    this.pendingStates.add(pendingStatesItem);
    return this;
  }

   /**
   * Get pendingStates
   * @return pendingStates
  **/

  public List<EBGSStateV5> getPendingStates() {
    return pendingStates;
  }

  public void setPendingStates(List<EBGSStateV5> pendingStates) {
    this.pendingStates = pendingStates;
  }

  public EBGSFactionHistorySystemV5 recoveringStates(List<EBGSStateV5> recoveringStates) {
    this.recoveringStates = recoveringStates;
    return this;
  }

  public EBGSFactionHistorySystemV5 addRecoveringStatesItem(EBGSStateV5 recoveringStatesItem) {
    if (this.recoveringStates == null) {
      this.recoveringStates = new ArrayList<EBGSStateV5>();
    }
    this.recoveringStates.add(recoveringStatesItem);
    return this;
  }

   /**
   * Get recoveringStates
   * @return recoveringStates
  **/

  public List<EBGSStateV5> getRecoveringStates() {
    return recoveringStates;
  }

  public void setRecoveringStates(List<EBGSStateV5> recoveringStates) {
    this.recoveringStates = recoveringStates;
  }

  public EBGSFactionHistorySystemV5 conflicts(List<EBGSConflictFactionV5> conflicts) {
    this.conflicts = conflicts;
    return this;
  }

  public EBGSFactionHistorySystemV5 addConflictsItem(EBGSConflictFactionV5 conflictsItem) {
    if (this.conflicts == null) {
      this.conflicts = new ArrayList<EBGSConflictFactionV5>();
    }
    this.conflicts.add(conflictsItem);
    return this;
  }

   /**
   * Get conflicts
   * @return conflicts
  **/

  public List<EBGSConflictFactionV5> getConflicts() {
    return conflicts;
  }

  public void setConflicts(List<EBGSConflictFactionV5> conflicts) {
    this.conflicts = conflicts;
  }

  public EBGSFactionHistorySystemV5 systems(List<EBGSSystemRefV5> systems) {
    this.systems = systems;
    return this;
  }

  public EBGSFactionHistorySystemV5 addSystemsItem(EBGSSystemRefV5 systemsItem) {
    if (this.systems == null) {
      this.systems = new ArrayList<EBGSSystemRefV5>();
    }
    this.systems.add(systemsItem);
    return this;
  }

   /**
   * Get systems
   * @return systems
  **/

  public List<EBGSSystemRefV5> getSystems() {
    return systems;
  }

  public void setSystems(List<EBGSSystemRefV5> systems) {
    this.systems = systems;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EBGSFactionHistorySystemV5 ebGSFactionHistorySystemV5 = (EBGSFactionHistorySystemV5) o;
    return Objects.equals(this._id, ebGSFactionHistorySystemV5._id) &&
        Objects.equals(this._v, ebGSFactionHistorySystemV5._v) &&
        Objects.equals(this.updatedAt, ebGSFactionHistorySystemV5.updatedAt) &&
        Objects.equals(this.updatedBy, ebGSFactionHistorySystemV5.updatedBy) &&
        Objects.equals(this.factionName, ebGSFactionHistorySystemV5.factionName) &&
        Objects.equals(this.factionNameLower, ebGSFactionHistorySystemV5.factionNameLower) &&
        Objects.equals(this.factionId, ebGSFactionHistorySystemV5.factionId) &&
        Objects.equals(this.state, ebGSFactionHistorySystemV5.state) &&
        Objects.equals(this.influence, ebGSFactionHistorySystemV5.influence) &&
        Objects.equals(this.happiness, ebGSFactionHistorySystemV5.happiness) &&
        Objects.equals(this.activeStates, ebGSFactionHistorySystemV5.activeStates) &&
        Objects.equals(this.pendingStates, ebGSFactionHistorySystemV5.pendingStates) &&
        Objects.equals(this.recoveringStates, ebGSFactionHistorySystemV5.recoveringStates) &&
        Objects.equals(this.conflicts, ebGSFactionHistorySystemV5.conflicts) &&
        Objects.equals(this.systems, ebGSFactionHistorySystemV5.systems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, _v, updatedAt, updatedBy, factionName, factionNameLower, factionId, state, influence, happiness, activeStates, pendingStates, recoveringStates, conflicts, systems);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSFactionHistorySystemV5 {\n");
    
    sb.append("    _id: ").append(toIndentedString(_id)).append("\n");
    sb.append("    _v: ").append(toIndentedString(_v)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
    sb.append("    factionName: ").append(toIndentedString(factionName)).append("\n");
    sb.append("    factionNameLower: ").append(toIndentedString(factionNameLower)).append("\n");
    sb.append("    factionId: ").append(toIndentedString(factionId)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    influence: ").append(toIndentedString(influence)).append("\n");
    sb.append("    happiness: ").append(toIndentedString(happiness)).append("\n");
    sb.append("    activeStates: ").append(toIndentedString(activeStates)).append("\n");
    sb.append("    pendingStates: ").append(toIndentedString(pendingStates)).append("\n");
    sb.append("    recoveringStates: ").append(toIndentedString(recoveringStates)).append("\n");
    sb.append("    conflicts: ").append(toIndentedString(conflicts)).append("\n");
    sb.append("    systems: ").append(toIndentedString(systems)).append("\n");
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