package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.Status;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AdditionalContactAddController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

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

	@FXML
	void btnCancelAdditionalContactAction(ActionEvent event) {
		 
		CompanyAddController.selectedTabValue = 0;
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnSaveAdditionalContactAction(ActionEvent event) {

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
				CompanyAddController.selectedTabValue = 1;
				// if(CompanyAddController.addEditIndex != -1){
				if (CompanyAddController.addAddtionalContact == 0) {
					CompanyEditController.listOfAdditionalContact.set(CompanyAddController.addEditIndex, bcm1);
				} else if (CompanyAddController.addAddtionalContact == 1) {
					CompanyEditController.listOfAdditionalContact.add(bcm1);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
			CompanyAddController.fetchAdditionalContactsUsingDuplicate();
			CompanyAddController.selectedTabValue = 0;
			closeAddAdditionalContactScreen(btnSaveAdditionalContact);
		}
	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
		if (CompanyAddController.addAddtionalContact != 1) {
			if (CompanyAddController.additionalContactModel != null) {

				txtAdditionalContact.setText(CompanyAddController.additionalContactModel.getCustomerName());
				txtPosition.setText(CompanyAddController.additionalContactModel.getPosition());
				txtExtension.setText(CompanyAddController.additionalContactModel.getExt());
				txtFax.setText(CompanyAddController.additionalContactModel.getFax());
				txtCellular.setText(CompanyAddController.additionalContactModel.getCellular());
				txtEmail.setText(CompanyAddController.additionalContactModel.getEmail());
				txtPager.setText(CompanyAddController.additionalContactModel.getPrefix());
				txtPhone.setText(CompanyAddController.additionalContactModel.getPhone());
				// ddlStatus.getSelectionModel().getSelectedItem();

			}
		}

	}

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/openAdd", null);
					com.dpu.request.AdditionalContact driver = mapper.readValue(response,
							com.dpu.request.AdditionalContact.class);

					List<Status> statusList = driver.getStatusList();
					fillDropDown(ddlStatus, statusList);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}

			if (object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}

		}
	}

	// -----------------------------------------------------------------------------------------

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
