
package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.fasterxml.jackson.annotation.JsonInclude;

import javafx.beans.property.SimpleStringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

		"company", "address", "city", "phone", "contact", "zip", "fax"

})
public class BillingControllerModel {

	private SimpleStringProperty company = new SimpleStringProperty();
	private SimpleStringProperty address = new SimpleStringProperty();
	private SimpleStringProperty city = new SimpleStringProperty();
	private SimpleStringProperty phone = new SimpleStringProperty();
	private SimpleStringProperty contact = new SimpleStringProperty();
	private SimpleStringProperty zip = new SimpleStringProperty();
	private SimpleStringProperty fax = new SimpleStringProperty();

	public BillingControllerModel() {

	}

	public BillingControllerModel(String cmpny, String addr, String cty, String phn, String cnt, String zp, String fx) {
		this.company = new SimpleStringProperty(cmpny);
		this.address = new SimpleStringProperty(addr);
		this.city = new SimpleStringProperty(cty);
		this.phone = new SimpleStringProperty(phn);
		this.contact = new SimpleStringProperty(cnt);
		this.zip = new SimpleStringProperty(zp);
		this.fax = new SimpleStringProperty(fx);

	}

	public String getCompany() {
		return company.get();
	}

	public void setCompany(String fName) {
		company.set(fName);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String fName) {
		address.set(fName);
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String fName) {
		city.set(fName);
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String fName) {
		phone.set(fName);
	}

	public String getContact() {
		return contact.get();
	}

	public void setContact(String fName) {
		contact.set(fName);
	}

	public String getZip() {
		return zip.get();
	}

	public void setZip(String fName) {
		zip.set(fName);
	}

	public String getFax() {
		return fax.get();
	}

	public void setFax(String fName) {
		fax.set(fName);
	}

}
