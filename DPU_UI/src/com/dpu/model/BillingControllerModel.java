
package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javafx.beans.property.SimpleStringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "address", "city", "zip", "statusId", "contact", "position", "email", "cellular", "phone",
		"ext", "fax", "tollfree" })
public class BillingControllerModel {

	@JsonProperty("name")
	private String name;
	@JsonProperty("address")
	private String address;
	@JsonProperty("city")
	private String city;
	@JsonProperty("zip")
	private String zip;
	@JsonProperty("statusId")
	private Long statusId;
	@JsonProperty("contact")
	private String contact;
	@JsonProperty("position")
	private String position;
	@JsonProperty("email")
	private String email;
	@JsonProperty("cellular")
	private String cellular;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("ext")
	private String ext;
	@JsonProperty("fax")
	private String fax;
	@JsonProperty("tollfree")
	private String tollfree;

	public Long companyId;
	public Long billingLocationId = null;

	public BillingControllerModel(String company, String address2, String city2, String phone2, String contact2,
			String zip2, String fax2, Long statusId2) {
		this.name = company;
		this.address = address2;
		this.city = city2;
		this.phone = phone2;
		this.contact = contact2;
		this.zip = zip2;
		this.fax = fax2;
		this.statusId = statusId2;

	}

	public BillingControllerModel() {

	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getBillingLocationId() {
		return billingLocationId;
	}

	public void setBillingLocationId(Long billingLocationId) {
		this.billingLocationId = billingLocationId;
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

	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty("zip")
	public String getZip() {
		return zip;
	}

	@JsonProperty("zip")
	public void setZip(String zip) {
		this.zip = zip;
	}

	@JsonProperty("statusId")
	public Long getStatusId() {
		return statusId;
	}

	@JsonProperty("statusId")
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("cellular")
	public String getCellular() {
		return cellular;
	}

	@JsonProperty("cellular")
	public void setCellular(String cellular) {
		this.cellular = cellular;
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

	@JsonProperty("tollfree")
	public String getTollfree() {
		return tollfree;
	}

	@JsonProperty("tollfree")
	public void setTollfree(String tollfree) {
		this.tollfree = tollfree;
	}

}
