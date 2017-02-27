package com.dpu.controller.database;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
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

public class HandlingAddController<T> extends Application implements Initializable{

	@FXML
	Button btnSaveHandling;
	
	@FXML
	TextField txtHandling;
	
	@FXML
	ComboBox<String> ddlStatus;
	
	List<Status> statusList;
	
	ObjectMapper mapper = new ObjectMapper();
	
	Validate validate = new Validate();
	
	@FXML
	private void btnSaveHandlingAction() {
		boolean response = validateAddHandlingScreen();
		if(response) {
			addHandling();
			closeAddHandlingScreen(btnSaveHandling);
		}
	}
	
	private void closeAddHandlingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private boolean validateAddHandlingScreen() {
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
	
	private void addHandling() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					HandlingModel service = setHandlingValue();
					String payload = mapper.writeValueAsString(service);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API, null, payload);
					if(MainScreen.handlingController != null) {
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
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}
	
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
		}
	}
	
	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_HANDLING_API + "/openAdd", null);
					HandlingModel service = mapper.readValue(response, HandlingModel.class);
					statusList = service.getStatusList();
					fillDropDown(ddlStatus, statusList);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private HandlingModel setHandlingValue() {
		HandlingModel dPUHandling = new HandlingModel();
		dPUHandling.setName(txtHandling.getText());
		dPUHandling.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		return dPUHandling;
	}
}