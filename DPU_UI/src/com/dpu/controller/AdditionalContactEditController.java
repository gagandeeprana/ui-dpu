package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.util.Validate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdditionalContactEditController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCancelAdditionalContact;

	@FXML
	private Button btnUpdateAdditionalContact;

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

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		try {
			CompanyEditController.selectedTabValue = 1;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Company");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CompanyEditController.selectedTabValue = 0;
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnUpdateAdditionalContactAction(ActionEvent event) {

		boolean result = validateAddCompanyScreen();
		if (result) {
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
				AdditionalContact bcm1 = new AdditionalContact(additionalContact, position, phone, fax, cellular, email,
						extension, pager, status);
				CompanyEditController.selectedTabValue = 1;
				// if(CompanyAddController.addEditIndex != -1){
				if (CompanyEditController.addAddtionalContact == 0) {
					CompanyEditController.listOfAdditionalContact.set(CompanyEditController.editIndex, bcm1);
				} else if (CompanyEditController.addAddtionalContact == 1) {
					CompanyEditController.listOfAdditionalContact.add(bcm1);
				}
				// }

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
						.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
				Parent root = (Parent) fxmlLoader.load();

				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("Add New Company");
				stage.setScene(new Scene(root));
				stage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
			CompanyEditController.selectedTabValue = 0;
			closeAddAdditionalContactScreen(btnUpdateAdditionalContact);
		}
	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@FXML
	void initialize() {
		assert btnCancelAdditionalContact != null : "fx:id=\"btnCancelAdditionalContact\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert btnUpdateAdditionalContact != null : "fx:id=\"btnSaveAdditionalContact\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert chkActualDelivery != null : "fx:id=\"chkActualDelivery\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert chkActualPickupDetails != null : "fx:id=\"chkActualPickupDetails\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert chkETADeliveryDetails != null : "fx:id=\"chkETADeliveryDetails\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert chkETAPickupDetails != null : "fx:id=\"chkETAPickupDetails\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert chkOrderConfirmation != null : "fx:id=\"chkOrderConfirmation\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert ddlStatus != null : "fx:id=\"ddlStatus\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtAdditionalContact != null : "fx:id=\"txtAdditionalContact\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtCellular != null : "fx:id=\"txtCellular\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtExtension != null : "fx:id=\"txtExtension\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtPager != null : "fx:id=\"txtPager\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
		assert txtPosition != null : "fx:id=\"txtPosition\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (CompanyEditController.addAddtionalContact != 1) {
			if (CompanyEditController.additionalContactModel != null) {

				txtAdditionalContact.setText(CompanyEditController.additionalContactModel.getCustomerName());
				txtPosition.setText(CompanyEditController.additionalContactModel.getPosition());
				txtExtension.setText(CompanyEditController.additionalContactModel.getExt());
				txtFax.setText(CompanyEditController.additionalContactModel.getFax());
				txtCellular.setText(CompanyEditController.additionalContactModel.getCellular());
				txtEmail.setText(CompanyEditController.additionalContactModel.getEmail());
				txtPager.setText(CompanyEditController.additionalContactModel.getPrefix());
				txtPhone.setText(CompanyEditController.additionalContactModel.getPhone());
				//ddlStatus.getSelectionModel().getSelectedItem();

			}
		}

	}

	// -------------------------------------------------------------------------------------------------

	@FXML
	Label lblAdditionalCOntact, lblPosition, lblPhone, lblFax, lblCellular, lblEmail, lblExt, lblPager, lblStatus;

	Validate validate = new Validate();

	private boolean validateAddCompanyScreen() {

		boolean result = true;
		String additionalContact = txtAdditionalContact.getText();
		String position = txtPosition.getText();
		String phone = txtPhone.getText();
		String fax = txtFax.getText();
		String city = txtCellular.getText();
		String email = txtEmail.getText();
		String ext = txtExtension.getText();
		String pager = txtPager.getText();
		String status = ddlStatus.getSelectionModel().getSelectedItem();

		boolean blnAdditionalContact = validate.validateEmptyness(additionalContact);
		if (!blnAdditionalContact) {
			result = false;
			txtAdditionalContact.setStyle("-fx-text-box-border: red;");
			lblAdditionalCOntact.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblAdditionalCOntact.setTextFill(Color.RED);

		}

		boolean blnPosition = validate.validateEmptyness(position);
		if (!blnPosition) {
			result = false;
			txtPosition.setStyle("-fx-text-box-border: red;");
			lblPosition.setVisible(true);
			// lblAddress.setText("Address is Mandatory");
			lblPosition.setTextFill(Color.RED);
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

		boolean blnCellular = validate.validateEmptyness(city);
		if (!blnCellular) {
			result = false;
			txtCellular.setStyle("-fx-text-box-border: red;");
			lblCellular.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblCellular.setTextFill(Color.RED);
		}

		boolean blnEmail = validate.validateEmptyness(email);
		if (!blnEmail) {
			result = false;
			txtEmail.setStyle("-fx-text-box-border: red;");
			lblEmail.setVisible(true);
			// lblFax.setText("Fax Number is Mandatory");
			lblEmail.setTextFill(Color.RED);
		}

		boolean blnExtension = validate.validateEmptyness(ext);
		if (!blnExtension) {
			result = false;
			txtExtension.setStyle("-fx-text-box-border: red;");
			lblExt.setVisible(true);
			// lblExt.setText("Fax Number is Mandatory");
			lblExt.setTextFill(Color.RED);
		}

		boolean blnPager = validate.validateEmptyness(pager);
		if (!blnPager) {
			result = false;
			txtPager.setStyle("-fx-text-box-border: red;");
			lblPager.setVisible(true);
			// lblPager.setText("Fax Number is Mandatory");
			lblPager.setTextFill(Color.RED);
		}

		boolean blnStatus = validate.validateEmptyness(status);
		if (!blnStatus) {
			result = false;
			ddlStatus.setStyle("-fx-text-box-border: red;");
			lblStatus.setVisible(true);
			// lblExt.setText("Fax Number is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}

		return result;
	}

	@FXML
	private void companyAdditionalContactKeyPressed() {

		String name = txtAdditionalContact.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblAdditionalCOntact.setTextFill(Color.BLACK);
			txtAdditionalContact.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtAdditionalContact.setStyle("-fx-focus-color: red;");
			txtAdditionalContact.requestFocus();
			lblAdditionalCOntact.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblAdditionalCOntact.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyPositionKeyPressed() {

		String name = txtPosition.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblPosition.setTextFill(Color.BLACK);
			txtPosition.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtPosition.setStyle("-fx-focus-color: red;");
			txtPosition.requestFocus();
			lblPosition.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			lblPosition.setTextFill(Color.RED);
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
			// lblFax.setText("Company Name is Mandatory");
			lblFax.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyCellularKeyPressed() {

		String name = txtCellular.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCellular.setTextFill(Color.BLACK);
			txtCellular.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCellular.setStyle("-fx-focus-color: red;");
			txtCellular.requestFocus();
			lblCellular.setVisible(true);
			// lblCellular.setText("Company Name is Mandatory");
			lblCellular.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyEmailKeyPressed() {

		String name = txtEmail.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblEmail.setTextFill(Color.BLACK);
			txtEmail.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtEmail.setStyle("-fx-focus-color: red;");
			txtEmail.requestFocus();
			lblEmail.setVisible(true);
			// lblEmail.setText("Company Name is Mandatory");
			lblEmail.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyExtKeyPressed() {

		String name = txtExtension.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblExt.setTextFill(Color.BLACK);
			txtExtension.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtExtension.setStyle("-fx-focus-color: red;");
			txtExtension.requestFocus();
			lblExt.setVisible(true);
			// lblExt.setText("Company Name is Mandatory");
			lblExt.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyPagerKeyPressed() {

		String name = txtPager.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblPager.setTextFill(Color.BLACK);
			txtPager.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtPager.setStyle("-fx-focus-color: red;");
			txtPager.requestFocus();
			lblPager.setVisible(true);
			// lblPager.setText("Company Name is Mandatory");
			lblPager.setTextFill(Color.RED);
		}
	}

	@FXML
	private void companyDdlStatusKeyPressed() {

		String textField = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblStatus.setText("");
			ddlStatus.setStyle("-fx-focus-color: skyblue;");
			lblStatus.setVisible(false);
		} else {
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("TextField is Mandatory");
			lblStatus.setTextFill(Color.RED);
		}
	}

}
