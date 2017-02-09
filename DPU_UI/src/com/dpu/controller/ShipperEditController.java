package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;
import com.dpu.model.Shipper;
import com.dpu.model.Status;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShipperEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateShipper;
	
	@FXML
	TextField txtLocationName, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtTollFree, txtPlant, txtCellNumber, txtZone, txtEmail, txtLeadTime, txtTimeZone, txtImporter;
	
	@FXML
	TextArea txtInternalNotes, txtStandardNotes;
	
	@FXML
	ComboBox<String> ddlStatus;

	private Long shipperId = 0l;
	
	@FXML
	private void btnUpdateShipperAction() {
		editShipper();
		closeEditShipperScreen(btnUpdateShipper);
	}
	
	private void editShipper() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Shipper shipper = setShipperValue();
					String payload = mapper.writeValueAsString(shipper);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipperId, null, payload);
					MainScreen.shipperController.fillShippers(response);
					
					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		
	}
	

	private Shipper setShipperValue() {
		Shipper shipper = new Shipper();
		shipper.setLocationName(txtLocationName.getText());
		shipper.setContact(txtContact.getText());
		shipper.setAddress(txtAddress.getText());
		shipper.setPosition(txtPosition.getText());
		shipper.setUnit(txtUnitNo.getText());
		shipper.setPhone(txtPhone.getText());
		shipper.setExt(txtExt.getText());
		shipper.setCity(txtCity.getText());
		shipper.setFax(txtFax.getText());
		shipper.setPrefix(txtPrefix.getText());
		shipper.setProvinceState(txtProvince.getText());
		shipper.setTollFree(txtTollFree.getText());
		shipper.setPlant(txtPlant.getText());
		shipper.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		//cellnumber yet to be done
		shipper.setZone(txtZone.getText());
		shipper.setEmail(txtEmail.getText());
		shipper.setLeadTime(txtLeadTime.getText());
		shipper.setTimeZone(txtTimeZone.getText());
		shipper.setImporter(txtImporter.getText());
		shipper.setInternalNotes(txtInternalNotes.getText());
		shipper.setStandardNotes(txtStandardNotes.getText());
		return shipper;
	}

	private void closeEditShipperScreen(Button btnSaveTrailer) {
		Stage loginStage = (Stage) btnSaveTrailer.getScene().getWindow();
        loginStage.close();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	List<Company> companyList = null;
	
	List<Status> statusList = null;
	
	public void initData(Shipper s) {
		shipperId = s.getShipperId();
		statusList = s.getStatusList();
		for(int i = 0; i< s.getStatusList().size();i++) {
			Status status = s.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if(status.getId() == s.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
		/*companyList = s.getCompanyList();
		for(int i = 0; i< s.getCompanyList().size();i++) {
			Company company = s.getCompanyList().get(i);
			ddlCompany.getItems().add(company.getName());
			if(company.getCompanyId() == s.getCompanyId()) {
				ddlCompany.getSelectionModel().select(i);
			}
		}*/
		txtLocationName.setText(s.getLocationName());
		txtContact.setText(s.getContact());
		txtAddress.setText(s.getAddress());
		txtPosition.setText(s.getPosition());
		txtUnitNo.setText(s.getUnit());
		txtPhone.setText(s.getPhone());
		txtExt.setText(s.getExt());
		txtCity.setText(s.getCity());
		txtFax.setText(s.getFax());
		txtPrefix.setText(s.getPrefix());
		txtProvince.setText(s.getProvinceState());
		txtTollFree.setText(s.getTollFree());
		txtPlant.setText(s.getPlant());
		txtCellNumber.setText(s.getPhone());
		txtZone.setText(s.getZone());
		txtEmail.setText(s.getEmail());
		txtLeadTime.setText(s.getLeadTime());
		txtTimeZone.setText(s.getTimeZone());
		txtImporter.setText(s.getImporter());
		txtInternalNotes.setText(s.getInternalNotes());
		txtStandardNotes.setText(s.getStandardNotes());
	}
}