package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.Shipper;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShipperEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateShipper;
	
	@FXML
	TextField txtCompany, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtTollFree, txtPlant, txtStatus, txtCellNumber, txtZone, txtEmail, txtLeadTime, txtTimeZone, txtImporter;
	
	@FXML
	TextArea txtInternalNotes, txtStandardNotes;

	private int shipperId = 0;
	
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


				//	String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + "trailerId", null, payload);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipperId, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.shipperController.fetchShippers();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		
	}
	

	//to update, set these values
	private Shipper setShipperValue() {
		Shipper shipper = new Shipper();
		shipper.setCompany(txtCompany.getText());
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
		shipper.setStatus(txtStatus.getText());
		//cellnumber yet to be done
		shipper.setZone(txtZone.getText());
		shipper.setEmail(txtEmail.getText());
		shipper.setLeadTime(txtLeadTime.getText());
		shipper.setTimeZone(txtTimeZone.getText());
		shipper.setImporter(txtImporter.getText());
		shipper.setInternalNotes(txtInternalNotes.getText());
		shipper.setStandardNotes(txtStandardNotes.getText());	
		shipper.setCompany(txtCompany.getText());
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
	
	public void initData(Shipper s) {
//		shipperId = s.getShipperId();

		txtCompany.setText(s.getCompany());
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
		txtStatus.setText(s.getStatus());
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