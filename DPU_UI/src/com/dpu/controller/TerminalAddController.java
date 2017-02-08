package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Terminal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TerminalAddController extends Application implements Initializable{

	@FXML
	Button btnSaveTerminal;
	
	@FXML
	Button btnCancelTerminal, btnAddAvailableServices, btnAddNewLocation;
	
	@FXML
	TextField txtTerminalName;
	
	@FXML
	ComboBox<String> ddlAvailableServices, ddlLocation;
	
	@FXML
	private void btnSaveTerminalAction() {
		addTerminal();
		closeAddTerminalScreen(btnSaveTerminal);
	}
	
	private void closeAddTerminalScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	@FXML
	private void btnCancelTerminalAction(){
		
	}
	
	private void addTerminal() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Terminal terminal = setTerminalValue();
					String payload = mapper.writeValueAsString(terminal);
					System.out.println("terminal add payload: " + payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API, null, payload);
					MainScreen.terminalController.fillTerminal(response);
					
					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
//					TerminalPanelController tm=new TerminalPanelController();
//					tm.fetchTerminals();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}
	
	@FXML
	private void btnAddNewLocationAction() {
		closeAddTerminalScreen(btnAddNewLocation);
		ShipperController.openAddShipperScreen();
	}
	
	@FXML
	private void btnAddAvailableServicesAction() {
		closeAddTerminalScreen(btnAddAvailableServices);
		ServiceController.openAddServiceScreen();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	List<DPUService> serviceList = null;
	
	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/openAdd", null);
					Terminal terminal = mapper.readValue(response, Terminal.class);
					serviceList = terminal.getServiceList();
					fillDropDown(ddlAvailableServices, serviceList);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		if(comboBox != null) {
			comboBox.getItems().clear();
			for(int i=0;i<list.size();i++) {
				Object object = list.get(i);
				if(object != null && object instanceof DPUService) {
					DPUService dpuService = (DPUService) object;
					comboBox.getItems().add(dpuService.getServiceName());
				}
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private Terminal setTerminalValue() {
		Terminal terminal = new Terminal();
		terminal.setTerminalName(txtTerminalName.getText());
//		terminal.setLocation(txtLocation.getText());
//		terminal.setS(serviceList.get(ddlAvailableServices.getSelectionModel().getSelectedIndex()).get.toString());		
		return terminal;
	}
}
