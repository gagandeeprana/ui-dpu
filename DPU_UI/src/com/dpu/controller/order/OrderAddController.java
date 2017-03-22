package com.dpu.controller.order;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Company;
import com.dpu.model.OrderModel;
import com.dpu.model.Shipper;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderAddController extends Application implements Initializable {

	@FXML
	Button btnSaveOrder;
	
	@FXML
	TextField txtCallerName, txtPONo;
	
	@FXML
	ComboBox<String> ddlCustomer, ddlAdditionalContacts, ddlBillingLocation, ddlShipper, ddlConsignee, ddlCurrency, ddlDelivery, ddlPickup;
	
	Validate validate = new Validate();

	/*@FXML
	private void txtNameKeyTyped() {
		txtName.setStyle("-fx-focus-color: #87CEEB;");
	}
	
	@FXML
	private void ddlTypeAction() {
		ddlType.setStyle("-fx-focus-color: #87CEEB;");
	}
	
	private boolean validateAddEquipmentScreen() {
		String name = txtName.getText();
		String type = ddlType.getSelectionModel().getSelectedItem();
		
		boolean result = validate.validateEmptyness(name);
		if(!result) {
			txtName.setTooltip(new Tooltip("Equipment Name is Mandatory"));
			txtName.setStyle("-fx-focus-color: red;");
			txtName.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(type);
		if(!result) {
			ddlType.setTooltip(new Tooltip("Type is Mandatory"));
			ddlType.setStyle("-fx-focus-color: red;");
			ddlType.requestFocus();
			return result;
		}
		return result;
	}*/
	
	
	
	@FXML
	private void btnSaveOrderAction() {
//		boolean result = validateAddEquipmentScreen();
//		if(result) {
//			addOrder();
			closeAddOrderScreen(btnSaveOrder);
//		}
	}
	
	private void closeAddOrderScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	List<Company> companyList = null;
	List<Shipper> shipperList = null;
	List<Shipper> consigneeList = null;
	List<AdditionalContact> additionalContactsList = null;
	List<Type> currencyList = null, deliveryList = null, pickupList = null;
	static public List<Type> temperatureList = null, temperatureTypeList = null;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	private void btnTemperatureAction() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.ORDER_BASE_PACKAGE + Iconstants.XML_TEMPERATURE_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Temperature");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/openAdd", null);
					OrderModel orderModel = mapper.readValue(response, OrderModel.class);
					companyList = orderModel.getCompanyList();
					fillDropDown(ddlCustomer, companyList);
					shipperList = orderModel.getShipperConsineeList();
					fillDropDown(ddlShipper, shipperList);
					consigneeList = orderModel.getShipperConsineeList();
					fillDropDown(ddlConsignee, consigneeList);
					currencyList = orderModel.getCurrencyList();
					fillDropDown(ddlCurrency, currencyList);
					pickupList = orderModel.getPickupList();
					fillDropDown(ddlPickup, pickupList);
					deliveryList = orderModel.getDeliveryList();
					fillDropDown(ddlDelivery, deliveryList);
					temperatureList = orderModel.getTemperatureList();
					temperatureTypeList = orderModel.getTemperatureTypeList();
					/*additionalContactsList = orderModel.getAdd
					fillDropDown(ddlCustomer, companyList);*/
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Company) {
				Company company = (Company) object;
				comboBox.getItems().add(company.getName());
			}
			if(object != null && object instanceof Shipper) {
				Shipper shipper = (Shipper) object;
				comboBox.getItems().add(shipper.getLocationName());
			}
			if(object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			if(object != null && object instanceof AdditionalContact) {

				AdditionalContact additionalContact = (AdditionalContact) object;
				comboBox.getItems().add(additionalContact.getCustomerName());
			}
			if(object != null && object instanceof BillingControllerModel) {
				
				BillingControllerModel billingLocation = (BillingControllerModel) object;
				comboBox.getItems().add(billingLocation.getName());
			}
		}
	}
	
	/*private void addOrder() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Equipment equipment = setEquipmentValue();
					String payload = mapper.writeValueAsString(equipment);
					System.out.println("Add Payload: " + payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API, null, payload);
					System.out.println(response);
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

					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}*/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ddlCustomer.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							Long companyId = companyList.get(ddlCustomer.getSelectionModel().getSelectedIndex()).getCompanyId();
							String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + companyId + "/getData", null);
							Company companyResponse = mapper.readValue(response, Company.class);
							List<AdditionalContact> additionalContacts = companyResponse.getAdditionalContacts();
							ddlAdditionalContacts.getItems().clear();
							ddlBillingLocation.getItems().clear();

							if(additionalContacts != null && additionalContacts.size() > 0) {
								fillDropDown(ddlAdditionalContacts, additionalContacts);
							}
							List<BillingControllerModel> billingLocations = companyResponse.getBillingLocations();
							if(billingLocations != null && billingLocations.size() > 0) {
								fillDropDown(ddlBillingLocation, billingLocations);
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						}
					}
				});
			}
			
	    });
		fetchMasterDataForDropDowns();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	/*private Equipment setEquipmentValue() {
		Equipment equipment = new Equipment();
		equipment.setEquipmentName(txtName.getText());
		equipment.setDescription(txtDescription.getText());
		equipment.setTypeId(cList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		return equipment;
	}*/
}