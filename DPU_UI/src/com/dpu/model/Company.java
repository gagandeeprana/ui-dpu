package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

 
public class Company {

    private String name;
    private String address;
    private String unitNo;
    private String city;
    private String provinceState;
    private String zip;
    private String email;
    private String website;
    private String contact;
    private String position;
    private String phone;
    private String ext;
    private String fax;
    private String companyPrefix;
    private String tollfree;
    private String cellular;
    private String pager;
    private String afterHours;
    private List<BillingControllerModel> billingLocations = null;
    private List<AdditionalContact> additionalContacts = null;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceState() {
        return provinceState;
    }

    public void setProvinceState(String provinceState) {
        this.provinceState = provinceState;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompanyPrefix() {
        return companyPrefix;
    }

    public void setCompanyPrefix(String companyPrefix) {
        this.companyPrefix = companyPrefix;
    }

    public String getTollfree() {
        return tollfree;
    }

    public void setTollfree(String tollfree) {
        this.tollfree = tollfree;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    public String getAfterHours() {
        return afterHours;
    }

    public void setAfterHours(String afterHours) {
        this.afterHours = afterHours;
    }

    public List<BillingControllerModel> getBillingLocations() {
        return billingLocations;
    }

    public void setBillingLocations(List<BillingControllerModel> billingLocations) {
        this.billingLocations = billingLocations;
    }

    public List<AdditionalContact> getAdditionalContacts() {
        return additionalContacts;
    }

    public void setAdditionalContacts(List<AdditionalContact> additionalContacts) {
        this.additionalContacts = additionalContacts;
    }


}
