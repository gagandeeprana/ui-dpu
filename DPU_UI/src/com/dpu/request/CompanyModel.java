
package com.dpu.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
 

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include= Inclusion.NON_NULL)
@JsonPropertyOrder({
	"companyId",
    "name",
    "address",
    "unitNo",
    "city",
    "provinceState",
    "zip",
    "email",
    "website",
    "contact",
    "position",
    "phone",
    "ext",
    "fax",
    "companyPrefix",
    "tollfree",
    "cellular",
    "pager",
    "afterHours",
    "billingLocations",
    "additionalContacts"
})
public class CompanyModel {

	@JsonProperty("companyId")
	private String companyId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("unitNo")
    private String unitNo;
    @JsonProperty("city")
    private String city;
    @JsonProperty("provinceState")
    private String provinceState;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("email")
    private String email;
    @JsonProperty("website")
    private String website;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("position")
    private String position;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("ext")
    private String ext;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("companyPrefix")
    private String companyPrefix;
    @JsonProperty("tollfree")
    private String tollfree;
    @JsonProperty("cellular")
    private String cellular;
    @JsonProperty("pager")
    private String pager;
    @JsonProperty("afterHours")
    private String afterHours;
    
    @JsonProperty("billingLocations")
    private List<BillingLocation> billingLocations = null;
    @JsonProperty("additionalContacts")
    private List<AdditionalContact> additionalContacts = null;
    
    
    
    
    @JsonProperty("companyId")
    public String getCompanyId() {
		return companyId;
	}

    @JsonProperty("companyId")
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("unitNo")
    public String getUnitNo() {
        return unitNo;
    }

    @JsonProperty("unitNo")
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("provinceState")
    public String getProvinceState() {
        return provinceState;
    }

    @JsonProperty("provinceState")
    public void setProvinceState(String provinceState) {
        this.provinceState = provinceState;
    }

    @JsonProperty("zip")
    public String getZip() {
        return zip;
    }

    @JsonProperty("zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonProperty("contact")
    public String getContact() {
        return contact;
    }

    @JsonProperty("contact")
    public void setContact(String contact) {
        this.contact = contact;
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

    @JsonProperty("companyPrefix")
    public String getCompanyPrefix() {
        return companyPrefix;
    }

    @JsonProperty("companyPrefix")
    public void setCompanyPrefix(String companyPrefix) {
        this.companyPrefix = companyPrefix;
    }

    @JsonProperty("tollfree")
    public String getTollfree() {
        return tollfree;
    }

    @JsonProperty("tollfree")
    public void setTollfree(String tollfree) {
        this.tollfree = tollfree;
    }

    @JsonProperty("cellular")
    public String getCellular() {
        return cellular;
    }

    @JsonProperty("cellular")
    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    @JsonProperty("pager")
    public String getPager() {
        return pager;
    }

    @JsonProperty("pager")
    public void setPager(String pager) {
        this.pager = pager;
    }

    @JsonProperty("afterHours")
    public String getAfterHours() {
        return afterHours;
    }

    @JsonProperty("afterHours")
    public void setAfterHours(String afterHours) {
        this.afterHours = afterHours;
    }

    @JsonProperty("billingLocations")
    public List<BillingLocation> getBillingLocations() {
        return billingLocations;
    }

    @JsonProperty("billingLocations")
    public void setBillingLocations(List<BillingLocation> billingLocations) {
        this.billingLocations = billingLocations;
    }

    @JsonProperty("additionalContacts")
    public List<AdditionalContact> getAdditionalContacts() {
        return additionalContacts;
    }

    @JsonProperty("additionalContacts")
    public void setAdditionalContacts(List<AdditionalContact> additionalContacts) {
        this.additionalContacts = additionalContacts;
    }

   

}
