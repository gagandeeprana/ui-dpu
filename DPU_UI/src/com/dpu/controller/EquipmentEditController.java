package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EquipmentEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateEquipment;

	Long equipmentId = 0l;

	@FXML
	TextField txtName, txtDescription;

	@FXML
	ComboBox<String> ddlType;

	Validate validate = new Validate();

	@FXML
	private void btnUpdateEquipmentAction() {
		boolean result = validateEditEquipmentScreen();
		if (result) {
			editEquipment();
			closeEditEquipmentScreen(btnUpdateEquipment);
		}
	}

	private void closeEditEquipmentScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private boolean validateEditEquipmentScreen() {
		String name = txtName.getText();
		String description = txtDescription.getText();
		String type = ddlType.getSelectionModel().getSelectedItem();

		boolean result = validate.validateEmptyness(name);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			txtName.setStyle("-fx-focus-color: red;");
			txtDescription.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(description);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			txtName.setStyle("-fx-focus-color: red;");
			txtDescription.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(type);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			ddlType.setStyle("-fx-focus-color: red;");
			ddlType.requestFocus();
			return result;
		}
		return result;
	}

	private Object openValidationScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_VALIDATION_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Warning");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String name = txtName.getText();
		String description = txtDescription.getText();
		String type = ddlType.getSelectionModel().getSelectedItem();
		if (name.equals("")) {
			strBuff.append("Equipment Name is Mandatory\n");
		}
		if (description.equals("")) {
			strBuff.append("Descriptionis Mandatory\n");
		}
		if (type == null) {
			strBuff.append("Type is Mandatory\n");
		}
		return strBuff;
	}

	// List<Type> cList = null;

	/*
	 * public void fetchTypes() {
	 * 
	 * Platform.runLater(new Runnable() {
	 * 
	 * @Override public void run() { try { ObjectMapper mapper = new
	 * ObjectMapper(); String response =
	 * GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TYPE_API,
	 * null); System.out.println(response); if(response != null &&
	 * response.length() > 0) { Type c[] = mapper.readValue(response,
	 * Type[].class); cList = new ArrayList<Type>(); for(Type ccl : c) {
	 * ddlType.getItems().add(ccl.getTypeName()); cList.add(ccl); } } } catch
	 * (Exception e) { JOptionPane.showMessageDialog(null, "Try Again..  " + e ,
	 * "Info", 1); } } }); }
	 */

	private void editEquipment() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Equipment equipment = setEquipmentValue();
					String payload = mapper.writeValueAsString(equipment);
					// System.out.println("EquipmentEditController: equimentId:
					// " + equipmentId);
					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipmentId, null, payload);
					// System.out.println("Update Response: " + response);
					// MainScreen.equipmentController.fillEquipments(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Equipment> equipmentList = (List<Equipment>) success.getResultList();
						String res = mapper.writeValueAsString(equipmentList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.equipmentController.fillEquipments(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
					/*
					 * if(response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage() , "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
					// MainScreen.equipmentController.fetchEquipments();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// fetchTypes();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	private Equipment setEquipmentValue() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentId(equipmentId);
		equipment.setEquipmentName(txtName.getText());
		equipment.setDescription(txtDescription.getText());
		equipment.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return equipment;
	}

	List<Type> typeList = null;

	public void initData(Equipment e) {
		// fetchTypes();
		equipmentId = e.getEquipmentId();
		typeList = e.getTypeList();
		txtName.setText(e.getEquipmentName());
		for (int i = 0; i < e.getTypeList().size(); i++) {
			Type type = e.getTypeList().get(i);
			ddlType.getItems().add(type.getTypeName());
			if (type.getTypeId() == e.getTypeId()) {
				ddlType.getSelectionModel().select(i);
			}
		}
		txtDescription.setText(e.getDescription());
	}
}