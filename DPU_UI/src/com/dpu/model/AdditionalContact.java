package com.dpu.model;

import java.util.List;

public class AdditionalContact {

	private String customerName;

	private String position;
	private String phone;
	private String ext;
	private String fax;
	private String prefix;
	private String cellular;
	private String statusId;
	private Long functionId;

	private String functionType;
	private String email;
	public Long companyId;
	private String function;
	private Type functionObj;
	public Long additionalContactId = null;
	private String status;
	 

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Status> statusList;

	public Long getFunctionId() {
		return functionId;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	 

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Type getFunctionObj() {
		return functionObj;
	}

	public void setFunctionObj(Type functionObj) {
		this.functionObj = functionObj;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public AdditionalContact(String additionalContact, String position2, String phone2, String fax2, String cellular2,
			String email2, String extension, String pager, String status, String function) {
		this.customerName = additionalContact;
		this.position = position2;
		this.phone = phone2;
		this.ext = extension;
		this.fax = fax2;
		this.prefix = pager;
		this.cellular = cellular2;
		this.statusId = status;
		this.email = email2;
		this.function = function;

	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
