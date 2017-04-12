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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
	private CheckBox chkActualDelivery;

	@FXML
	private CheckBox chkActualPickupDetails;

	@FXML
	private CheckBox chkETADeliveryDetails;

	@FXML
	private CheckBox chkETAPickupDetails;

	@FXML
	private CheckBox chkOrderConfirmation;

	@FXML
	private ComboBox<String> ddlStatus;

	@FXML
	private TextField txtAdditionalContact;

	@FXML
	private TextField txtCellular;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtExtension;

	@FXML
	private TextField txtFax;

	@FXML
	private TextField txtPager;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtPosition;

	private void openAddCarrierScree() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_Edit_SCREEN));
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

			String additionalContact = txtAdditionalContact.getText();
			String position = txtPosition.getText();
			String phone = txtPhone.getText();
			String extension = txtExtension.getText();
			String fax = txtFax.getText();
			String pager = txtPager.getText();
			String cellular = txtCellular.getText();
			String status = ddlStatus.getSelectionModel().getSelectedItem();
			String email = txtEmail.getText();

			AddtionalCarrierContact addtionalCarrierContact = new AddtionalCarrierContact(additionalContact, position,
					phone, extension, fax, pager, cellular, status, email);
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
		if (CarrierEditController.addAddtionalContact != 1) {
			if (CarrierEditController.additionalContactModel != null) {
				txtAdditionalContact.setText(CarrierAddController.additionalContactModel.getCustomerName());
				txtPosition.setText(CarrierAddController.additionalContactModel.getPosition());
				txtExtension.setText(CarrierAddController.additionalContactModel.getExt());
				txtFax.setText(CarrierAddController.additionalContactModel.getFax());
				txtCellular.setText(CarrierAddController.additionalContactModel.getCellular());
				txtEmail.setText(CarrierAddController.additionalContactModel.getEmail());
				txtPager.setText(CarrierAddController.additionalContactModel.getPrefix());
				txtPhone.setText(CarrierAddController.additionalContactModel.getPhone());
			}
		}

	}

}
