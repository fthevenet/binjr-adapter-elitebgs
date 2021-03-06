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
 * EBGSStationHistoryV5
 */


public class EBGSStationHistoryV5 {
  @SerializedName("_id")
  private String _id;

  @SerializedName("__v")
  private int _v;

  @SerializedName("updated_at")
  private String updatedAt;

  @SerializedName("updated_by")
  private String updatedBy;

  @SerializedName("government")
  private String government;

  @SerializedName("allegiance")
  private String allegiance;

  @SerializedName("state")
  private String state;

  @SerializedName("controlling_minor_faction_cased")
  private String controllingMinorFactionCased;

  @SerializedName("controlling_minor_faction")
  private String controllingMinorFaction;

  @SerializedName("controlling_minor_faction_id")
  private String controllingMinorFactionId;

  @SerializedName("services")
  private List<EBGSStationServicesV5> services;

  public EBGSStationHistoryV5 _id(String _id) {
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

  public EBGSStationHistoryV5 _v(int _v) {
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

  public EBGSStationHistoryV5 updatedAt(String updatedAt) {
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

  public EBGSStationHistoryV5 updatedBy(String updatedBy) {
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

  public EBGSStationHistoryV5 government(String government) {
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

  public EBGSStationHistoryV5 allegiance(String allegiance) {
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

  public EBGSStationHistoryV5 state(String state) {
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

  public EBGSStationHistoryV5 controllingMinorFactionCased(String controllingMinorFactionCased) {
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

  public EBGSStationHistoryV5 controllingMinorFaction(String controllingMinorFaction) {
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

  public EBGSStationHistoryV5 controllingMinorFactionId(String controllingMinorFactionId) {
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

  public EBGSStationHistoryV5 services(List<EBGSStationServicesV5> services) {
    this.services = services;
    return this;
  }

  public EBGSStationHistoryV5 addServicesItem(EBGSStationServicesV5 servicesItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EBGSStationHistoryV5 ebGSStationHistoryV5 = (EBGSStationHistoryV5) o;
    return Objects.equals(this._id, ebGSStationHistoryV5._id) &&
        Objects.equals(this._v, ebGSStationHistoryV5._v) &&
        Objects.equals(this.updatedAt, ebGSStationHistoryV5.updatedAt) &&
        Objects.equals(this.updatedBy, ebGSStationHistoryV5.updatedBy) &&
        Objects.equals(this.government, ebGSStationHistoryV5.government) &&
        Objects.equals(this.allegiance, ebGSStationHistoryV5.allegiance) &&
        Objects.equals(this.state, ebGSStationHistoryV5.state) &&
        Objects.equals(this.controllingMinorFactionCased, ebGSStationHistoryV5.controllingMinorFactionCased) &&
        Objects.equals(this.controllingMinorFaction, ebGSStationHistoryV5.controllingMinorFaction) &&
        Objects.equals(this.controllingMinorFactionId, ebGSStationHistoryV5.controllingMinorFactionId) &&
        Objects.equals(this.services, ebGSStationHistoryV5.services);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, _v, updatedAt, updatedBy, government, allegiance, state, controllingMinorFactionCased, controllingMinorFaction, controllingMinorFactionId, services);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSStationHistoryV5 {\n");
    
    sb.append("    _id: ").append(toIndentedString(_id)).append("\n");
    sb.append("    _v: ").append(toIndentedString(_v)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
    sb.append("    government: ").append(toIndentedString(government)).append("\n");
    sb.append("    allegiance: ").append(toIndentedString(allegiance)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    controllingMinorFactionCased: ").append(toIndentedString(controllingMinorFactionCased)).append("\n");
    sb.append("    controllingMinorFaction: ").append(toIndentedString(controllingMinorFaction)).append("\n");
    sb.append("    controllingMinorFactionId: ").append(toIndentedString(controllingMinorFactionId)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
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
