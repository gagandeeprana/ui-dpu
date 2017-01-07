package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;
import com.dpu.model.Equipment;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EquipmentController extends Application implements Initializable {

	@FXML
	TableView<Equipment> tblEquipment;
	
	@FXML
	TableColumn<Equipment, String> type, description;
	
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
		type = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(0);
		description = (TableColumn<Equipment, String>) tblEquipment.getColumns().get(1);
	}

	private void fetchEquipments() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_EQUIPMENT_API, null);
					Equipment c[] = mapper.readValue(response, Equipment[].class);
					List<Equipment> cList = new ArrayList<Equipment>();
					for(Equipment ccl : c) {
						cList.add(ccl);
					}
					ObservableList<Equipment> data = FXCollections.observableArrayList(cList);
					
					setColumnValues();
					tblEquipment.setItems(data);
		
		            tblEquipment.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Equipment, String> param) {
				return new SimpleStringProperty(param.getValue().getEquipmentName() + "");
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