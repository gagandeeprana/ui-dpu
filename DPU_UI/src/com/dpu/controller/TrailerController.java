package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Trailer;

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

public class TrailerController extends Application implements Initializable {

	@FXML
	TableView<Trailer> tblTrailer;
	
	@FXML
	TableColumn<Trailer, String> unitNo, owner, oOName, category, status, usage, division, terminal, trailerType, finance;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchTrailers();
	}
	
	@FXML
	private void btnAddTrailerAction() {
		openAddTrailerScreen();
	}
	
	private void openAddTrailerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.TRAILER_BASE_PACKAGE + Iconstants.XML_TRAILER_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Trailer");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(0);
		owner = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(1);
		oOName = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(2);
		category = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(3);
		status = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(4);
		usage = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(5);
		division = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(6);
		terminal = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(7);
		trailerType = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(8);
		finance = (TableColumn<Trailer, String>) tblTrailer.getColumns().get(9);
	}
	
	private void fetchTrailers() {
		
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API, null);
					Trailer c[] = mapper.readValue(response, Trailer[].class);
					List<Trailer> tList = new ArrayList<Trailer>();
					for(Trailer Trailer : c) {
						tList.add(Trailer);
					}
					ObservableList<Trailer> data = FXCollections.observableArrayList(tList);
					
					setColumnValues();
					tblTrailer.setItems(data);
		
					tblTrailer.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		unitNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getTrailerId() + "");
			}
		});
		owner.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getCurrentOdometer() + "");
			}
		});
		oOName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
					
					@Override
					public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
						return new SimpleStringProperty(param.getValue().getPlateNo() + "");
					}
				});
		category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getCurrentOdometer() + "");
			}
		});
		status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getModel() + "");
			}
		});
		usage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getTareWeight() + "");
			}
		});
		division.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getPlateNo() + "");
			}
		});
		terminal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getTrailerId() + "");
			}
		});
		trailerType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getEquipmentId() + "");
			}
		});
		finance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Trailer,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Trailer, String> param) {
				return new SimpleStringProperty(param.getValue().getRgw() + "");
			}
		});
	}
}