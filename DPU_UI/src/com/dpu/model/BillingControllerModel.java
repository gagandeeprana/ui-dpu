
package com.dpu.model;

public class BillingControllerModel {

	private String name;
	private String address;
	private String city;
	private String phone;
	private String contact;
	private String zip;
	private String fax;

	public Long companyId;
	public Long billingLocationId = null;

	public BillingControllerModel(String company, String address, String city, String phone, String contact, String zip,
			String fax) {
		this.name = company;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.contact = contact;
		this.zip = zip;
		this.fax = fax;

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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

}
