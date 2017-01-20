package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Type;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquipmentEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateEquipment;
	
	Long equipmentId = 0l;
	
	@FXML
	TextField txtName, txtDescription;
	
	@FXML
	ComboBox<String> ddlType;
	
	@FXML
	private void btnUpdateEquipmentAction() {
		editEquipment();
		closeEditEquipmentScreen(btnUpdateEquipment);
	}
	
	private void closeEditEquipmentScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	List<Type> cList = null;
	
	public void fetchTypes() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TYPE_API, null);
					System.out.println(response);
					if(response != null && response.length() > 0) {
						Type c[] = mapper.readValue(response, Type[].class);
						cList = new ArrayList<Type>();
						for(Type ccl : c) {
							ddlType.getItems().add(ccl.getTypeName());
							cList.add(ccl);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e , "Info", 1);
				}
			}
		});
	}
	
	private void editEquipment() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Equipment equipment = setEquipmentValue();
					String payload = mapper.writeValueAsString(equipment);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipmentId, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.equipmentController.fetchEquipments();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		fetchTypes();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private Equipment setEquipmentValue() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentId(equipmentId);
		equipment.setDescription(txtDescription.getText());
		equipment.setEquipmentName(ddlType.getSelectionModel().getSelectedItem());
		return equipment;
	}

	public void initData(Equipment e) {
		fetchTypes();
		equipmentId = e.getEquipmentId();
		txtName.setText(e.getEquipmentName());
		System.out.println();
		for(int i = 0; i<cList.size();i++) {
			Type type = cList.get(i);
			if(type.getTypeId() == e.getTypeId()) {
				ddlType.getSelectionModel().select(i);
			}
		}
		txtDescription.setText(e.getDescription());
	}
}