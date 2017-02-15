package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.ProvinceStateResponse;

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

public class ProvinceStateController extends Application implements Initializable{
 
	@FXML
	TableView<ProvinceStateResponse> tblProvinceState;

	@FXML
	TableColumn<ProvinceStateResponse, String> code, name;
	
	@FXML
	private void btnSaveProvinceStateAction() {
		ProvinceStateResponse provinceStateResponse = tblProvinceState.getSelectionModel().getSelectedItem();
		if(provinceStateResponse != null) {
			DriverAddController driverAddController = new DriverAddController();
			driverAddController.txtProvince.setText(provinceStateResponse.getStateName());
		}
	}
	
    @SuppressWarnings("unchecked")
	private void fetchColumns() {
		code = (TableColumn<ProvinceStateResponse, String>) tblProvinceState.getColumns().get(0);
		name = (TableColumn<ProvinceStateResponse, String>) tblProvinceState.getColumns().get(1);
	}
    
    public void fillProvinces(String response) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<ProvinceStateResponse> provinces = new ArrayList<>();
			if(response != null && response.length() > 0) {
				ProvinceStateResponse d[] = mapper.readValue(response, ProvinceStateResponse[].class);
				for(ProvinceStateResponse ddl : d) {
					provinces.add(ddl);
				}
			}
			ObservableList<ProvinceStateResponse> data = FXCollections.observableArrayList(provinces);
			setColumnValues();
			tblProvinceState.setItems(data);
            tblProvinceState.setVisible(true);
		} catch (Exception e) {
			System.out.println("ProvinceStateController: fillProvinces(): "+ e.getMessage());
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}   
	
	private void setColumnValues() {
		code.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProvinceStateResponse,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ProvinceStateResponse, String> param) {
				return new SimpleStringProperty(param.getValue().getStateCode() + "");
			}
		});
		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProvinceStateResponse,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ProvinceStateResponse, String> param) {
				return new SimpleStringProperty(param.getValue().getStateName() + "");
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchProvinces(null);
	}
	
	public void fetchProvinces(String provinceName) {
		
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = null;
					if(provinceName != null) {
						response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_PROVINCE_API + "/" + provinceName + "/", null);
					} else {
						response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_PROVINCE_API, null);
					}
					fillProvinces(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
}