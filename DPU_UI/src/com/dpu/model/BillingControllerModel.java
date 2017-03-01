
package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class BillingControllerModel {

	private String name;
	private String address;
	private String city;
	private String zip;
	private Long statusId;
	private String contact;
	private String position;
	private String email;
	private String cellular;
	private String phone;
	private String ext;
	private String fax;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
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

	public String getTollfree() {
		return tollfree;
	}

	public void setTollfree(String tollfree) {
		this.tollfree = tollfree;
	}

}
