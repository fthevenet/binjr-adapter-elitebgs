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
 * EBGSStationsPageV5
 */


public class EBGSStationsPageV5 {
  @SerializedName("docs")
  private List<EBGSStationsV5> docs;

  @SerializedName("total")
  private int total;

  @SerializedName("limit")
  private int limit;

  @SerializedName("page")
  private int page;

  @SerializedName("pages")
  private int pages;

  public EBGSStationsPageV5 docs(List<EBGSStationsV5> docs) {
    this.docs = docs;
    return this;
  }

  public EBGSStationsPageV5 addDocsItem(EBGSStationsV5 docsItem) {
    if (this.docs == null) {
      this.docs = new ArrayList<EBGSStationsV5>();
    }
    this.docs.add(docsItem);
    return this;
  }

   /**
   * Get docs
   * @return docs
  **/

  public List<EBGSStationsV5> getDocs() {
    return docs;
  }

  public void setDocs(List<EBGSStationsV5> docs) {
    this.docs = docs;
  }

  public EBGSStationsPageV5 total(int total) {
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public EBGSStationsPageV5 limit(int limit) {
    this.limit = limit;
    return this;
  }

   /**
   * Get limit
   * @return limit
  **/

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public EBGSStationsPageV5 page(int page) {
    this.page = page;
    return this;
  }

   /**
   * Get page
   * @return page
  **/

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public EBGSStationsPageV5 pages(int pages) {
    this.pages = pages;
    return this;
  }

   /**
   * Get pages
   * @return pages
  **/

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EBGSStationsPageV5 ebGSStationsPageV5 = (EBGSStationsPageV5) o;
    return Objects.equals(this.docs, ebGSStationsPageV5.docs) &&
        Objects.equals(this.total, ebGSStationsPageV5.total) &&
        Objects.equals(this.limit, ebGSStationsPageV5.limit) &&
        Objects.equals(this.page, ebGSStationsPageV5.page) &&
        Objects.equals(this.pages, ebGSStationsPageV5.pages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(docs, total, limit, page, pages);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EBGSStationsPageV5 {\n");
    
    sb.append("    docs: ").append(toIndentedString(docs)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pages: ").append(toIndentedString(pages)).append("\n");
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
