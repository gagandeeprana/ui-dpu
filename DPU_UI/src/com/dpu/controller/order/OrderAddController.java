package com.dpu.controller.order;

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
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Category;
import com.dpu.model.Company;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Shipper;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderAddController extends Application implements Initializable {

	@FXML
	Button btnSaveOrder;
	
	@FXML
	TextField txtCallerName, txtPONo;
	
	@FXML
	ComboBox<String> ddlCustomer, ddlAdditionalContacts, ddlBillingLocation, ddlShipper, ddlConsignee, ddlCurrency, ddlDelivery, 
	ddlPickup, ddlProbill;
	
	Validate validate = new Validate();
	
	public static OrderModel orderModel = new OrderModel();

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
			addOrder();
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
	List<BillingControllerModel> billingLocations = null;
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
	
	private void addOrder() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					OrderModel orderModel = setOrderValue();
					String payload = mapper.writeValueAsString(orderModel);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API, null, payload);
					System.out.println(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<OrderModel> orderModelList = (List<OrderModel>) success.getResultList();
						String res = mapper.writeValueAsString(orderModelList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.orderController.fillOrders(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	@FXML
	TextField txtPickpupScheduledDate;
	
	private OrderModel setOrderValue() {
		orderModel.setCompanyId(companyList.get(ddlCustomer.getSelectionModel().getSelectedIndex()).getCompanyId());
		orderModel.setBillingLocationId(billingLocations.get(ddlBillingLocation.getSelectionModel().getSelectedIndex()).getBillingLocationId());
		orderModel.setContactId(additionalContactsList.get(ddlAdditionalContacts.getSelectionModel().getSelectedIndex()).getAdditionalContactId());
		orderModel.setCurrencyId(currencyList.get(ddlCurrency.getSelectionModel().getSelectedIndex()).getTypeId());
		List<ProbilModel> probilModelList = new ArrayList<>();
		for(Integer probill : probillDropDownList) {
			ProbilModel probilModel = new ProbilModel();
			probilModel.setShipperId(shipperList.get(ddlShipper.getSelectionModel().getSelectedIndex()).getShipperId());
			probilModel.setConsineeId(consigneeList.get(ddlConsignee.getSelectionModel().getSelectedIndex()).getShipperId());
			probilModel.setPickupId(pickupList.get(ddlPickup.getSelectionModel().getSelectedIndex()).getTypeId());
			probilModel.setDeliveryId(deliveryList.get(ddlDelivery.getSelectionModel().getSelectedIndex()).getTypeId());
			probilModel.setPickupScheduledDate(txtPickpupScheduledDate.getText());
		}
		return orderModel;
	}

	@FXML
	Label lblFaxConsignee, lblFaxShipper, lblAddressShipper, lblPhoneShipper, lblAddressConsginee, lblPhoneConsignee;
	
	List<Integer> probillDropDownList = new ArrayList<Integer>();
	
	@FXML
	private void btnAddProbillAction() {
		Integer value = probillDropDownList.get(probillDropDownList.size() - 1);
		ddlProbill.getItems().add((value + 1) + "");
		probillDropDownList.add(value + 1);
	}
	
	@FXML
	TextField txtPickupNo2, txtPickupNo3, txtPickupNo4, txtPickupNo5;
	
	@FXML
	Button btnAddPickupNo2, btnAddPickupNo3, btnAddPickupNo4, btnAddPickupNo5;
	
	@FXML
	AnchorPane root, anchorPanePickupNo;

	@FXML
	StackPane stackPanePickupNos;
	
	private void hidePickupComponents() {
		
//		root.getChildren()
//		root.getChildren().removeAll(txtPickupNo2, txtPickupNo3, txtPickupNo4, txtPickupNo5, btnAddPickupNo2, btnAddPickupNo3, btnAddPickupNo4, btnAddPickupNo5);
//		root.clearConstraints(stackPanePickupNos);
	}
	
	@FXML
	private void btnAddPickupNoAction() {
	}
	@FXML
	private void btnAddPickupNo2Action() {
	}
	@FXML
	private void btnAddPickupNo3Action() {
	}
	@FXML
	private void btnAddPickupNo4Action() {
	}
	@FXML
	private void btnAddPickupNo5Action() {
	}
	
	@FXML
	private void btnRemoveProbillAction() {
		if(probillDropDownList.size() == 1) {
			return;
		}
		if(ddlProbill.getSelectionModel().getSelectedItem() != null) {
			probillDropDownList.remove(ddlProbill.getSelectionModel().getSelectedIndex());
			ddlProbill.getItems().remove(ddlProbill.getSelectionModel().getSelectedIndex());
			ddlProbill.getItems().clear();
			ObservableList<Integer> data = FXCollections.observableArrayList(probillDropDownList);
			for(Integer i : data) {
				ddlProbill.getItems().add(i + "");
			}
			ddlProbill.getSelectionModel().select(0);
			ddlProbill.setVisibleRowCount(probillDropDownList.size());
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hidePickupComponents();
		probillDropDownList.add(1);
		ddlProbill.getItems().add(1 + "");
		ddlProbill.getSelectionModel().select(0);
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
							billingLocations = companyResponse.getBillingLocations();
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
		ddlShipper.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							Long shipperId = shipperList.get(ddlShipper.getSelectionModel().getSelectedIndex()).getShipperId();
							String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + shipperId, null);
							Shipper shipperResponse = mapper.readValue(response, Shipper.class);
							if(shipperResponse != null) {
								if(shipperResponse.getAddress() != null) {
									lblAddressShipper.setText(shipperResponse.getAddress());
								}
								if(shipperResponse.getFax() != null) {
									lblFaxShipper.setText(shipperResponse.getFax());
								}
								if(shipperResponse.getAddress() != null) {
									lblPhoneShipper.setText(shipperResponse.getPhone());
								}
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
						}
					}
				});
			}
	    });
		ddlConsignee.valueProperty().addListener(new ChangeListener<String>() {
	        
			@SuppressWarnings("rawtypes")
			@Override public void changed(ObservableValue ov, String t, String t1) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							Long consigneeId = consigneeList.get(ddlConsignee.getSelectionModel().getSelectedIndex()).getShipperId();
							String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API + "/" + consigneeId, null);
							Shipper shipperResponse = mapper.readValue(response, Shipper.class);
							if(shipperResponse != null) {
								if(shipperResponse.getAddress() != null) {
									lblAddressConsginee.setText(shipperResponse.getAddress());
								}
								if(shipperResponse.getFax() != null) {
									lblFaxConsignee.setText(shipperResponse.getFax());
								}
								if(shipperResponse.getAddress() != null) {
									lblPhoneConsignee.setText(shipperResponse.getPhone());
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