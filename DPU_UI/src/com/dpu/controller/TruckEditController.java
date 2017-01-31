package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Truck;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TruckEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateTruck;
	
	Long truckId = 0l;
	
	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtDivision, txtOoName, txtTerminal, 
	txtCategory, txtTruckType, txtStatus, txtFinance;
	
	@FXML
	private void btnUpdateTruckAction() {
		editTruck();
		closeEditTruckScreen(btnUpdateTruck);
		
	}
	
	private void closeEditTruckScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editTruck() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Truck truck = setTruckValue();
					String payload = mapper.writeValueAsString(truck);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API + "/" + truckId, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.truckController.fetchTrucks();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
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
	
	private Truck setTruckValue() {
		Truck truck = new Truck();
		truck.setUnitNo(Integer.parseInt(txtUnitNo.getText()));
//		truck.setUsage(txtUsage.getText());
//		truck.setOwner(txtOwner.getText());
//		truck.setDivision(txtDivision.getText());
//		truck.setoOName(txtOoName.getText());
//		truck.setTerminal(txtTerminal.getText());
//		truck.setCategory(txtCategory.getText());
//		truck.setTruckType(txtTruckType.getText());
//		truck.setStatus(txtStatus.getText());
		truck.setFinance(txtFinance.getText());
		return truck;
	}

	public void initData(Truck t) {
		truckId = t.getTruckId();
		txtUnitNo.setText(String.valueOf(t.getUnitNo()));
//		txtUsage.setText(t.getUsage());
//		txtOwner.setText(t.getOwner());
//		txtDivision.setText(t.getDivision());
//		txtOoName.setText(t.getoOName());
//		txtTerminal.setText(t.getTerminal());
//		txtCategory.setText(t.getCategory());
//		txtTruckType.setText(t.getTruckType());
//		txtStatus.setText(t.getStatus());
		txtFinance.setText(t.getFinance());
	}
}