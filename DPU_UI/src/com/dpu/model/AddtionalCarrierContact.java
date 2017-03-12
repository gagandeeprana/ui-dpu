package com.dpu.model;

public class AddtionalCarrierContact {

	private String additionalContact;
	private String position;
	private String phoneNumber;
	private String extNo;
	private String faxNumber;
	private String pagerNumber;
	private String cellularNumber;
	private String statusId;
	private String email;
	public Long carrierId;
	public Long additionalContactId = null;

	public AddtionalCarrierContact(String additionalContact, String position, String phoneNumber, String extNo,
			String faxNumber, String pagerNumber, String cellularNumber, String statusId, String email) {
		super();
		this.additionalContact = additionalContact;
		this.position = position;
		this.phoneNumber = phoneNumber;
		this.extNo = extNo;
		this.faxNumber = faxNumber;
		this.pagerNumber = pagerNumber;
		this.cellularNumber = cellularNumber;
		this.statusId = statusId;
		this.email = email;

	}

	public String getAdditionalContact() {
		return additionalContact;
	}

	public void setAdditionalContact(String additionalContact) {
		this.additionalContact = additionalContact;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExtNo() {
		return extNo;
	}

	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getPagerNumber() {
		return pagerNumber;
	}

	public void setPagerNumber(String pagerNumber) {
		this.pagerNumber = pagerNumber;
	}

	public String getCellularNumber() {
		return cellularNumber;
	}

	public void setCellularNumber(String cellularNumber) {
		this.cellularNumber = cellularNumber;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
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

}
