package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AddtionalCarrierContact {

	private Long additionalContactId;

	private String incCompany;

	private String policyNumber;

	private String incBroker;

	private String brokerContact;

	private String brokerPhone;

	private String ext;

	private Integer expiryDate;

	private String congCoverage;

	private String libilityCoverage;

	private String brokerFax;

	private String email;

	public AddtionalCarrierContact() {
		super();
	}

	public AddtionalCarrierContact(String incCompany, String policyNumber, String incBroker, String brokerContact,
			String brokerPhone, String ext, String congCoverage, String libilityCoverage, String brokerFax,
			String email) {
		super();
		this.incCompany = incCompany;
		this.policyNumber = policyNumber;
		this.incBroker = incBroker;
		this.brokerContact = brokerContact;
		this.brokerPhone = brokerPhone;
		this.ext = ext;
		this.congCoverage = congCoverage;
		this.libilityCoverage = libilityCoverage;
		this.brokerFax = brokerFax;
		this.email = email;
	}

	public Long getAdditionalContactId() {
		return additionalContactId;
	}

	public void setAdditionalContactId(Long additionalContactId) {
		this.additionalContactId = additionalContactId;
	}

	public String getIncCompany() {
		return incCompany;
	}

	public void setIncCompany(String incCompany) {
		this.incCompany = incCompany;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getIncBroker() {
		return incBroker;
	}

	public void setIncBroker(String incBroker) {
		this.incBroker = incBroker;
	}

	public String getBrokerContact() {
		return brokerContact;
	}

	public void setBrokerContact(String brokerContact) {
		this.brokerContact = brokerContact;
	}

	public String getBrokerPhone() {
		return brokerPhone;
	}

	public void setBrokerPhone(String brokerPhone) {
		this.brokerPhone = brokerPhone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Integer expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCongCoverage() {
		return congCoverage;
	}

	public void setCongCoverage(String congCoverage) {
		this.congCoverage = congCoverage;
	}

	public String getLibilityCoverage() {
		return libilityCoverage;
	}

	public void setLibilityCoverage(String libilityCoverage) {
		this.libilityCoverage = libilityCoverage;
	}

	public String getBrokerFax() {
		return brokerFax;
	}

	public void setBrokerFax(String brokerFax) {
		this.brokerFax = brokerFax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
