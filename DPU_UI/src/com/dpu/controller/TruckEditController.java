package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Status;
import com.dpu.model.Terminal;
import com.dpu.model.Truck;
import com.dpu.model.Type;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TruckEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateTruck;
	
	Long truckId = 0l;
	
	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtOoName, txtFinance;
	
	@FXML
	ComboBox<String> ddlStatus, ddlCategory, ddlDivision, ddlTerminal, ddlTruckType;
	
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
					MainScreen.truckController.fillTruck(response);
					
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
		truck.setOwner(txtOwner.getText());
		truck.setoOName(txtOoName.getText());
		truck.setTruchUsage(txtUsage.getText());
		truck.setFinance(txtFinance.getText());
		truck.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		truck.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		truck.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		truck.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		truck.setTruckTypeId(truckTypeList.get(ddlTruckType.getSelectionModel().getSelectedIndex()).getTypeId());
		return truck;
	}

	ObjectMapper mapper = new ObjectMapper();
	
	List<Category> categoryList = null;
	
	List<Status> statusList = null;

	List<Division> divisionList = null;

	List<Terminal> terminalList = null;

	List<Type> truckTypeList = null;
	
	public void initData(Truck t) {
		truckId = t.getTruckId();
		txtUnitNo.setText(String.valueOf(t.getUnitNo()));
		txtOwner.setText(t.getOwner());
		txtOoName.setText(t.getoOName());
		txtUsage.setText(t.getTruchUsage());
		txtFinance.setText(t.getFinance());
		categoryList = t.getCategoryList();
		for(int i = 0; i< t.getCategoryList().size();i++) {
			Category category = t.getCategoryList().get(i);
			ddlCategory.getItems().add(category.getName());
			if(category.getCategoryId() == t.getCategoryId()) {
				ddlCategory.getSelectionModel().select(i);
			}
		}
		statusList = t.getStatusList();
		for(int i = 0; i< t.getStatusList().size();i++) {
			Status status = t.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if(status.getId() == t.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
		divisionList = t.getDivisionList();
		for(int i = 0; i< t.getDivisionList().size();i++) {
			Division division = t.getDivisionList().get(i);
			ddlDivision.getItems().add(division.getDivisionName());
			if(division.getDivisionId() == t.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}
		terminalList = t.getTerminalList();
		for(int i = 0; i< t.getTerminalList().size();i++) {
			Terminal terminal = t.getTerminalList().get(i);
			ddlTerminal.getItems().add(terminal.getTerminalName());
			if(terminal.getTerminalId() == t.getTerminalId()) {
				ddlTerminal.getSelectionModel().select(i);
			}
		}
		truckTypeList = t.getTruckTypeList();
		for(int i = 0; i< t.getTruckTypeList().size();i++) {
			Type truck = t.getTruckTypeList().get(i);
			ddlTruckType.getItems().add(truck.getTypeName());
			if(truck.getTypeId() == t.getTruckTypeId()) {
				ddlTruckType.getSelectionModel().select(i);
			}
		}
	}
}