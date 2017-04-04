package com.dpu.controller.order;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Company;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.OrderPickUpDeliveryModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Shipper;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class OrderEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateOrder;
	
	@FXML
	TextField txtCallerName, txtPONo;
	
	@FXML
	ComboBox<String> ddlCustomer, ddlAdditionalContacts, ddlBillingLocation, ddlShipper, ddlConsignee, ddlCurrency, ddlDelivery, 
	ddlPickup, ddlProbill;
	
	@FXML
	ComboBox<String> ddlStatus;

	Long orderId = 0l;
	Validate validate = new Validate();
	List<Status> statusList;
	private OrderModel orderModel;
	List<Company> companyList = null;
	List<Shipper> shipperList = null;
	List<Shipper> consigneeList = null;
	List<AdditionalContact> additionalContactsList = null;
	List<BillingControllerModel> billingLocations = null;
	List<Type> currencyList = null, deliveryList = null, pickupList = null;
	static public List<Type> temperatureList = null, temperatureTypeList = null;
	List<Type> typeList, operationList, timeZoneList;
	Long papsCustomBrokerTypeId, parsCustomBrokerTypeId = 0l;
	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	private void btnAddProbillAction() {
		Integer value = probillDropDownList.get(probillDropDownList.size() - 1);
		ddlProbill.getItems().add((value + 1) + "");
		probillDropDownList.add(value + 1);
	}
	
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
	
	@FXML
	private void btnAddPickupNoAction() {
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
	private void btnUpdateOrderAction() {
//		boolean response = validateEditHandlingScreen();
//		if(response) {
			editOrder();
			closeEditOrderScreen(btnUpdateOrder);
//		}
	}
	
	private void closeEditOrderScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editOrder() {
		
		Platform.runLater(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					OrderModel orderModel = setOrderValue();
					String payload = mapper.writeValueAsString(orderModel);
					System.out.println("AYYYY: " + payload);
					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API + "/" + orderId, null, payload);
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
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}

		});
	}
	
	List<Integer> probillDropDownList = new ArrayList<Integer>();
	
	@FXML
	TextField txtPickpupScheduledDate, txtPickpupScheduledTime, txtPickpupMABDate, txtPickpupMABTime,
	txtDeliverScheduledDate, txtDeliverScheduledTime, txtDeliverMABDate, txtDeliverMABTime, txtDelivery1, txtPickup1;
	
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
			probilModel.setPickupScheduledTime(txtPickpupScheduledTime.getText());
			probilModel.setPickupMABDate(txtPickpupMABDate.getText());
			probilModel.setPickupMABTime(txtPickpupMABTime.getText());
			probilModel.setDeliverScheduledDate(txtDeliverScheduledDate.getText());
			probilModel.setDeliverScheduledTime(txtDeliverScheduledTime.getText());
			probilModel.setDeliveryMABDate(txtDeliverMABDate.getText());
			probilModel.setDeliveryMABTime(txtDeliverMABTime.getText());
			List<OrderPickUpDeliveryModel> orderPickupDeliveryModelList = new ArrayList<>();
			OrderPickUpDeliveryModel orderPickUpDeliveryModel = new OrderPickUpDeliveryModel();
			orderPickUpDeliveryModel.setPickupDeliveryNo(txtPickup1.getText());
			orderPickUpDeliveryModel.setTypeId(1l);
			OrderPickUpDeliveryModel orderPickUpDeliveryModel1 = new OrderPickUpDeliveryModel();
			orderPickUpDeliveryModel1.setPickupDeliveryNo(txtDelivery1.getText());
			orderPickUpDeliveryModel1.setTypeId(2l);
			orderPickupDeliveryModelList.add(orderPickUpDeliveryModel);
			orderPickupDeliveryModelList.add(orderPickUpDeliveryModel1);
			probilModel.setOrderPickUpDeliveryList(orderPickupDeliveryModelList);
			probilModelList.add(probilModel);
		}
		orderModel.setProbilList(probilModelList);
		return orderModel;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public void initData(OrderModel orderModel) {
		orderId = orderModel.getId();
		
	}
	
}