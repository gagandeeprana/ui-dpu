package com.dpu.controller.order;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.EquipmentEditController;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.OrderAndProbillModel;
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrderController extends Application implements Initializable {

	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	TableView<OrderAndProbillModel> tblOrder;
	
	List<OrderModel> orders = null;
	
	@FXML
	TableColumn<OrderAndProbillModel, String> orderId, probill, status, customerName, poNumber, pickUpDate, pickUpCompany,
	pickUpLocation, provinceState, pickUpPostal, deliveryDate, deliveryCompany, deliveryLocation, deliveryPostal,
	orderCharges;
	
	@FXML
	AnchorPane equipmentParentAnchorPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchOrders();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		orderId = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(0);
		probill = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(1);
		status = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(2);
		customerName = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(3);
		poNumber = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(4);
		pickUpDate = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(5);
		pickUpCompany = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(6);
		pickUpLocation = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(7);
		provinceState = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(8);
		pickUpPostal = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(9);
		deliveryDate = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(10);
		deliveryCompany = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(11);
		deliveryLocation = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(12);
		deliveryPostal = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(13);
		orderCharges = (TableColumn<OrderAndProbillModel, String>) tblOrder.getColumns().get(14);
	}

	/*@FXML
	private void btnAddEquipmentAction() {
		openAddEquipmentScreen();
	}*/
	
	/*private void openAddEquipmentScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.EQUIPMENT_BASE_PACKAGE + Iconstants.XML_EQUIPMENT_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Equipment");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}*/
	
	/*@FXML
	private void btnDeleteEquipmentAction() {
		Equipment equipment = tblEquipment.getSelectionModel().getSelectedItem();
		if(equipment != null) {
			Platform.runLater(new Runnable() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
						if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Equipment> equipmentList = (List<Equipment>) success.getResultList();
							String res = mapper.writeValueAsString(equipmentList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillEquipments(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}*/
	
	/*@FXML
	private void btnGoEquipmentAction() {
		String searchEquipment = txtSearchEquipment.getText();
		if(searchEquipment != null && searchEquipment.length() > 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + searchEquipment + "/search", null);
						fillEquipments(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
		
		if(searchEquipment != null && searchEquipment.length() == 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API, null);
						fillEquipments(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
	}*/
	
	/*@FXML
	private void btnEditEquipmentAction() {
		
		Equipment equipment = equipments.get(tblEquipment.getSelectionModel().getSelectedIndex());
		if(equipment != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
						System.out.println("EE:: " + response);
						if(response != null && response.length() > 0) {
							Equipment e = mapper.readValue(response, Equipment.class);
							EquipmentEditController equipmentEditController = (EquipmentEditController) openEditEquipmentScreen();
							equipmentEditController.initData(e);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}*/
	
	private Object openEditEquipmentScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.EQUIPMENT_BASE_PACKAGE + Iconstants.XML_EQUIPMENT_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Equipment");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
	public void fetchOrders() {
	
		fetchColumns();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_ORDER_API, null);
					fillOrders(response);
				} catch (Exception e) {
				}
			}
		});

	}
	
	public void fillOrders(String response) {
		
		try {
			ObservableList<OrderAndProbillModel> data = null;
			orders = new ArrayList<OrderModel>();
			setColumnValues();
			List<OrderAndProbillModel> listOrderAndProbill = new ArrayList<>();
			if(response != null && response.length() > 0) {
				OrderModel c[] = mapper.readValue(response, OrderModel[].class);
				for(OrderModel ccl : c) {
					orders.add(ccl);
				}
				for(OrderModel orderModel : orders) {
					List<ProbilModel> probilModelList = orderModel.getProbilList();
					for(ProbilModel probilModel : probilModelList) {
						OrderAndProbillModel orderAndProbill = new OrderAndProbillModel();
						orderAndProbill.setOrderModel(orderModel);
						orderAndProbill.setProbilModel(probilModel);
						listOrderAndProbill.add(orderAndProbill);
					}
				}
				data = FXCollections.observableArrayList(listOrderAndProbill);
			} else {
				data = FXCollections.observableArrayList(listOrderAndProbill);
			}
			tblOrder.setItems(data);
            tblOrder.setVisible(true);
		} catch (Exception e) {
			System.out.println("OrderController: fillOrders(): "+ e.getMessage());
		}
	}
	
	private void setColumnValues() {
		
		orderId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderAndProbillModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<OrderAndProbillModel, String> param) {
				return new SimpleStringProperty(param.getValue().getOrderModel().getId() + "");
			}
		});
	}
	// ADD MENU

/*			public int tblEquipmentMenuCount = 0;

			@FXML
			public void handleAddContMouseClick(MouseEvent event) {

				// Create ContextMenu
				ContextMenu contextMenu = new ContextMenu();

				MenuItem item1 = new MenuItem("ADD");
				item1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
					}

				});
				MenuItem item2 = new MenuItem("EDIT");
				item2.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});

				MenuItem item3 = new MenuItem("DELETE");
				item3.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});
				
				MenuItem item4 = new MenuItem("PERSONALIZE");
				item1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
					}

				});
				MenuItem item5 = new MenuItem("DUPLICATE");
				item2.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});

				MenuItem item6 = new MenuItem("FILTER BY");
				item3.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});



				// Add MenuItem to ContextMenu
				contextMenu.getItems().addAll(item1, item2, item3, item4, item5, item6);
				if (tblEquipmentMenuCount == 0) {
					tblEquipmentMenuCount++;
					// When user right-click on Table
					tblEquipment.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
						@Override
						public void handle(ContextMenuEvent event) {
							contextMenu.show(tblEquipment, event.getScreenX(), event.getScreenY());

						}

					});

				}

			}
*/}