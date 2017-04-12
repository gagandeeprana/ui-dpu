
package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AddtionalCarrierContact {

	private Long carrierId;
	private Long additionalContactId;

	private String customerName;

	private String position;

	private String phone;

	private String ext;

	private String fax;

	private String prefix;

	private String cellular;

	private String statusId;

	private String statusName;

	private String email;

	public AddtionalCarrierContact() {
		// TODO Auto-generated constructor stub
	}
	
	public AddtionalCarrierContact(String customerName, String position, String phone, String ext, String fax,
			String prefix, String cellular, String statusId, String email) {
		super();
		this.customerName = customerName;
		this.position = position;
		this.phone = phone;
		this.ext = ext;
		this.fax = fax;
		this.prefix = prefix;
		this.cellular = cellular;
		this.statusId = statusId;
		this.email = email;
	}

	public Long getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(Long carrierId) {
		this.carrierId = carrierId;
	}

	public Long getAdditionalContactId() {
		return additionalContactId;
	}

	public void setAdditionalContactId(Long additionalContactId) {
		this.additionalContactId = additionalContactId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}


	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
