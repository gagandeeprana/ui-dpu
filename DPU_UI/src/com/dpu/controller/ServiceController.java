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
import com.dpu.model.DPUService;
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

public class ServiceController extends Application implements Initializable {

	@FXML
	TableView<DPUService> tblService;
	
	@FXML
	TableColumn<DPUService, String> service, textField, associationWith, status;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchServices();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	private void btnAddServiceAction() {
		openAddServiceScreen();
	}
	
	public void fillServices(String response) {
		
		try {
			if(response != null && response.length() > 0) {
				DPUService c[] = mapper.readValue(response, DPUService[].class);
				List<DPUService> services = new ArrayList<DPUService>();
				for(DPUService ccl : c) {
					services.add(ccl);
				}
				ObservableList<DPUService> data = FXCollections.observableArrayList(services);
				
				setColumnValues();
				tblService.setItems(data);
	
	            tblService.setVisible(true);
			}
		} catch (Exception e) {
			System.out.println("ServiceController: fillEquipments(): "+ e.getMessage());
		}
	}
	
	@FXML
	private void btnEditServiceAction() {
		DPUService dpuService = tblService.getSelectionModel().getSelectedItem();
		System.out.println(dpuService + "   service:: ");
		if(dpuService != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + dpuService.getServiceId(), null);
						if(response != null && response.length() > 0) {
							DPUService c = mapper.readValue(response, DPUService.class);
							ServiceEditController serviceEditController = (ServiceEditController) openEditServiceScreen();
							serviceEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	private Object openEditServiceScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.SERVICE_BASE_PACKAGE + Iconstants.XML_SERVICE_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Service");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private void openAddServiceScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.SERVICE_BASE_PACKAGE + Iconstants.XML_SERVICE_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Service");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		service = (TableColumn<DPUService, String>) tblService.getColumns().get(0);
		textField = (TableColumn<DPUService, String>) tblService.getColumns().get(1);
		associationWith = (TableColumn<DPUService, String>) tblService.getColumns().get(2);
		status = (TableColumn<DPUService, String>) tblService.getColumns().get(3);
	}

	public void fetchServices() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API, null);
					DPUService s[] = mapper.readValue(response, DPUService[].class);
					List<DPUService> cList = new ArrayList<DPUService>();
					for(DPUService ccl : s) {
						cList.add(ccl);
					}
					ObservableList<DPUService> data = FXCollections.observableArrayList(cList);
					
					setColumnValues();
					tblService.setItems(data);
		
		            tblService.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	@FXML
	private void btnDeleteServiceAction() {
		DPUService service = tblService.getSelectionModel().getSelectedItem();
		if(service != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + service.getServiceId(), null);
						if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						fetchServices();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	private void setColumnValues() {
		
		service.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getServiceName() + "");
			}
		});
		textField.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getTextField() + "");
			}
		});
		associationWith.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getAssociationWith() + "");
			}
		});
		status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getStatus());
			}
		});

	}
}