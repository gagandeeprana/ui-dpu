package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Equipment;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EquipmentController extends Application implements Initializable {

	@FXML
	TableView<Equipment> tblEquipment;
	
	@FXML
	TableColumn<Equipment, String> name, type, description;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
						if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						fetchEquipments();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
	private void btnEditEquipmentAction() {
		Equipment equipment = tblEquipment.getSelectionModel().getSelectedItem();
		System.out.println(equipment + "   equipment:: ");
		if(equipment != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API + "/" + equipment.getEquipmentId(), null);
						if(response != null && response.length() > 0) {
							Equipment e = mapper.readValue(response, Equipment.class);
							EquipmentEditController equipmentEditController = (EquipmentEditController) openEditEquipmentScreen();
							equipmentEditController.initData(e);
						}
					} catch (Exception e) {
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
	
	public void fetchEquipments() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API, null);
					System.out.println(response);
					if(response != null && response.length() > 0) {
						Equipment c[] = mapper.readValue(response, Equipment[].class);
						List<Equipment> cList = new ArrayList<Equipment>();
						for(Equipment ccl : c) {
							cList.add(ccl);
						}
						ObservableList<Equipment> data = FXCollections.observableArrayList(cList);
						
						setColumnValues();
						tblEquipment.setItems(data);
			
			            tblEquipment.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e , "Info", 1);
				}
			}
		});
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
}