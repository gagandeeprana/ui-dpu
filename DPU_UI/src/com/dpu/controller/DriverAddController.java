package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class DriverAddController extends Application implements Initializable{

	@FXML
	Button btnSaveDriver;
	
	@FXML
	TextField txtCode, txtFirstName, txtLastName, txtAddress, txtUnit, txtPostal, txtHome, txtCity, txtFaxNo, txtCellular, 
	txtPager, txtDivision, txtEmail, txtProvince;
	
	@FXML
	ComboBox<String> ddlTerminal, ddlCategory, ddlRole, ddlStatus, ddlDriverClass, ddlDivision;
	
	@FXML
	private void btnSaveDriverAction() {
		if(txtCode.getText().equals("")) {
			final Tooltip tooltip = new Tooltip();
			tooltip.setText("Code reqd.");
			txtCode.setTooltip(tooltip);
		} else {
			addDriver();
			closeAddDriverScreen(btnSaveDriver);
		}
	}
	
	private void showProvinces() {
		try {
			txtProvince.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		        @Override
		        public void handle(KeyEvent event) {
		            if (event.getCode() == KeyCode.TAB) {
		            	ProvinceStateController.launch();
		            }
		        }
		    });
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("showProvinces(): Exception: " + e.getMessage());
		}
	}
	
	private void closeAddDriverScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addDriver() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Driver driver = setDriverValue();
					String payload = mapper.writeValueAsString(driver);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API, null, payload);
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
					
					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	List<Terminal> terminalList = null;
	
	List<Category> categoryList = null;
	
	List<Type> roleList = null, classList = null;
	
	List<Status> statusList = null;
	
	List<Division> divisionList = null;
	
	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/openAdd", null);
					Driver driver = mapper.readValue(response, Driver.class);
					categoryList = driver.getCategoryList();
					fillDropDown(ddlCategory, categoryList);
					roleList = driver.getRoleList();
					fillDropDown(ddlRole, roleList);
					statusList = driver.getStatusList();
					fillDropDown(ddlStatus, statusList);
					classList = driver.getDriverClassList();
					fillDropDown(ddlDriverClass, classList);
					divisionList = driver.getDivisionList();
					fillDropDown(ddlDivision, divisionList);
					terminalList = driver.getTerminalList();
					fillDropDown(ddlTerminal, terminalList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			/*if(object != null && object instanceof Type && comboBox.equals(ddlDriverClass)) {
				Type highlight = (Type) object;
				comboBox.getItems().add(highlight.getTypeName());
			}*/
			if(object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
			if(object != null && object instanceof Category) {
				Category category = (Category) object;
				comboBox.getItems().add(category.getName());
			}
			if(object != null && object instanceof Division) {
				Division division = (Division) object;
				comboBox.getItems().add(division.getDivisionName());
			}
			if(object != null && object instanceof Terminal) {
				Terminal terminal = (Terminal) object;
				comboBox.getItems().add(terminal.getTerminalName());
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
		showProvinces();
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
		driver.setPvs(txtProvince.getText());
		driver.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		driver.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		driver.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		driver.setRoleId(roleList.get(ddlRole.getSelectionModel().getSelectedIndex()).getTypeId());
		driver.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		driver.setDriverClassId(classList.get(ddlDriverClass.getSelectionModel().getSelectedIndex()).getTypeId());
		return driver;
	}
}