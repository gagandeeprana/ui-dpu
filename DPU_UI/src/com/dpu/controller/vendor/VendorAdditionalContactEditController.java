package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VendorAdditionalContactEditController implements Initializable {

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
//		try {
//			CompanyEditController.selectedTabValue =1;
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
//					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
//			Parent root = (Parent) fxmlLoader.load();
//
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.setTitle("Add New Company");
//			stage.setScene(new Scene(root));
//			stage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		CompanyEditController.selectedTabValue =0;
		closeAddAdditionalContactScreen(btnCancelAdditionalContact);
	}

	@FXML
	void btnUpdateAdditionalContactAction(ActionEvent event) {
//		try {
//			
//			String additionalContact = txtAdditionalContact.getText();
//			String position = txtPosition.getText();
//			String phone = txtPhone.getText();
//			String extension = txtExtension.getText();
//			String fax = txtFax.getText();
//			String pager = txtPager.getText();
//			String cellular = txtCellular.getText();
//			String status =  ddlStatus.getSelectionModel().getSelectedItem();
//			String email = txtEmail.getText();
//			AdditionalContact bcm1 = new AdditionalContact(additionalContact, position, phone, fax, cellular, email,
//					extension, pager, status);
//			CompanyEditController.selectedTabValue =1;
//			//if(CompanyAddController.addEditIndex != -1){
//				if (CompanyEditController.addAddtionalContact == 0) {
//					CompanyEditController.listOfAdditionalContact.set(CompanyEditController.editIndex, bcm1);
//				} else if(CompanyEditController.addAddtionalContact == 1){
//					CompanyEditController.listOfAdditionalContact.add(bcm1);
//				}
//			//}
//
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
//					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
//			Parent root = (Parent) fxmlLoader.load();
//
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.setTitle("Add New Company");
//			stage.setScene(new Scene(root));
//			stage.show();
// 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		CompanyEditController.selectedTabValue = 0;
		closeAddAdditionalContactScreen(btnUpdateAdditionalContact);
	}

	private void closeAddAdditionalContactScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@FXML
	void initialize() {
//		assert btnCancelAdditionalContact != null : "fx:id=\"btnCancelAdditionalContact\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert btnUpdateAdditionalContact != null : "fx:id=\"btnSaveAdditionalContact\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert chkActualDelivery != null : "fx:id=\"chkActualDelivery\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert chkActualPickupDetails != null : "fx:id=\"chkActualPickupDetails\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert chkETADeliveryDetails != null : "fx:id=\"chkETADeliveryDetails\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert chkETAPickupDetails != null : "fx:id=\"chkETAPickupDetails\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert chkOrderConfirmation != null : "fx:id=\"chkOrderConfirmation\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert ddlStatus != null : "fx:id=\"ddlStatus\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtAdditionalContact != null : "fx:id=\"txtAdditionalContact\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtCellular != null : "fx:id=\"txtCellular\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtExtension != null : "fx:id=\"txtExtension\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtPager != null : "fx:id=\"txtPager\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";
//		assert txtPosition != null : "fx:id=\"txtPosition\" was not injected: check your FXML file 'AdditionalContactAddScreen.fxml'.";

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		if (CompanyEditController.addAddtionalContact != 1) {
//			if (CompanyEditController.additionalContactModel != null) {
//
//				txtAdditionalContact.setText(CompanyEditController.additionalContactModel.getCustomerName());
//				txtPosition.setText(CompanyEditController.additionalContactModel.getPosition());
//				txtExtension.setText(CompanyEditController.additionalContactModel.getExt());
//				txtFax.setText(CompanyEditController.additionalContactModel.getFax());
//				txtCellular.setText(CompanyEditController.additionalContactModel.getCellular());
//				txtEmail.setText(CompanyEditController.additionalContactModel.getEmail());
//				txtPager.setText(CompanyEditController.additionalContactModel.getPrefix());
//				txtPhone.setText(CompanyEditController.additionalContactModel.getPhone());
//				// ddlStatus.getSelectionModel().getSelectedItem();
//
//			}
//		}

	}

}