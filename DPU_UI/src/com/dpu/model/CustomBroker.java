package com.dpu.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBroker {

	private Long customBrokerId;
	private String customBroker;
	private String contactName;
	private String phoneNo;
	private String extension;
	private String faxNo;
	private Integer emailAddress;
	private Date webSite;

	public Long getCustomBrokerId() {
		return customBrokerId;
	}

	public void setCustomBrokerId(Long customBrokerId) {
		this.customBrokerId = customBrokerId;
	}

	public String getCustomBroker() {
		return customBroker;
	}

	public void setCustomBroker(String customBroker) {
		this.customBroker = customBroker;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public Integer getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(Integer emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getWebSite() {
		return webSite;
	}

	public void setWebSite(Date webSite) {
		this.webSite = webSite;
	}

}
