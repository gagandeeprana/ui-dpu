package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Driver;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Terminal;
import com.dpu.model.Type;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DriverEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateDriver;
	
	Long driverId = 0l;
	
	@FXML
	TextField txtCode, txtFirstName, txtLastName, txtAddress, txtUnit, txtPostal, txtHome, txtCity, txtFaxNo, txtCellular, 
	txtPager, txtDivision, txtEmail, txtProvince;
	
	@FXML
	ComboBox<String> ddlTerminal, ddlCategory, ddlRole, ddlStatus, ddlDriverClass, ddlDivision;
	
	@FXML
	private void btnUpdateDriverAction() {
		editDriver();
		closeEditDriverScreen(btnUpdateDriver);
		
	}
	
	private void closeEditDriverScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editDriver() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Driver driver = setDriverValue();
					String payload = mapper.writeValueAsString(driver);
					System.out.println("PP:: " + payload);
					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driverId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Driver> driverList = (List<Driver>) success.getResultList();
						String res = mapper.writeValueAsString(driverList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.driverController.fillDriver(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
//					MainScreen.driverController.fillDriver(response);
					
					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
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
		driver.setPvs(txtProvince.getText());
		driver.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		driver.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		driver.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		driver.setRoleId(roleList.get(ddlRole.getSelectionModel().getSelectedIndex()).getTypeId());
		driver.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		driver.setDriverClassId(classList.get(ddlDriverClass.getSelectionModel().getSelectedIndex()).getTypeId());
		return driver;
	}

	List<Terminal> terminalList = null;
	
	List<Category> categoryList = null;
	
	List<Type> roleList = null, classList = null;
	
	List<Status> statusList = null;
	
	List<Division> divisionList = null;
	
	public void initData(Driver driver) {
		driverId = driver.getDriverId();
		txtCode.setText(driver.getDriverCode());
		txtFirstName.setText(driver.getFirstName());
		txtLastName.setText(driver.getLastName());
		txtAddress.setText(driver.getAddress());
		txtUnit.setText(driver.getUnit());
		txtCity.setText(driver.getCity());
		txtPostal.setText(driver.getPostalCode());
		txtEmail.setText(driver.getCity());
		txtHome.setText(driver.getHome());
		txtFaxNo.setText(driver.getFaxNo());
		txtProvince.setText(driver.getPvs());
		txtCellular.setText(driver.getCellular());
		txtPager.setText(driver.getPager());
		divisionList = driver.getDivisionList();
		terminalList = driver.getTerminalList();
		categoryList = driver.getCategoryList();
		roleList = driver.getRoleList();
		statusList = driver.getStatusList();
		classList = driver.getDriverClassList();
		for(int i = 0; i< driver.getDivisionList().size();i++) {
			Division division = driver.getDivisionList().get(i);
			ddlDivision.getItems().add(division.getDivisionName());
			if(division.getDivisionId() == driver.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}
		for(int i = 0; i< driver.getTerminalList().size();i++) {
			Terminal terminal = driver.getTerminalList().get(i);
			ddlTerminal.getItems().add(terminal.getTerminalName());
			if(terminal.getTerminalId() == driver.getTerminalId()) {
				ddlTerminal.getSelectionModel().select(i);
			}
		}
		for(int i = 0; i< driver.getCategoryList().size();i++) {
			Category category = driver.getCategoryList().get(i);
			ddlCategory.getItems().add(category.getName());
			if(category.getCategoryId() == driver.getCategoryId()) {
				ddlCategory.getSelectionModel().select(i);
			}
		}
		for(int i = 0; i< driver.getRoleList().size();i++) {
			Type role = driver.getRoleList().get(i);
			ddlRole.getItems().add(role.getTypeName());
			if(role.getTypeId() == driver.getRoleId()) {
				ddlRole.getSelectionModel().select(i);
			}
		}
		for(int i = 0; i< driver.getDriverClassList().size();i++) {
			Type driverClass = driver.getDriverClassList().get(i);
			ddlDriverClass.getItems().add(driverClass.getTypeName());
			if(driverClass.getTypeId() == driver.getDriverClassId()) {
				ddlDriverClass.getSelectionModel().select(i);
			}
		}
		for(int i = 0; i< driver.getStatusList().size();i++) {
			Status status = driver.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if(status.getId() == driver.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}
}