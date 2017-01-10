package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Shipper {

	@JsonProperty(value = "shipper_id")
	private int shipperId;

	@JsonProperty(value = "company")
	private String company;

	@JsonProperty(value = "address")
	private String address;

	@JsonProperty(value = "unit")
	private String unit;

	@JsonProperty(value = "city")
	private String city;

	@JsonProperty(value = "province_state")
	private String provinceState;

	@JsonProperty(value = "postal_zip")
	private String postalZip;

	@JsonProperty(value = "status")
	private int status;

	@JsonProperty(value = "zone")
	private String zone;

	@JsonProperty(value = "lead_time")
	private String leadTime;

	@JsonProperty(value = "time_zone")
	private String timeZone;

	@JsonProperty(value = "contact")
	private String contact;

	@JsonProperty(value = "position")
	private String position;

	@JsonProperty(value = "phone")
	private String phone;

	@JsonProperty(value = "ext")
	private String ext;

	@JsonProperty(value = "fax")
	private String fax;

	@JsonProperty(value = "prefix")
	private String prefix;

	@JsonProperty(value = "toll_free")
	private String tollFree;

	@JsonProperty(value = "plant")
	private String plant;

	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "importer")
	private String importer;

	@JsonProperty(value = "internal_notes")
	private String internalNotes;

	@JsonProperty(value = "standard_notes")
	private String standardNotes;

	public int getShipperId() {
		return shipperId;
	}

	public void setShipperId(int shipperId) {
		this.shipperId = shipperId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getPostalZip() {
		return postalZip;
	}

	public void setPostalZip(String postalZip) {
		this.postalZip = postalZip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTollFree() {
		return tollFree;
	}

	public void setTollFree(String tollFree) {
		this.tollFree = tollFree;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImporter() {
		return importer;
	}

	public void setImporter(String importer) {
		this.importer = importer;
	}

	public String getInternalNotes() {
		return internalNotes;
	}

	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}

	public String getStandardNotes() {
		return standardNotes;
	}

	public void setStandardNotes(String standardNotes) {
		this.standardNotes = standardNotes;
	}
}