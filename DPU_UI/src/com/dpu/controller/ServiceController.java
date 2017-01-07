package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ServiceController extends Application implements Initializable {

	@FXML
	TableView<Service> tblService;
	
	@FXML
	TableColumn<Service, String> service, textField, associationWith, status;
	
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
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		service = (TableColumn<Service, String>) tblService.getColumns().get(0);
		textField = (TableColumn<Service, String>) tblService.getColumns().get(1);
		associationWith = (TableColumn<Service, String>) tblService.getColumns().get(2);
		status = (TableColumn<Service, String>) tblService.getColumns().get(3);
	}

	private void fetchServices() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API, null);
					Service s[] = mapper.readValue(response, Service[].class);
					List<Service> cList = new ArrayList<Service>();
					for(Service ccl : s) {
						cList.add(ccl);
					}
					ObservableList<Service> data = FXCollections.observableArrayList(cList);
					
					setColumnValues();
					tblService.setItems(data);
		
		            tblService.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		service.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param) {
				return new SimpleStringProperty(param.getValue().getServiceName() + "");
			}
		});
		textField.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param) {
				return new SimpleStringProperty(param.getValue().getServiceResponse() + "");
			}
		});
		associationWith.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param) {
				return new SimpleStringProperty(param.getValue().getServiceId() + "");
			}
		});
		status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param) {
				return new SimpleStringProperty(param.getValue().getStatus() + "");
			}
		});

	}
}