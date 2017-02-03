package com.dpu.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javafx.beans.property.SimpleStringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({

		"additionalContact", "position", "phone", "fax", "cellular", "email", "extension", "pager", "status"

})
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long additionalContactId ;
	private Long companyId;
	private SimpleStringProperty additionalContact = new SimpleStringProperty();
	private SimpleStringProperty position = new SimpleStringProperty();
	private SimpleStringProperty phone = new SimpleStringProperty();
	private SimpleStringProperty fax = new SimpleStringProperty();
	private SimpleStringProperty cellular = new SimpleStringProperty();
	private SimpleStringProperty email = new SimpleStringProperty();
	private SimpleStringProperty extension = new SimpleStringProperty();
	private SimpleStringProperty pager = new SimpleStringProperty();
	private SimpleStringProperty status = new SimpleStringProperty();

	public AdditionalContact() {
		// 0-param constructor
	}

	public AdditionalContact(String additionalContact, String position, String phone, String fax, String cellular,
			String email, String extension, String pager, String status) {
		this.additionalContact = new SimpleStringProperty(additionalContact);
		this.position = new SimpleStringProperty(position);
		this.phone = new SimpleStringProperty(phone);
		this.fax = new SimpleStringProperty(fax);
		this.cellular = new SimpleStringProperty(cellular);
		this.email = new SimpleStringProperty(email);
		this.extension = new SimpleStringProperty(extension);
		this.pager = new SimpleStringProperty(pager);
		this.status = new SimpleStringProperty(status);

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

	public  String  getAdditionalContact() {
		return additionalContact.get();
	}

	public void setAdditionalContact( String  additionalContac ) {
		additionalContact.set(additionalContac);
	}

	public  String  getPosition() {
		return position.get();
	}

	public void setPosition( String  positionn) {
		 position.set(positionn); 
	}

	public  String  getPhone() {
		return phone.get();
	}

	public void setPhone( String  phonee) {
		 phone.set(phonee); 
	}

	public  String  getFax() {
		return fax.get();
	}

	public void setFax( String  faxx) {
		 fax.set(faxx);
	}

	public  String  getCellular() {
		return cellular.get();
	}

	public void setCellular( String  cellularr) {
		 cellular.set(cellularr);
	}

	public  String getEmail() {
		return email.get();
	}

	public void setEmail( String  emaill) {
		email.set(emaill);
	}

	public  String  getExtension() {
		return extension.get();
	}

	public void setExtension( String  extensionn) {
		 extension.set(extensionn);
	}

	public  String  getPager() {
		return pager.get();
	}

	public void setPager( String pagerr) {
		 pager.set(pagerr);
	}

	public  String   getStatus() {
		return status.get();
	}

	public void setStatus( String  statuss) {
		 status.set(statuss);
	}

}
