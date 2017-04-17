package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.BillingControllerModel;
import com.dpu.util.Validate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BillingEditController implements Initializable {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCancelBillingLocation;

	@FXML
	private Button btnUpdateBillingLocation;

	@FXML
	private TextField txtAddress;

	@FXML
	private TextField txtCity;

	@FXML
	private TextField txtCompany;

	@FXML
	private TextField txtContact;

	@FXML
	private TextField txtFax;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtZip;

	@FXML
	void btnUpdateBillingLocationAction(ActionEvent event) {

		boolean result = validateAddCompanyScreen();
		if (result) {
			try {

				String company = txtCompany.getText();
				String address = txtAddress.getText();
				String city = txtCity.getText();
				String phone = txtPhone.getText();
				String contact = txtContact.getText();
				String zip = txtZip.getText();
				String fax = txtFax.getText();
				Long statusId = Long.parseLong("1");
				BillingControllerModel bcm1 = new BillingControllerModel(company, address, city, phone, contact, zip,
						fax, statusId);

				if (CompanyEditController.addBillingLocation == 0) {
					if (CompanyEditController.billingLocationIdPri != 0l
							|| CompanyEditController.billingLocationIdPri != null)
						bcm1.setBillingLocationId(CompanyEditController.billingLocationIdPri);
					CompanyEditController.listOfBilling.set(CompanyEditController.editIndex, bcm1);
					CompanyEditController.billingLocationIdPri = 0l;

				} else if (CompanyEditController.addBillingLocation == 1) {
					CompanyEditController.listOfBilling.add(bcm1);

				}

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
						.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("Update Company");
				stage.setScene(new Scene(root));
				stage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
			closeAddBillingScreen(btnUpdateBillingLocation);
		}

	}

	private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@FXML
	void btnCancelBillingLocationAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Update Company");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeAddBillingScreen(btnCancelBillingLocation);
	}

	@FXML
	void initialize() {
		assert btnUpdateBillingLocation != null : "fx:id=\"btnSaveBillingLocation\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtCompany != null : "fx:id=\"txtCompany\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtContact != null : "fx:id=\"txtContact\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
		assert txtZip != null : "fx:id=\"txtZip\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";

	}

	public void initialize(URL location, ResourceBundle resources) {

		txtPhone.addEventFilter(KeyEvent.KEY_TYPED, Validate.numeric_Validation(10));
		txtCompany.addEventFilter(KeyEvent.KEY_TYPED, Validate.letter_Validation(10));

		if (CompanyEditController.addBillingLocation != 1) {
			if (CompanyEditController.billingControllerModel != null) {

				if (CompanyAddController.billingControllerModel.getName() != null) {
					txtCompany.setText(CompanyAddController.billingControllerModel.getName());
					txtPhone.setText(CompanyAddController.billingControllerModel.getPhone());
				}
				txtAddress.setText(CompanyEditController.billingControllerModel.getAddress());
				txtCity.setText(CompanyEditController.billingControllerModel.getCity());
				txtContact.setText(CompanyEditController.billingControllerModel.getPhone());
				txtZip.setText(CompanyEditController.billingControllerModel.getZip());
				txtFax.setText(CompanyEditController.billingControllerModel.getFax());
			}
		}

	}

	@FXML
	Label lblCompany, lblAddress, lblCity, lblZip, lblFax, lblPhone, lblContact;

	Validate validate = new Validate();

	@FXML
	private void companyNameKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblCompany.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyAdderssKeyPressed() {

		String name = txtAddress.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblAddress.setTextFill(Color.BLACK);
			txtAddress.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtAddress.setStyle("-fx-focus-color: red;");
			txtAddress.requestFocus();
			lblAddress.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblAddress.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyCityKeyPressed() {

		String name = txtCity.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCity.setTextFill(Color.BLACK);
			txtCity.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCity.setStyle("-fx-focus-color: red;");
			txtCity.requestFocus();
			lblCity.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblCity.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyZipKeyPressed() {

		String name = txtZip.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblZip.setTextFill(Color.BLACK);
			txtZip.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtZip.setStyle("-fx-focus-color: red;");
			txtZip.requestFocus();
			lblZip.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblZip.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyFaxKeyPressed() {

		String name = txtFax.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblFax.setTextFill(Color.BLACK);
			txtFax.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtFax.setStyle("-fx-focus-color: red;");
			txtFax.requestFocus();
			lblFax.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblFax.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyPhoneKeyPressed() {

		String name = txtPhone.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblPhone.setTextFill(Color.BLACK);
			txtPhone.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtPhone.setStyle("-fx-focus-color: red;");
			txtPhone.requestFocus();
			lblPhone.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblPhone.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyContactKeyPressed() {

		String name = txtContact.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblContact.setTextFill(Color.BLACK);
			txtContact.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtContact.setStyle("-fx-focus-color: red;");
			txtContact.requestFocus();
			lblContact.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblContact.setTextFill(Color.RED);
		}
	}

	private boolean validateAddCompanyScreen() {

		boolean result = true;
		String customerName = txtCompany.getText();
		String address = txtAddress.getText();
		String phone = txtPhone.getText();
		String fax = txtFax.getText();
		String city = txtCity.getText();
		String zip = txtZip.getText();
		String contact = txtContact.getText();

		boolean blnName = validate.validateEmptyness(customerName);
		if (!blnName) {
			result = false;
			txtCompany.setStyle("-fx-text-box-border: red;");
			lblCompany.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblCompany.setTextFill(Color.RED);

		}

		boolean blnAddress = validate.validateEmptyness(address);
		if (!blnAddress) {
			result = false;
			txtAddress.setStyle("-fx-text-box-border: red;");
			lblAddress.setVisible(true);
			// lblAddress.setText("Address is Mandatory");
			lblAddress.setTextFill(Color.RED);
		}

		boolean blnPhone = validate.validateEmptyness(phone);
		if (!blnPhone) {
			result = false;
			txtPhone.setStyle("-fx-text-box-border: red;");
			lblPhone.setVisible(true);
			// lblPhone.setText("Phone Number is Mandatory");
			lblPhone.setTextFill(Color.RED);
		}

		boolean blnFax = validate.validateEmptyness(fax);
		if (!blnFax) {
			result = false;
			txtFax.setStyle("-fx-text-box-border: red;");
			lblFax.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblFax.setTextFill(Color.RED);
		}

		boolean blnCity = validate.validateEmptyness(city);
		if (!blnCity) {
			result = false;
			txtCity.setStyle("-fx-text-box-border: red;");
			lblCity.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblCity.setTextFill(Color.RED);
		}

		boolean blnZip = validate.validateEmptyness(zip);
		if (!blnZip) {
			result = false;
			txtZip.setStyle("-fx-text-box-border: red;");
			lblZip.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblZip.setTextFill(Color.RED);
		}

		boolean blnContact = validate.validateEmptyness(contact);
		if (!blnContact) {
			result = false;
			txtContact.setStyle("-fx-text-box-border: red;");
			lblContact.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblContact.setTextFill(Color.RED);
		}

		return result;

	}

}