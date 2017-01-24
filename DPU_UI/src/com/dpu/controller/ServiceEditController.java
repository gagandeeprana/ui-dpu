package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Status;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class ServiceEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateService;
	
	Long serviceId = 0l;
	
	@FXML
	TextField txtService;
	
	Validate validate = new Validate();
	
	@FXML
	ComboBox<String> ddlTextField, ddlAssociationWith, ddlStatus;
	
	private boolean validateEditServiceScreen() {
		String name = txtService.getText();
		String textField = ddlTextField.getSelectionModel().getSelectedItem();
		String association = ddlAssociationWith.getSelectionModel().getSelectedItem();
		String status = ddlStatus.getSelectionModel().getSelectedItem();

		
		boolean result = validate.validateEmptyness(name);
		if(!result) {
			txtService.setTooltip(new Tooltip("Equipment Name is Mandatory"));
			txtService.setStyle("-fx-focus-color: red;");
			txtService.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(textField);
		if(!result) {
			ddlTextField.setTooltip(new Tooltip("TextField is Mandatory"));
			ddlTextField.setStyle("-fx-focus-color: red;");
			ddlTextField.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(association);
		if(!result) {
			ddlAssociationWith.setTooltip(new Tooltip("Association is Mandatory"));
			ddlAssociationWith.setStyle("-fx-focus-color: red;");
			ddlAssociationWith.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(status);
		if(!result) {
			ddlStatus.setTooltip(new Tooltip("Status is Mandatory"));
			ddlStatus.setStyle("-fx-focus-color: red;");
			ddlStatus.requestFocus();
			return result;
		}
		return result;
	}
	
	@FXML
	private void btnUpdateServiceAction() {
		boolean response = validateEditServiceScreen();
		if(response) {
			editService();
			closeEditServiceScreen(btnUpdateService);
		}
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
					MainScreen.serviceController.fillServices(response);

					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
					//MainScreen.serviceController.fetchServices();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}
	
	List<Type> typeList, associatedWithList;
	
	List<Status> statusList;

	private DPUService setServiceValue() {
		DPUService dPUService = new DPUService();
		dPUService.setServiceName(txtService.getText());
		dPUService.setTextFieldId(typeList.get(ddlTextField.getSelectionModel().getSelectedIndex()).getTypeId());
		dPUService.setAssociationWithId(associatedWithList.get(ddlAssociationWith.getSelectionModel().getSelectedIndex()).getTypeId());
		dPUService.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		return dPUService;
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
		typeList = service.getTextFieldList();
		associatedWithList = service.getAssociatedWithList();
		statusList = service.getStatusList();
		for(int i = 0; i< service.getTextFieldList().size();i++) {
			Type type = service.getTextFieldList().get(i);
			ddlTextField.getItems().add(type.getTypeName());
			if(type.getTypeId() == service.getTextFieldId()) {
				ddlTextField.getSelectionModel().select(i);
			}
		}
		
		for(int i = 0; i< service.getAssociatedWithList().size();i++) {
			Type type = service.getAssociatedWithList().get(i);
			ddlAssociationWith.getItems().add(type.getTypeName());
			if(type.getTypeId() == service.getAssociationWithId()) {
				ddlAssociationWith.getSelectionModel().select(i);
			}
		}
		
		for(int i = 0; i< service.getStatusList().size();i++) {
			Status status = service.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if(status.getId() == service.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}
}