package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.AddtionalCarrierContact;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdditionCaarierContactAddController implements Initializable {

	@FXML
	private Button btnCancelAdditionalContact;

	@FXML
	private Button btnSaveAdditionalContact;

	@FXML
	private TextField txtIncCompany;

	@FXML
	private TextField txtPolicyNumber;

	@FXML
	private TextField txtIncBroker;

	@FXML
	private TextField txtBrokerContact;

	@FXML
	private TextField txtBrokerPhone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtExt;

	@FXML
	private TextField txtBrokerFax;

	@FXML
	private TextField txtCongCoverage;

	@FXML
	private TextField txtLibilityCoverage;

	// @FXML
	// void initialize() {
	// assert btnCancelAdditionalContact != null :
	// "fx:id=\"btnCancelAdditionalContact\" was not injected: check your FXML
	// file 'AdditionalContactAddScreen.fxml'.";
	// assert btnSaveAdditionalContact != null :
	// "fx:id=\"btnSaveAdditionalContact\" was not injected: check your FXML
	// file 'AdditionalContactAddScreen.fxml'.";
	// assert ddlStatus != null : "fx:id=\"ddlStatus\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtAdditionalContact != null : "fx:id=\"txtAdditionalContact\" was
	// not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtCellular != null : "fx:id=\"txtCellular\" was not injected:
	// check your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtExtension != null : "fx:id=\"txtExtension\" was not injected:
	// check your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your
	// FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtPager != null : "fx:id=\"txtPager\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check
	// your FXML file 'AdditionalContactAddScreen.fxml'.";
	// assert txtPosition != null : "fx:id=\"txtPosition\" was not injected:
	// check your FXML file 'AdditionalContactAddScreen.fxml'.";
	//
	// }

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		openAddCarrierScree();
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnSaveAdditionalContactAction(ActionEvent event) {
		try {

			String brokerContact = txtBrokerContact.getText();
			String brokerFax = txtBrokerFax.getText();
			String brokerPhone = txtBrokerPhone.getText();
			String congCoverage = txtCongCoverage.getText();
			String email = txtEmail.getText();
			String ext = txtExt.getText();
			String incBroker = txtIncBroker.getText();
			String company = txtIncCompany.getText();
			String libilityCoverage = txtLibilityCoverage.getText();
			String policyNumber = txtPolicyNumber.getText();

			AddtionalCarrierContact addtionalCarrierContact = new AddtionalCarrierContact(company, policyNumber,
					incBroker, brokerContact, brokerPhone, ext, congCoverage, libilityCoverage, brokerFax, email);
			if (CarrierAddController.addAddtionalContact == 0) {
				CarrierAddController.listOfAdditionalContact.set(CarrierAddController.addEditIndex,
						addtionalCarrierContact);
			} else if (CarrierAddController.addAddtionalContact == 1) {
				CarrierAddController.listOfAdditionalContact.add(addtionalCarrierContact);
			}
			// CarrierAddController.listOfAdditionalContact.add(addtionalCarrierContact);
			openAddCarrierScree();

		} catch (Exception e) {
			e.printStackTrace();
		}

		closeAddAdditionalContactScreen(btnSaveAdditionalContact);
	}

	private void openAddCarrierScree() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Carrier");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (CarrierAddController.addAddtionalContact != 1) {
			if (CarrierAddController.additionalContactModel != null) {
				txtBrokerContact.setText(CarrierAddController.additionalContactModel.getBrokerContact());
				txtBrokerFax.setText(CarrierAddController.additionalContactModel.getBrokerFax());
				txtBrokerPhone.setText(CarrierAddController.additionalContactModel.getBrokerPhone());
				txtCongCoverage.setText(CarrierAddController.additionalContactModel.getCongCoverage());
				txtEmail.setText(CarrierAddController.additionalContactModel.getEmail());
				txtExt.setText(CarrierAddController.additionalContactModel.getExt());
				txtIncBroker.setText(CarrierAddController.additionalContactModel.getIncBroker());
				txtIncCompany.setText(CarrierAddController.additionalContactModel.getIncCompany());
				txtLibilityCoverage.setText(CarrierAddController.additionalContactModel.getLibilityCoverage());
				txtPolicyNumber.setText(CarrierAddController.additionalContactModel.getPolicyNumber());
			}

		}
	}

}
