package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Truck;

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

public class TruckController extends Application implements Initializable {

	@FXML
	TableView<Truck> tblTruck;
	
	@FXML
	TableColumn<Truck, String> unitNo, owner, oOName, category, status, usage, division, terminal, truckType, finance;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchTrucks();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Truck, String>) tblTruck.getColumns().get(0);
		owner = (TableColumn<Truck, String>) tblTruck.getColumns().get(1);
		oOName = (TableColumn<Truck, String>) tblTruck.getColumns().get(2);
		category = (TableColumn<Truck, String>) tblTruck.getColumns().get(3);
		status = (TableColumn<Truck, String>) tblTruck.getColumns().get(4);
		usage = (TableColumn<Truck, String>) tblTruck.getColumns().get(5);
		division = (TableColumn<Truck, String>) tblTruck.getColumns().get(6);
		terminal = (TableColumn<Truck, String>) tblTruck.getColumns().get(7);
		truckType = (TableColumn<Truck, String>) tblTruck.getColumns().get(8);
		finance = (TableColumn<Truck, String>) tblTruck.getColumns().get(9);
	}
	
	private void fetchTrucks() {
		
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_API, null);
					Truck c[] = mapper.readValue(response, Truck[].class);
					List<Truck> tList = new ArrayList<Truck>();
					for(Truck truck : c) {
						tList.add(truck);
					}
					ObservableList<Truck> data = FXCollections.observableArrayList(tList);
					
					setColumnValues();
					tblTruck.setItems(data);
		
					tblTruck.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		unitNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getUnitNo() + "");
			}
		});
		owner.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getOwnerId() + "");
			}
		});
		oOName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
					
					@Override
					public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
						return new SimpleStringProperty(param.getValue().getPlateNo() + "");
					}
				});
		category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getCurrentOdometer() + "");
			}
		});
		status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getStatus() + "");
			}
		});
		usage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getTareWeight() + "");
			}
		});
		division.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getPlateNo() + "");
			}
		});
		terminal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getTruckClass() + "");
			}
		});
		truckType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getTruckClass() + "");
			}
		});
		finance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Truck,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Truck, String> param) {
				return new SimpleStringProperty(param.getValue().getRgw() + "");
			}
		});
	}
}