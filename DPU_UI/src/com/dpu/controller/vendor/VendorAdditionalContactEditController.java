package com.dpu.controller.vendor;

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

import javafx.application.Platform;
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
	
	List<Status> statusList;

	/*private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/openAdd", null);
					AdditionalContact driver = mapper.readValue(response, AdditionalContact.class);

					statusList = driver.getStatusList();
					fillDropDown(ddlStatus, statusList);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}*/
	
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

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/openAdd", null);
					com.dpu.request.AdditionalContact driver = mapper.readValue(response, com.dpu.request.AdditionalContact.class);

					statusList = driver.getStatusList();
//					fillDropDown(ddlStatus, statusList);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		if (VendorAddController.addAddtionalContact != 1) {
			if (VendorAddController.additionalContactModel != null) {
				txtAdditionalContact.setText(VendorAddController.additionalContactModel.getVendorName());
				txtPosition.setText(VendorAddController.additionalContactModel.getPosition());
				txtExtension.setText(VendorAddController.additionalContactModel.getExt());
				txtFax.setText(VendorAddController.additionalContactModel.getFax());
				txtCellular.setText(VendorAddController.additionalContactModel.getCellular());
				txtEmail.setText(VendorAddController.additionalContactModel.getEmail());
				txtPager.setText(VendorAddController.additionalContactModel.getPrefix());
				txtPhone.setText(VendorAddController.additionalContactModel.getPhone());
				if(VendorAdditionalContactAddController.statusList != null && VendorAdditionalContactAddController.statusList.size() > 0) {
					statusList = VendorAdditionalContactAddController.statusList;
				} else {
					fetchMasterDataForDropDowns();
				}
				
				for(int i=0; i < statusList.size(); i++) {
					Status status = statusList.get(i);
					ddlStatus.getItems().add(status.getStatus());
					if(status.getId().equals(VendorAddController.additionalContactModel.getStatusId())) {
						ddlStatus.getSelectionModel().select(i);
					}
				}
//			}
		}
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

}








