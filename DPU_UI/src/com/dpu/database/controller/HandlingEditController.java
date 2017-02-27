package com.dpu.database.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Status;
import com.dpu.model.Success;
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

public class HandlingEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateHandling;
	
	Long handlingId = 0l;
	
	@FXML
	TextField txtHandling;
	
	Validate validate = new Validate();
	
	@FXML
	ComboBox<String> ddlStatus;
	
	private boolean validateEditHandlingScreen() {
		String name = txtHandling.getText();
		String status = ddlStatus.getSelectionModel().getSelectedItem();

		
		boolean result = validate.validateEmptyness(name);
		if(!result) {
			txtHandling.setTooltip(new Tooltip("Handling Name is Mandatory"));
			txtHandling.setStyle("-fx-focus-color: red;");
			txtHandling.requestFocus();
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
	private void btnUpdateHandlingAction() {
		boolean response = validateEditHandlingScreen();
		if(response) {
			editHandling();
			closeEditHandlingScreen(btnUpdateHandling);
		}
	}
	
	private void closeEditHandlingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editHandling() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					HandlingModel handling = setHandlingValue();
					String payload = mapper.writeValueAsString(handling);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API + "/" + handlingId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<HandlingModel> handlingList = (List<HandlingModel>) success.getResultList();
						String res = mapper.writeValueAsString(handlingList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.handlingController.fillHandling(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}
	
	List<Status> statusList;

	private HandlingModel setHandlingValue() {
		HandlingModel dPUService = new HandlingModel();
		dPUService.setName(txtHandling.getText());
		dPUService.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		return dPUService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public void initData(HandlingModel handling) {
		handlingId = handling.getId();
		txtHandling.setText(handling.getName());
		statusList = handling.getStatusList();
		for(int i = 0; i< handling.getStatusList().size();i++) {
			Status status = handling.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if(status.getId() == handling.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}
}