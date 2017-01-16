package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
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

public class DriverAddController extends Application implements Initializable{

	@FXML
	Button btnSaveDriver;
	
	@FXML
	TextField txtCode, txtFirstName, txtLastName, txtAddress, txtUnit, txtPostal, txtHome, txtCity, txtFaxNo, txtCellular, 
	txtPager, txtDivision, txtEmail;
	
	@FXML
	ComboBox<String> ddlTerminal, ddlCategory, ddlRole, ddlStatus, ddlDriverClass;
	
	@FXML
	private void btnSaveDriverAction() {
		addDriver();
		closeAddDriverScreen(btnSaveDriver);
	}
	
	private void closeAddDriverScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addDriver() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Driver driver = setDriverValue();
					String payload = mapper.writeValueAsString(driver);
					System.out.println(payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.driverController.fetchDrivers();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ddlTerminal.setValue("Terminal1");
		ddlCategory.setValue("Category1");
		ddlRole.setValue("Role1");
		ddlStatus.setValue("Active");
		ddlDriverClass.setValue("Class1");
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private Driver setDriverValue() {
		Driver driver = new Driver();
		driver.setDriverCode(txtCode.getText());
		driver.setFirstName(txtFirstName.getText());
		driver.setLastName(txtLastName.getText());
		driver.setAddress(txtAddress.getText());
		driver.setUnit(txtUnit.getText());
		driver.setCity(txtCity.getText());
		driver.setPostalCode(txtPostal.getText());
		driver.setEmail(txtEmail.getText());
		driver.setHome(txtHome.getText());
		driver.setFaxNo(txtFaxNo.getText());
		driver.setCellular(txtCellular.getText());
		driver.setPager(txtPager.getText());
		driver.setDivision(txtDivision.getText());
		driver.setTerminalId(ddlTerminal.getSelectionModel().getSelectedItem().equals("Terminal1")?1:2);
		driver.setCatogoryId(ddlCategory.getSelectionModel().getSelectedItem().equals("Category1")?1:2);
		driver.setRoleId(ddlRole.getSelectionModel().getSelectedItem().equals("Role1")?1:2);
		driver.setStatusId(ddlStatus.getSelectionModel().getSelectedItem().equals("Active")?1:0);
		driver.setDriverClassId(ddlDriverClass.getSelectionModel().getSelectedItem().equals("Class1")?1:2);
		return driver;
	}
}