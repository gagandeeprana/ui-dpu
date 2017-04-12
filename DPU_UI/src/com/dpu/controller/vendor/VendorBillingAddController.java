package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.VendorBillingLocation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VendorBillingAddController implements Initializable {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	

	@FXML
	private Button btnCancelBillingLocation;

	@FXML
	private Button btnSaveBillingLocation;

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
	void btnSaveBillingLocationAction(ActionEvent event) {
		try {
 
			String company = txtCompany.getText();
			String address = txtAddress.getText();
			String city = txtCity.getText();
			String phone = txtPhone.getText();
			String contact = txtContact.getText();
			String zip = txtZip.getText();
			String fax = txtFax.getText();
			Long statusId = Long.parseLong("1");
			VendorBillingLocation bcm1 = new VendorBillingLocation(company, address, city, phone, contact, zip, fax,statusId);

			if (VendorAddController.add == 0) {
				VendorEditController.listOfBilling.set(VendorAddController.addEditIndex, bcm1);
			} else if(VendorAddController.add == 1){
				VendorEditController.listOfBilling.add(bcm1);
			}
			/*if (CompanyAddController.add == 0) {
				CompanyAddController.listOfBilling.set(CompanyAddController.addEditIndex, bcm1);
			} else if(CompanyAddController.add == 1){
				CompanyAddController.listOfBilling.add(bcm1);
			}*/

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Company");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		closeAddBillingScreen(btnSaveBillingLocation);

	}

	private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	
	
	@FXML
	void btnCancelBillingLocationAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Company");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeAddBillingScreen(btnCancelBillingLocation);
	}
	
//	@FXML
//	void initialize() {
//		assert btnSaveBillingLocation != null : "fx:id=\"btnSaveBillingLocation\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtCompany != null : "fx:id=\"txtCompany\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtContact != null : "fx:id=\"txtContact\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//		assert txtZip != null : "fx:id=\"txtZip\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
//
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 
		if (VendorAddController.add != 1) {
			if (VendorAddController.VendorBillingLocation != null) {
				txtCompany.setText(VendorAddController.VendorBillingLocation.getName());
				txtAddress.setText(VendorAddController.VendorBillingLocation.getAddress());
				txtCity.setText(VendorAddController.VendorBillingLocation.getCity());
				txtPhone.setText(VendorAddController.VendorBillingLocation.getPhone());
				txtContact.setText(VendorAddController.VendorBillingLocation.getPhone());
				txtZip.setText(VendorAddController.VendorBillingLocation.getZip());
				txtFax.setText(VendorAddController.VendorBillingLocation.getFax());
			}
		}

	}
}