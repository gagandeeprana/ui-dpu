package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Driver;
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

public class ServiceEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateService;
	
	int serviceId = 0;
	
	@FXML
	TextField txtService;
	
	@FXML
	ComboBox<String> ddlTextField, ddlAssociationWith, ddlStatus;
	
	@FXML
	private void btnUpdateServiceAction() {
		editService();
		closeEditServiceScreen(btnUpdateService);
		
	}
	
	private void closeEditServiceScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editService() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					DPUService service = setServiceValue();
					String payload = mapper.writeValueAsString(service);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + serviceId, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.serviceController.fetchServices();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}
	
	private DPUService setServiceValue() {
		DPUService dpuService = new DPUService();
		dpuService.setServiceName(txtService.getText());
		dpuService.setTextField(ddlTextField.getSelectionModel().getSelectedItem());
		dpuService.setAssociationWith(ddlAssociationWith.getSelectionModel().getSelectedItem());
		dpuService.setStatus(ddlStatus.getSelectionModel().getSelectedItem().equals("Active")?1:0);
		return null;
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
	
	public void initData(DPUService service) {
		serviceId = service.getServiceId();
		txtService.setText(service.getServiceName());
		ddlTextField.setValue(service.getTextField());
		ddlAssociationWith.setValue(service.getAssociationWith());
		ddlStatus.setValue(service.getStatus() == 1? "Active":"Inactive");
	}
}