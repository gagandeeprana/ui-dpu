package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServiceAddController extends Application implements Initializable{

	@FXML
	Button btnSaveService;
	
	@FXML
	TextField txtService;
	
	@FXML
	ComboBox<String> ddlTextField, ddlAssociationWith, ddlStatus;
	
	@FXML
	private void btnSaveServiceAction() {
		addService();
		closeAddServiceScreen(btnSaveService);
	}
	
	private void closeAddServiceScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addService() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					DPUService service = setServiceValue();
					String payload = mapper.writeValueAsString(service);
					System.out.println(payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API, null, payload);
					System.out.println(response);
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.serviceController.fetchServices();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ddlTextField.setValue("Yes");
		ddlAssociationWith.setValue("Asso 1");
		ddlStatus.setValue("Active");
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private DPUService setServiceValue() {
		DPUService dPUService = new DPUService();
		dPUService.setServiceName(txtService.getText());
		dPUService.setTextField(ddlTextField.getSelectionModel().getSelectedItem());
		dPUService.setAssociationWith(ddlAssociationWith.getSelectionModel().getSelectedItem());
		dPUService.setStatus(ddlStatus.getSelectionModel().getSelectedItem().equals("Active")?1:0);
		return dPUService;
	}
}