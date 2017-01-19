package com.dpu.model;

import java.io.Serializable;

public class AdditionalContact implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String additionalContact;
	private String positionn;
	private String phoneNumber;
	private String ExtensionNumber;
	private String faxNumber;
	private String pagerNumber;
	private String cellularNumber;
	private String statuss;
	private String emaill;
	
	
	public String getAdditionalContact() {
		return additionalContact;
	}
	public void setAdditionalContact(String additionalContact) {
		this.additionalContact = additionalContact;
	}
	 
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getExtensionNumber() {
		return ExtensionNumber;
	}
	public void setExtensionNumber(String extensionNumber) {
		ExtensionNumber = extensionNumber;
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
	public String getPositionn() {
		return positionn;
	}
	public void setPositionn(String positionn) {
		this.positionn = positionn;
	}
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	public String getEmaill() {
		return emaill;
	}
	public void setEmaill(String emaill) {
		this.emaill = emaill;
	}
	 
	
	

}
