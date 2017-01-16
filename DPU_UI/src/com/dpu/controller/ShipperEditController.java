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
import com.dpu.model.Trailer;

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
		editTrailer();
		closeEditTrailerScreen(btnUpdateShipper);
	}
	
	private void editTrailer() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Trailer trailer = setTrailerValue();
					String payload = mapper.writeValueAsString(trailer);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + "trailerId", null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.trailerController.fetchTrailers();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
		
	}
	
	private Trailer setTrailerValue() {
		Trailer trailer = new Trailer();
		
//		trailer.setTrailerId(trailerId);
//		trailer.setUnitNo(Integer.parseInt(txtUnitNo.getText()));
//		trailer.setUsage(txtUsage.getText());
//		trailer.setOwner(txtOwner.getText());
//		trailer.setDivision(txtDivision.getText());
//		trailer.setoOName(txtOoName.getText());
//		trailer.setTerminal(txtTerminal.getText());
//		trailer.setCategory(txtCategory.getText());
//		trailer.setTrailerType(txtTrailerType.getText());
//		trailer.setStatus(ddlStatus.getSelectionModel().getSelectedItem().toString());
//		trailer.setFinance(ddlFinance.getSelectionModel().getSelectedItem().toString());
		return trailer;
	}

	private void closeEditTrailerScreen(Button btnSaveTrailer) {
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
		shipperId = s.getShipperId();
////		txtCompa.setText(c.getName());
//		txtContact.setText(c.getContact());
//		txtAddress.setText(c.getAddress());
//		txtPosition.setText(c.getPosition());
//		txtUnitNo.setText(c.getUnitNo());
//		txtPhone.setText(c.getPhone());
//		txtExt.setText(c.getExt());
//		txtCity.setText(c.getCity());
//		txtFax.setText(c.getFax());
//		txtPrefix.setText(c.getCompanyPrefix());
//		txtProvince.setText(c.getProvinceState());
////		txtZip.setText(c.getZip());
////		txtAfterHours.setText(c.getAfterHours());
//		txtEmail.setText(c.getEmail());
//		txtTollFree.setText(c.getTollfree());
////		txtWebsite.setText(c.getWebsite());
////		txtCellular.setText(c.getCellular());
////		txtPager.setText(c.getPager());
	}
	
}