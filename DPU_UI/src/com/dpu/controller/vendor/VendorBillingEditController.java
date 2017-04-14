package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.BillingControllerModel;

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

public class VendorBillingEditController implements Initializable {
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
		try {

			String company = txtCompany.getText();
			String address = txtAddress.getText();
			String city = txtCity.getText();
			String phone = txtPhone.getText();
			String contact = txtContact.getText();
			String zip = txtZip.getText();
			String fax = txtFax.getText();
			Long statusId =Long.parseLong("1");
			BillingControllerModel bcm1 = new BillingControllerModel(company, address, city, phone, contact, zip, fax,statusId);

//			if (CompanyEditController.addBillingLocation == 0 ) {
//				if (CompanyEditController.billingLocationIdPri != 0l
//						|| CompanyEditController.billingLocationIdPri != null)
//				bcm1.setBillingLocationId(CompanyEditController.billingLocationIdPri);
//				CompanyEditController.listOfBilling.set(CompanyEditController.editIndex, bcm1);
//				CompanyEditController.billingLocationIdPri = 0l;
//
//			} else if (CompanyEditController.addBillingLocation == 1) {
//				CompanyEditController.listOfBilling.add(bcm1);
//				
//			}

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

	private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@FXML
	void btnCancelBillingLocationAction(ActionEvent event) {
//		try {
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
//					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
//			Parent root = (Parent) fxmlLoader.load();
//
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.setTitle("Update Company");
//			stage.setScene(new Scene(root));
//			stage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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

//		if (CompanyEditController.addBillingLocation != 1) {
//			if (CompanyEditController.billingControllerModel != null) {
//				txtCompany.setText(CompanyEditController.billingControllerModel.getName());
//				txtAddress.setText(CompanyEditController.billingControllerModel.getAddress());
//				txtCity.setText(CompanyEditController.billingControllerModel.getCity());
//				txtPhone.setText(CompanyEditController.billingControllerModel.getPhone());
//				txtContact.setText(CompanyEditController.billingControllerModel.getPhone());
//				txtZip.setText(CompanyEditController.billingControllerModel.getZip());
//				txtFax.setText(CompanyEditController.billingControllerModel.getFax());
//			}
//		}

	}
}