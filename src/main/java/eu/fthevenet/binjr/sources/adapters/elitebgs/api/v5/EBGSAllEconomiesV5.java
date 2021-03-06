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
 * EBGSAllEconomiesV5
 */


public class EBGSAllEconomiesV5 {
  @SerializedName("name")
  private String name;

  @SerializedName("proportion")
  private int proportion;

  public EBGSAllEconomiesV5 name(String name) {
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

  public EBGSAllEconomiesV5 proportion(int proportion) {
    this.proportion = proportion;
    return this;
  }

   /**
   * Get proportion
   * @return proportion
  **/

  public int getProportion() {
    return proportion;
  }

  public void setProportion(int proportion) {
    this.proportion = proportion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EBGSAllEconomiesV5 ebGSAllEconomiesV5 = (EBGSAllEconomiesV5) o;
    return Objects.equals(this.name, ebGSAllEconomiesV5.name) &&
        Objects.equals(this.proportion, ebGSAllEconomiesV5.proportion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, proportion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSAllEconomiesV5 {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    proportion: ").append(toIndentedString(proportion)).append("\n");
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
