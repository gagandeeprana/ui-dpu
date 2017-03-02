package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.CustomBroker;
import com.dpu.model.CustomBrokerTypeModel;
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomBrokerEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateCustomBroker;
	
	@FXML
	TextField txtCustomerBrokerName, txtContactNamePAPS, txtCentralPhonePAPS, txtExtensionPAPS, txtCentralFaxPAPS, txtEmailPAPS, txtTrackerLinkPAPS,
	txtContactNamePARS, txtCentralPhonePARS, txtExtensionPARS, txtCentralFaxPARS, txtEmailPARS, txtTrackerLinkPARS;
	
	@FXML
	ComboBox<String> ddlOperationPAPS, ddl24HoursPAPS, ddlStatusPAPS, ddlOperationPARS, ddl24HoursPARS, ddlStatusPARS, ddlType;
	
	Long customBrokerId = 0l;
	
	Validate validate = new Validate();
	
	List<Status> statusList;

	private Long customBrokerTypeId;
	
	@FXML
	ComboBox<String> ddlStatus;
	
	/*private boolean validateEditHandlingScreen() {
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
	}*/
	
	@FXML
	private void btnUpdateCustomBrokerAction() {
//		boolean response = validateEditHandlingScreen();
//		if(response) {
			editCustomBroker();
			closeEditCustomBrokerScreen(btnUpdateCustomBroker);
//		}
	}
	
	private void closeEditCustomBrokerScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editCustomBroker() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CustomBroker customBroker = setCustomBrokerValue();
					String payload = mapper.writeValueAsString(customBroker);
					System.out.println("AYYYY: " + payload);
					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API + "/" + customBrokerId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<CustomBroker> customBrokerList = (List<CustomBroker>) success.getResultList();
						String res = mapper.writeValueAsString(customBrokerList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.customBrokerController.fillCustomBrokers(res);
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
	
	private CustomBroker setCustomBrokerValue() {
		CustomBroker customBroker = new CustomBroker();
		customBroker.setCustomBrokerId(customBrokerId);
		customBroker.setCustomBrokerName(txtCustomerBrokerName.getText());
		List<CustomBrokerTypeModel> customBrokerTypeModelList = new ArrayList<>();
		CustomBrokerTypeModel customBrokerTypeModel = null;
		switch(ddlType.getSelectionModel().getSelectedItem()) {
		case Iconstants.CUSTOM_BROKER_PAPS:
			customBrokerTypeModel = setPAPS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
			break;
		case Iconstants.CUSTOM_BROKER_PARS:
			customBrokerTypeModel = setPARS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
			break;
		default:
			customBrokerTypeModel = setPAPS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
			customBrokerTypeModel = setPARS(customBroker);
			customBrokerTypeModelList.add(customBrokerTypeModel);
		}
		customBroker.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		customBroker.setCustomBrokerTypes(customBrokerTypeModelList);
		return customBroker;
	}
	
	private CustomBrokerTypeModel setPAPS(CustomBroker customBroker) {
		CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
		for(Type type : typeList) {
			if(type.getTypeName().equals(Iconstants.CUSTOM_BROKER_PAPS)) {
				customBrokerTypeModel.setTypeId(type.getTypeId());
				break;
			}
		}
		if(papsCustomBrokerTypeId != null && papsCustomBrokerTypeId != 0l) {
			customBrokerTypeModel.setCustomBrokerTypeId(papsCustomBrokerTypeId);
		}
//		customBrokerTypeModel.setCustomBrokerTypeId(customBrokerTypeId);
		customBrokerTypeModel.setContactName(txtContactNamePAPS.getText());
		customBrokerTypeModel.setOperationId(operationList.get(ddlOperationPAPS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setPhone(txtCentralPhonePAPS.getText());
		customBrokerTypeModel.setExtention(txtExtensionPAPS.getText());
		customBrokerTypeModel.setFaxNumber(txtCentralFaxPAPS.getText());
		customBrokerTypeModel.setTimeZoneId(timeZoneList.get(ddl24HoursPAPS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setEmail(txtEmailPAPS.getText());
		customBrokerTypeModel.setTrackerLink(txtTrackerLinkPAPS.getText());
		customBrokerTypeModel.setStatusId(statusList.get(ddlStatusPAPS.getSelectionModel().getSelectedIndex()).getId());		
//		customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return customBrokerTypeModel;
	}
	
	private CustomBrokerTypeModel setPARS(CustomBroker customBroker) {
		CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
		for(Type type : typeList) {
			if(type.getTypeName().equals(Iconstants.CUSTOM_BROKER_PARS)) {
				customBrokerTypeModel.setTypeId(type.getTypeId());
				break;
			}
		}
		if(parsCustomBrokerTypeId != null && parsCustomBrokerTypeId != 0l) {
			customBrokerTypeModel.setCustomBrokerTypeId(parsCustomBrokerTypeId);
		}
		customBrokerTypeModel.setContactName(txtContactNamePARS.getText());
		customBrokerTypeModel.setOperationId(operationList.get(ddlOperationPARS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setPhone(txtCentralPhonePARS.getText());
		customBrokerTypeModel.setExtention(txtExtensionPARS.getText());
		customBrokerTypeModel.setFaxNumber(txtCentralFaxPARS.getText());
		customBrokerTypeModel.setTimeZoneId(timeZoneList.get(ddl24HoursPARS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setEmail(txtEmailPARS.getText());
		customBrokerTypeModel.setTrackerLink(txtTrackerLinkPARS.getText());
		customBrokerTypeModel.setStatusId(statusList.get(ddlStatusPARS.getSelectionModel().getSelectedIndex()).getId());
//		customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return customBrokerTypeModel;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public void initData(CustomBroker customBroker) {
		customBrokerId = customBroker.getCustomBrokerId();
		txtCustomerBrokerName.setText(customBroker.getCustomBrokerName());
		String customBrokerType = null;
		for(Type type : customBroker.getTypeList()) {
			if(type.getTypeId() == customBroker.getTypeId()) {
				customBrokerType = type.getTypeName();
				break;
			}
		}
		switch (customBrokerType) {
		case Iconstants.CUSTOM_BROKER_PAPS:
			setPAPS(customBroker, 0);
			setOther(customBrokerType);
			break;
		case Iconstants.CUSTOM_BROKER_PARS:
			customBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
			setPARS(customBroker, 0);
			setOther(customBrokerType);
			break;
		default:
			setBoth(customBroker, 0);
			break;
		}
		customBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
		typeList = customBroker.getTypeList();
		for(int i = 0; i < typeList.size();i++) {
			Type type = typeList.get(i);
			ddlType.getItems().add(type.getTypeName());
			if(type.getTypeId() == customBroker.getTypeId()) {
				ddlType.getSelectionModel().select(i);
			}
		}
	}
	
	List<Type> typeList, operationList, timeZoneList;
	
	private void setOther(String customBrokerType) {
		if(customBrokerType.equals(Iconstants.CUSTOM_BROKER_PAPS)) {
			for(int i = 0; i< operationList.size();i++) {
				Type type = operationList.get(i);
				ddlOperationPARS.getItems().add(type.getTypeName());
			}
			for(int i = 0; i < timeZoneList.size();i++) {
				Type type = timeZoneList.get(i);
				ddl24HoursPARS.getItems().add(type.getTypeName());
			}
			for(int i = 0; i < statusList.size();i++) {
				Status status = statusList.get(i);
				ddlStatusPARS.getItems().add(status.getStatus());
			}
		} else if(customBrokerType.equals(Iconstants.CUSTOM_BROKER_PARS)) {
			for(int i = 0; i< operationList.size();i++) {
				Type type = operationList.get(i);
				ddlOperationPAPS.getItems().add(type.getTypeName());
			}
			for(int i = 0; i < timeZoneList.size();i++) {
				Type type = timeZoneList.get(i);
				ddl24HoursPAPS.getItems().add(type.getTypeName());
			}
			for(int i = 0; i < statusList.size();i++) {
				Status status = statusList.get(i);
				ddlStatusPAPS.getItems().add(status.getStatus());
			}
		}
	}
	
	Long papsCustomBrokerTypeId, parsCustomBrokerTypeId = 0l;
	
	private void setPAPS(CustomBroker customBroker, int index) {
		CustomBrokerTypeModel customBrokerTypeModel = customBroker.getCustomBrokerTypes().get(index);
		txtContactNamePAPS.setText(customBrokerTypeModel.getContactName());
		operationList = customBroker.getOperationList();
		papsCustomBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
		for(int i = 0; i< operationList.size();i++) {
			Type type = operationList.get(i);
			ddlOperationPAPS.getItems().add(type.getTypeName());
			if(type.getTypeId() == customBrokerTypeModel.getOperationId()) {
				ddlOperationPAPS.getSelectionModel().select(i);
			}
		}
		txtCentralPhonePAPS.setText(customBrokerTypeModel.getPhone());
		txtExtensionPAPS.setText(customBrokerTypeModel.getExtention());
		txtCentralFaxPAPS.setText(customBrokerTypeModel.getFaxNumber());
		timeZoneList = customBroker.getTimeZoneList();
		for(int i = 0; i < timeZoneList.size();i++) {
			Type type = timeZoneList.get(i);
			ddl24HoursPAPS.getItems().add(type.getTypeName());
			if(type.getTypeId() == customBrokerTypeModel.getTimeZoneId()) {
				ddl24HoursPAPS.getSelectionModel().select(i);
			}
		}
		txtEmailPAPS.setText(customBrokerTypeModel.getEmail());
		txtTrackerLinkPAPS.setText(customBrokerTypeModel.getTrackerLink());
		statusList = customBroker.getStatusList();
		for(int i = 0; i < statusList.size();i++) {
			Status status = statusList.get(i);
			ddlStatusPAPS.getItems().add(status.getStatus());
			if(status.getId() == customBrokerTypeModel.getStatusId()) {
				ddlStatusPAPS.getSelectionModel().select(i);
			}
		}
	}
	
	private void setPARS(CustomBroker customBroker, int index) {
		CustomBrokerTypeModel customBrokerTypeModel = customBroker.getCustomBrokerTypes().get(index);
		txtContactNamePARS.setText(customBrokerTypeModel.getContactName());
		operationList = customBroker.getOperationList();
		parsCustomBrokerTypeId = customBroker.getCustomBrokerTypes().get(0).getCustomBrokerTypeId();
		for(int i = 0; i< operationList.size();i++) {
			Type type = operationList.get(i);
			ddlOperationPARS.getItems().add(type.getTypeName());
			if(type.getTypeId() == customBrokerTypeModel.getOperationId()) {
				ddlOperationPARS.getSelectionModel().select(i);
			}
		}
		txtCentralPhonePARS.setText(customBrokerTypeModel.getPhone());
		txtExtensionPARS.setText(customBrokerTypeModel.getExtention());
		txtCentralFaxPARS.setText(customBrokerTypeModel.getFaxNumber());
		timeZoneList = customBroker.getTimeZoneList();
		for(int i = 0; i < timeZoneList.size();i++) {
			Type type = timeZoneList.get(i);
			ddl24HoursPARS.getItems().add(type.getTypeName());
			if(type.getTypeId() == customBrokerTypeModel.getTimeZoneId()) {
				ddl24HoursPARS.getSelectionModel().select(i);
			}
		}
		txtEmailPARS.setText(customBrokerTypeModel.getEmail());
		txtTrackerLinkPARS.setText(customBrokerTypeModel.getTrackerLink());
		statusList = customBroker.getStatusList();
		for(int i = 0; i < statusList.size();i++) {
			Status status = statusList.get(i);
			ddlStatusPARS.getItems().add(status.getStatus());
			if(status.getId() == customBrokerTypeModel.getStatusId()) {
				ddlStatusPARS.getSelectionModel().select(i);
			}
		}
	}
	
	private void setBoth(CustomBroker customBroker, int index) {
		setPAPS(customBroker, 0);
		setPARS(customBroker, 1);
	}
}