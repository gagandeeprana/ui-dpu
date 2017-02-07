package com.dpu.controller;

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
import com.dpu.model.Equipment;

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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EquipmentController extends Application implements Initializable {

	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	TextField txtSearchEquipment;
	
	@FXML
	TableView<Equipment> tblEquipment;
	
	List<Equipment> equipments = null;
	
	@FXML
	TableColumn<Equipment, String> name, type, description;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        final ProgressIndicator progressIndicator = new ProgressIndicator(0);
		fetchEquipments();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		name = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(0);
		type = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(1);
		description = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(2);
	}

	@FXML
	private void btnAddEquipmentAction() {
		openAddEquipmentScreen();
	}
	
	private void openAddEquipmentScreen() {
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
	}
	
	@FXML
	private void btnDeleteEquipmentAction() {
		Equipment equipment = tblEquipment.getSelectionModel().getSelectedItem();
		if(equipment != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
						/*if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}*/
						fillEquipments(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
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
	}
	
	@FXML
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
	}
	
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
	
	@FXML
	AnchorPane equipmentParentAnchorPane;
	
	public void fetchEquipments() {
	
		fetchColumns();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					long startTime = new Date().getTime();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API, null);
					System.out.println(response);
					if(response != null && response.length() > 0) {
						Equipment c[] = mapper.readValue(response, Equipment[].class);
						equipments = new ArrayList<Equipment>();
						for(Equipment ccl : c) {
							equipments.add(ccl);
						}
						ObservableList<Equipment> data = FXCollections.observableArrayList(equipments);
						
						setColumnValues();
						tblEquipment.setItems(data);
			
			            
			    		tblEquipment.setVisible(true);
			            System.out.println("Time to fetch equipments: " + (new Date().getTime() - startTime));
					}
				} catch (Exception e) {
																																																					JOptionPane.showMessageDialog(null, "Try Again..  " + e , "Info", 1);
				}
			}
		});

	}
	
	public void fillEquipments(String response) {
		
		try {
			ObservableList<Equipment> data = null;
			equipments = new ArrayList<Equipment>();
			setColumnValues();
			if(response != null && response.length() > 0) {
				Equipment c[] = mapper.readValue(response, Equipment[].class);
				for(Equipment ccl : c) {
					equipments.add(ccl);
				}
				data = FXCollections.observableArrayList(equipments);
			} else {
				data = FXCollections.observableArrayList(equipments);
			}
			tblEquipment.setItems(data);
            tblEquipment.setVisible(true);
		} catch (Exception e) {
			System.out.println("EquipmentController: fillEquipments(): "+ e.getMessage());
		}
	}
	
	private void setColumnValues() {
		
		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
				return new SimpleStringProperty(param.getValue().getEquipmentName() + "");
			}
		});
		type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
				return new SimpleStringProperty(param.getValue().getType() + "");
			}
		});
		description.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
				return new SimpleStringProperty(param.getValue().getDescription() + "");
			}
		});
	}
	// ADD MENU

			public int tblEquipmentMenuCount = 0;

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
}