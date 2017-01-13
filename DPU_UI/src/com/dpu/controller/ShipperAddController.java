package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
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

public class ShipperAddController extends Application implements Initializable{

	@FXML
	Button btnSaveShipper;
	
	@FXML
	TextField txtCompany, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtTollFree, txtPlant, txtStatus, txtCellNumber, txtZone, txtEmail, txtLeadTime, txtTimeZone, txtImporter;
	
	@FXML
	TextArea txtInternalNotes, txtStandardNotes;
	
	@FXML
	private void btnSaveShipperAction() {
		addShipper();
		closeAddShipperScreen(btnSaveShipper);
	}
	
	private void closeAddShipperScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addShipper() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Shipper shipper = setShipperValue();
					String payload = mapper.writeValueAsString(shipper);
					System.out.println(payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.shipperController.fetchShippers();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
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
		return shipper;
	}
}