package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "customerName", "position", "phone", "ext", "fax", "prefix", "cellular", "statusId", "email" })
public class AdditionalContact {

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
	@JsonProperty("statusId")
	private Long statusId;
	@JsonProperty("email")
	private String email;

	public Long companyId;
	public Long additionalContactId = null;

	public AdditionalContact(String additionalContact, String position2, String phone2, String fax2, String cellular2,
			String email2, String extension, String pager, Long status) {
		this.customerName = additionalContact;
		this.position = position2;
		this.phone = phone2;
		this.ext = extension;
		this.fax = fax2;
		this.prefix = pager;
		this.cellular = cellular2;
		this.statusId = status;
		this.email = email2;
	}

	public AdditionalContact() {

	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getAdditionalContactId() {
		return additionalContactId;
	}

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

	@JsonProperty("statusId")
	public Long getStatusId() {
		return statusId;
	}

	@JsonProperty("statusId")
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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
