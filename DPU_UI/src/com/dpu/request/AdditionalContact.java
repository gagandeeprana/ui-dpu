
package com.dpu.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
 

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include= Inclusion.NON_NULL)
@JsonPropertyOrder({
    "additionalContactId",
    "customerName",
    "position",
    "phone",
    "ext",
    "fax",
    "prefix",
    "cellular",
    "status",
    "email"
})
public class AdditionalContact {

    @JsonProperty("additionalContactId")
    private Long additionalContactId;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("position")
    private String position;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("ext")
    private String ext;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("prefix")
    private String prefix;
    @JsonProperty("cellular")
    private String cellular;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("additionalContactId")
    public Long getAdditionalContactId() {
        return additionalContactId;
    }

    @JsonProperty("additionalContactId")
    public void setAdditionalContactId(Long additionalContactId) {
        this.additionalContactId = additionalContactId;
    }

    @JsonProperty("customerName")
    public String getCustomerName() {
        return customerName;
    }

    @JsonProperty("customerName")
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(String position) {
        this.position = position;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("ext")
    public String getExt() {
        return ext;
    }

    @JsonProperty("ext")
    public void setExt(String ext) {
        this.ext = ext;
    }

    @JsonProperty("fax")
    public String getFax() {
        return fax;
    }

    @JsonProperty("fax")
    public void setFax(String fax) {
        this.fax = fax;
    }

    @JsonProperty("prefix")
    public String getPrefix() {
        return prefix;
    }

    @JsonProperty("prefix")
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @JsonProperty("cellular")
    public String getCellular() {
        return cellular;
    }

    @JsonProperty("cellular")
    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    
}
