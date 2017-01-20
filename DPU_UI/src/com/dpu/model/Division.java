package com.dpu.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Division {

	// @JsonProperty(value = "division_id")
	private int divisionId;

	// @JsonProperty(value = "division_code")
	private String divisionCode;

	// @JsonProperty(value = "division_name")
	private String divisionName;

	// @JsonProperty(value = "federal")
	private String fedral;

	// @JsonProperty(value = "provincial")
	private String provincial;

	// @JsonProperty(value = "SCAC")
	private String SCAC;

	// @JsonProperty(value = "carrier_code")
	private String carrierCode;

	// @JsonProperty(value = "contact_prefix")
	private String contractPrefix;

	// @JsonProperty(value = "invoice_prefix")
	private String invoicePrefix;

	// @JsonProperty(value = "created_on")
	private Date createdOn;

	// @JsonProperty(value = "created_by")
	private int createdBy;

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getFedral() {
		return fedral;
	}

	public void setFedral(String fedral) {
		this.fedral = fedral;
	}

	public String getProvincial() {
		return provincial;
	}

	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}

	public String getSCAC() {
		return SCAC;
	}

	public void setSCAC(String sCAC) {
		SCAC = sCAC;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getContractPrefix() {
		return contractPrefix;
	}

	public void setContractPrefix(String contractPrefix) {
		this.contractPrefix = contractPrefix;
	}

	public String getInvoicePrefix() {
		return invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

}
