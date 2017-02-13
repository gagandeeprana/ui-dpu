package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
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

public class TruckAddController extends Application implements Initializable{

	@FXML
	Button btnSaveTruck;
	
	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtOoName, txtFinance;
	
	@FXML
	ComboBox<String> ddlStatus, ddlCategory, ddlDivision, ddlTerminal, ddlTruckType;
	
	@FXML
	private void btnSaveTruckAction() {
		addTruck();
		closeAddTruckScreen(btnSaveTruck);
	}
	
	private void closeAddTruckScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addTruck() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Truck truck = setTruckValue();
					String payload = mapper.writeValueAsString(truck);
					System.out.println("truck payload: " + payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API, null, payload);
//					MainScreen.truckController.fillTruck(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Truck> truckList = (List<Truck>) success.getResultList();
						String res = mapper.writeValueAsString(truckList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.truckController.fillTruck(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
					
					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Type) {
				Type truckType = (Type) object;
				comboBox.getItems().add(truckType.getTypeName());
			}
			if(object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
			if(object != null && object instanceof Category) {
				Category category = (Category) object;
				comboBox.getItems().add(category.getName());
			}
			if(object != null && object instanceof Division) {
				Division division = (Division) object;
				comboBox.getItems().add(division.getDivisionName());
			}
			if(object != null && object instanceof Terminal) {
				Terminal terminal = (Terminal) object;
				comboBox.getItems().add(terminal.getTerminalName());
			}
		}
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	List<Category> categoryList = null;
	
	List<Status> statusList = null;

	List<Division> divisionList = null;

	List<Terminal> terminalList = null;

	List<Type> truckTypeList = null;

	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API + "/openAdd", null);
					Truck truck = mapper.readValue(response, Truck.class);
					categoryList = truck.getCategoryList();
					fillDropDown(ddlCategory, categoryList);
					statusList = truck.getStatusList();
					fillDropDown(ddlStatus, statusList);
					divisionList = truck.getDivisionList();
					fillDropDown(ddlDivision, divisionList);
					terminalList = truck.getTerminalList();
					fillDropDown(ddlTerminal, terminalList);
					truckTypeList = truck.getTruckTypeList();
					fillDropDown(ddlTruckType, truckTypeList);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
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
}