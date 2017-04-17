package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.CustomBroker;
import com.dpu.model.CustomBrokerTypeModel;
import com.dpu.model.Failed;
import com.dpu.model.Shipper;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CustomBrokerAddController<T> extends Application implements Initializable{

	@FXML
	Button btnSaveCustomBroker;
	
	@FXML
	TextField txtCustomerBrokerName, txtContactNamePAPS, txtCentralPhonePAPS, txtExtensionPAPS, txtCentralFaxPAPS, txtEmailPAPS, txtTrackerLinkPAPS,
	txtContactNamePARS, txtCentralPhonePARS, txtExtensionPARS, txtCentralFaxPARS, txtEmailPARS, txtTrackerLinkPARS;
	
	@FXML
	ComboBox<String> ddlOperationPAPS, ddl24HoursPAPS, ddlStatusPAPS, ddlOperationPARS, ddl24HoursPARS, ddlStatusPARS, ddlType;
	
	List<Status> statusList;
	
	List<Type> typeList, operationList, timeZoneList;
	
	ObjectMapper mapper = new ObjectMapper();
	
	Validate validate = new Validate();
	
	@FXML
	Label lblCustomBrokerName, lblContactNamePAPS;
	
	@FXML
	private void btnSaveCustomBrokerAction() {
		boolean response = validateAddCustomBrokerScreen();
		if(response) {
			addCustomBroker();
			closeAddCustomBrokerScreen(btnSaveCustomBroker);
		}
	}
	
	private boolean validateAddCustomBrokerScreen() {
		boolean response = true;
		String name = txtCustomerBrokerName.getText();
		boolean result = validate.validateEmptyness(name);

		if (!result) {
			
			response = false;
			txtCustomerBrokerName.setStyle("-fx-text-box-border: red;");
			lblCustomBrokerName.setVisible(true);
			lblCustomBrokerName.setText("CustomBroker Name is Mandatory");
			lblCustomBrokerName.setTextFill(Color.RED);
			
		} else if (!validate.validateLength(name, 5, 25)) {
			
			response = false;
			txtCustomerBrokerName.setStyle("-fx-text-box-border: red;");
			lblCustomBrokerName.setVisible(true);
			lblCustomBrokerName.setText("Min. length 5 and Max. length 25");
			lblCustomBrokerName.setTextFill(Color.RED);
		}
		if(validatePAPS) {
			String contactNamePAPS = txtContactNamePAPS.getText();
			result = validate.validateEmptyness(contactNamePAPS);
			if (!result) {
				
				response = false;
				txtContactNamePAPS.setStyle("-fx-text-box-border: red;");
				lblContactNamePAPS.setVisible(true);
				lblContactNamePAPS.setText("Contact Name is Mandatory");
				lblContactNamePAPS.setTextFill(Color.RED);
				
			} else if (!validate.validateLength(name, 5, 25)) {
				
				response = false;
				txtCustomerBrokerName.setStyle("-fx-text-box-border: red;");
				lblContactNamePAPS.setVisible(true);
				lblContactNamePAPS.setText("Min. length 5 and Max. length 25");
				lblContactNamePAPS.setTextFill(Color.RED);
			}
		}
		return response;
	}
	
	private void closeAddCustomBrokerScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	@FXML
	private void customerBrokerNameKeyPressed() {
		String name = txtCustomerBrokerName.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCustomBrokerName.setText("");
			txtCustomerBrokerName.setStyle("-fx-focus-color: skyblue;");
			lblCustomBrokerName.setVisible(false);
		} else {
			txtCustomerBrokerName.setStyle("-fx-border-color: red;");
			lblCustomBrokerName.setVisible(true);
			lblCustomBrokerName.setText("CustomBroker Name is Mandatory");
			lblCustomBrokerName.setTextFill(Color.RED);
		}
	}
	
	@FXML
	private void contactNamePAPSKeyReleased() {
		String name = txtContactNamePAPS.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblContactNamePAPS.setText("");
			txtContactNamePAPS.setStyle("-fx-focus-color: skyblue;");
			lblContactNamePAPS.setVisible(false);
		} else {
			txtContactNamePAPS.setStyle("-fx-border-color: red;");
			lblContactNamePAPS.setVisible(true);
			lblContactNamePAPS.setText("Contact Name is Mandatory");
			lblContactNamePAPS.setTextFill(Color.RED);
		}
	}
	
	/*private boolean validateAddHandlingScreen() {
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
	
	private void addCustomBroker() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CustomBroker customBroker = setCustomBrokerValue();
					String payload = mapper.writeValueAsString(customBroker);
					System.out.println(payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API, null, payload);
					if(MainScreen.customBrokerController != null) {
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
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	boolean validatePAPS, validatePARS;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
		String typeDropDownValue = ddlType.getSelectionModel().getSelectedItem();
		if(typeDropDownValue == null) {
			setPAPSEditable(true);
			setPARSEditable(true);
		}
		ddlType.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							String typeValue = ddlType.getSelectionModel().getSelectedItem();
							if(typeValue != null) {
								switch (typeValue) {
								case "PARS":
									validatePARS = true;
									validatePAPS = false;
									clearPAPS();
									setPARSEditable(false);
									setPAPSEditable(true);
									break;
									
								case "PAPS":
									validatePARS = false;
									validatePAPS = true;
									clearPARS();
									setPARSEditable(true);
									setPAPSEditable(false);
									break;

								case "Both":
									validatePAPS = true;
									validatePARS = true;
									setPAPSEditable(false);
									setPARSEditable(false);
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						}
					}
				});
			}
			
	    });
	}
	
	private void setPAPSEditable(boolean val) {
		txtContactNamePAPS.setDisable(val);
		ddlOperationPAPS.setDisable(val);
		txtCentralPhonePAPS.setDisable(val);
		txtExtensionPAPS.setDisable(val);
		txtCentralFaxPAPS.setDisable(val);
		ddl24HoursPAPS.setDisable(val);
		txtEmailPAPS.setDisable(val);
		txtTrackerLinkPAPS.setDisable(val);
		ddlStatusPAPS.setDisable(val);
	}
	
	private void setPARSEditable(boolean val) {
		txtContactNamePARS.setDisable(val);
		ddlOperationPARS.setDisable(val);
		txtCentralPhonePARS.setDisable(val);
		txtExtensionPARS.setDisable(val);
		txtCentralFaxPARS.setDisable(val);
		ddl24HoursPARS.setDisable(val);
		txtEmailPARS.setDisable(val);
		txtTrackerLinkPARS.setDisable(val);
		ddlStatusPARS.setDisable(val);
	}
	
	private void clearPAPS() {
		txtContactNamePAPS.setText("");
//		ddlOperationPAPS.getSelectionModel().select(0);
		txtCentralPhonePAPS.setText("");
		txtExtensionPAPS.setText("");
		txtCentralFaxPAPS.setText("");
//		ddl24HoursPAPS.getSelectionModel().select(0);
		txtEmailPAPS.setText("");
		txtTrackerLinkPAPS.setText("");
//		ddlStatusPAPS.getSelectionModel().select(0);
	}
	
	private void clearPARS() {
		txtContactNamePARS.setText("");
//		ddlOperationPARS.getSelectionModel().select(0);
		txtCentralPhonePARS.setText("");
		txtExtensionPARS.setText("");
		txtCentralFaxPARS.setText("");
//		ddl24HoursPARS.getSelectionModel().select(0);
		txtEmailPARS.setText("");
		txtTrackerLinkPARS.setText("");
//		ddlStatusPARS.getSelectionModel().select(0);
	}
	
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
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
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API + "/openAdd", null);
					CustomBroker customBroker = mapper.readValue(response, CustomBroker.class);
					statusList = customBroker.getStatusList();
					typeList = customBroker.getTypeList();
					timeZoneList = customBroker.getTimeZoneList();
					operationList = customBroker.getOperationList();
					fillDropDown(ddlStatusPAPS, statusList);
					fillDropDown(ddlStatusPARS, statusList);
					fillDropDown(ddl24HoursPAPS, timeZoneList);
					fillDropDown(ddl24HoursPARS, timeZoneList);
					fillDropDown(ddlOperationPAPS, operationList);
					fillDropDown(ddlOperationPARS, operationList);
					fillDropDown(ddlType, typeList);
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

	private CustomBroker setCustomBrokerValue() {
		CustomBroker customBroker = new CustomBroker();
		customBroker.setCustomBrokerName(txtCustomerBrokerName.getText());
		List<CustomBrokerTypeModel> customBrokerTypeModelList = new ArrayList<>();
		CustomBrokerTypeModel customBrokerTypeModel = null;
		switch(ddlType.getSelectionModel().getSelectedItem()) {
		case Iconstants.CUSTOM_BROKER_PAPS:
			customBrokerTypeModel = setPAPS();
			customBrokerTypeModelList.add(customBrokerTypeModel);
			break;
		case Iconstants.CUSTOM_BROKER_PARS:
			customBrokerTypeModel = setPARS();
			customBrokerTypeModelList.add(customBrokerTypeModel);
			break;
		default:
			customBrokerTypeModel = setPAPS();
			customBrokerTypeModelList.add(customBrokerTypeModel);
			customBrokerTypeModel = setPARS();
			customBrokerTypeModelList.add(customBrokerTypeModel);
		}
		customBroker.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		customBroker.setCustomBrokerTypes(customBrokerTypeModelList);
		return customBroker;
	}
	
	private CustomBrokerTypeModel setPAPS() {
		CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
		customBrokerTypeModel.setContactName(txtContactNamePAPS.getText());
		customBrokerTypeModel.setOperationId(operationList.get(ddlOperationPAPS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setPhone(txtCentralPhonePAPS.getText());
		customBrokerTypeModel.setExtention(txtExtensionPAPS.getText());
		customBrokerTypeModel.setFaxNumber(txtCentralFaxPAPS.getText());
		customBrokerTypeModel.setTimeZoneId(timeZoneList.get(ddl24HoursPAPS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setEmail(txtEmailPAPS.getText());
		customBrokerTypeModel.setTrackerLink(txtTrackerLinkPAPS.getText());
		customBrokerTypeModel.setStatusId(statusList.get(ddlStatusPAPS.getSelectionModel().getSelectedIndex()).getId());		customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return customBrokerTypeModel;
	}
	
	private CustomBrokerTypeModel setPARS() {
		CustomBrokerTypeModel customBrokerTypeModel = new CustomBrokerTypeModel();
		customBrokerTypeModel.setContactName(txtContactNamePARS.getText());
		customBrokerTypeModel.setOperationId(operationList.get(ddlOperationPARS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setPhone(txtCentralPhonePARS.getText());
		customBrokerTypeModel.setExtention(txtExtensionPARS.getText());
		customBrokerTypeModel.setFaxNumber(txtCentralFaxPARS.getText());
		customBrokerTypeModel.setTimeZoneId(timeZoneList.get(ddl24HoursPARS.getSelectionModel().getSelectedIndex()).getTypeId());
		customBrokerTypeModel.setEmail(txtEmailPARS.getText());
		customBrokerTypeModel.setTrackerLink(txtTrackerLinkPARS.getText());
		customBrokerTypeModel.setStatusId(statusList.get(ddlStatusPARS.getSelectionModel().getSelectedIndex()).getId());
		customBrokerTypeModel.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return customBrokerTypeModel;
	}
}