package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Shipper;

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

public class ShipperController extends Application implements Initializable {

	@FXML
	TableView<Shipper> tblShipper;
	
	@FXML
	TableColumn<Shipper, String> company, address, unit, city, ps, phone, prefix, tollfree, plant, cellNumber, email, importer;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchShippers();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		company = (TableColumn<Shipper, String>) tblShipper.getColumns().get(0);
		address = (TableColumn<Shipper, String>) tblShipper.getColumns().get(1);
		unit = (TableColumn<Shipper, String>) tblShipper.getColumns().get(2);
		city = (TableColumn<Shipper, String>) tblShipper.getColumns().get(3);
		ps = (TableColumn<Shipper, String>) tblShipper.getColumns().get(4);
		phone = (TableColumn<Shipper, String>) tblShipper.getColumns().get(5);
		prefix = (TableColumn<Shipper, String>) tblShipper.getColumns().get(6);
		tollfree = (TableColumn<Shipper, String>) tblShipper.getColumns().get(7);
		plant = (TableColumn<Shipper, String>) tblShipper.getColumns().get(8);
		cellNumber = (TableColumn<Shipper, String>) tblShipper.getColumns().get(9);
		email = (TableColumn<Shipper, String>) tblShipper.getColumns().get(10);
		importer = (TableColumn<Shipper, String>) tblShipper.getColumns().get(11);
	}

	private void fetchShippers() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SHIPPER_API, null);
					Shipper s[] = mapper.readValue(response, Shipper[].class);
					List<Shipper> cList = new ArrayList<Shipper>();
					for(Shipper ccl : s) {
						cList.add(ccl);
					}
					ObservableList<Shipper> data = FXCollections.observableArrayList(cList);
					
					setColumnValues();
					tblShipper.setItems(data);
		
		            tblShipper.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		company.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getCompany() + "");
			}
		});
		address.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getAddress() + "");
			}
		});
		unit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getUnit() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		ps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getProvinceState() + "");
			}
		});
		phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getPhone() + "");
			}
		});
		prefix.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getPrefix() + "");
			}
		});
		tollfree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getTollFree() + "");
			}
		});
		plant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getPlant() + "");
			}
		});
		cellNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getPhone() + "");
			}
		});
		email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getEmail() + "");
			}
		});
		importer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Shipper,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Shipper, String> param) {
				return new SimpleStringProperty(param.getValue().getImporter() + "");
			}
		});
	}
}
