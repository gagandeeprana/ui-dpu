/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.AddtionalCarrierContact;

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

/**
 * @author jagvir
 *
 */
public class AdditionCaarierContactEditController implements Initializable {

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

	private void openAddCarrierScree() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit Outside Carrier");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			if (CarrierEditController.addAddtionalContact == 0) {
				CarrierEditController.listOfAdditionalContact.set(CarrierAddController.addEditIndex,
						addtionalCarrierContact);
			} else if (CarrierEditController.addAddtionalContact == 1) {
				CarrierEditController.listOfAdditionalContact.add(addtionalCarrierContact);
			}
			// CarrierAddController.listOfAdditionalContact.add(addtionalCarrierContact);
			openAddCarrierScree();

		} catch (Exception e) {
			e.printStackTrace();
		}

		closeEditAdditionalContactScreen(btnSaveAdditionalContact);
	}

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		openAddCarrierScree();
		closeEditAdditionalContactScreen(btnCancelAdditionalContact);
	}

	private void closeEditAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (CarrierEditController.additionalContactModel != null) {
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
