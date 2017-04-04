package com.dpu.controller.planner;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Driver;
import com.dpu.model.Trailer;
import com.dpu.model.Truck;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PlannerController<T> extends Application implements Initializable {

	@FXML
	TableColumn<T, String> list;
	
	@FXML
	Tab tabPaneTrucks;
	
	@FXML
	private void tabPaneTrucksSelection() {
		fetchLists(1);
	}
	
	@FXML
	TableView tblLists;
	
	@FXML
	private void tabPaneTrailersSelection() {
		fetchLists(2);
	}
	
	@FXML
	private void tabPaneDriversSelection() {
		fetchLists(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchLists(0);
		LocalDate localDate = LocalDate.now();
//		int start
//		createTableColumns(
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		try {
			list = (TableColumn<T, String>) tblLists.getColumns().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fetchLists(Integer value) {
		
		fetchColumns();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = null;
					switch (value) {
						case 0:
							response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_SPECIFIC_API, null);
							break;
						case 1:
							response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRUCK_SPECIFIC_API, null);
							break;
						case 2:
							response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_SPECIFIC_API, null);
							break;
					}
					fillLists(response, value);
				} catch (Exception e) {
				}
			}
		});
	}
	
	@FXML
	TableView<String> tblPlanner;
	
	private void createTableColumns(int startYr, int startDt, int startMonth, int endYr, int endDt, int endMonth) {
		LocalDate start = LocalDate.of(startYr, startDt, startMonth);
		LocalDate end = LocalDate.of(endYr, endDt, endMonth);
		List<String> ret = datesBetween(start, end);
		
		for(int i=0;i<ret.size();i++) {
			TableColumn<String, String> tblColumn = new TableColumn<String, String>(ret.get(i));
			tblPlanner.getColumns().add(i, tblColumn);
		}
	}
	
	public static List<String> datesBetween(LocalDate start, LocalDate end) {
		List<String> ret = new ArrayList<String>();
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			String dt = date.getDayOfMonth() + "";
			String month = date.getMonth() + "";
			String day = date.getDayOfWeek() + "";
			ret.add(dt + "-" + month + " (" + day + ")");
		}
		return ret;
	}
	
	List<Driver> drivers = null;
	List<Truck> trucks = null;
	List<Trailer> trailers = null;
	ObjectMapper mapper = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public void fillLists(String response, Integer value) {
		
		try {
			ObservableList<T> data = null;
			switch (value) {
				case 0:
					drivers = new ArrayList<Driver>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						Driver c[] = mapper.readValue(response, Driver[].class);
						for(Driver ccl : c) {
							drivers.add(ccl);
						}
						data = (ObservableList<T>) FXCollections.observableArrayList(drivers);
					} else {
						data = (ObservableList<T>) FXCollections.observableArrayList(drivers);
					}
					tblLists.setItems(data);
					tblLists.setVisible(true);
					break;
				case 1:
					trucks = new ArrayList<Truck>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						System.out.println("truck response: " + response);
						Truck c[] = mapper.readValue(response, Truck[].class);
						for(Truck ccl : c) {
							trucks.add(ccl);
						}
						data = (ObservableList<T>) FXCollections.observableArrayList(trucks);
					} else {
						data = (ObservableList<T>) FXCollections.observableArrayList(trucks);
					}
					tblLists.setItems(data);
					tblLists.setVisible(true);
					break;
				case 2:
					trailers = new ArrayList<Trailer>();
					setColumnValues(value);
					if(response != null && response.length() > 0) {
						Trailer c[] = mapper.readValue(response, Trailer[].class);
						for(Trailer ccl : c) {
							trailers.add(ccl);
						}
						data = (ObservableList<T>) FXCollections.observableArrayList(trailers);
					} else {
						data = (ObservableList<T>) FXCollections.observableArrayList(trailers);
					}
					tblLists.setItems(data);
					tblLists.setVisible(true);
					break;
			}
		} catch (Exception e) {
			System.out.println("PlannerController: fillLists(): "+ e.getMessage());
		}
	}
	
	private void setColumnValues(Integer value) {
			
		switch (value) {
		case 0:
			list.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<T, String> param) {
					CellDataFeatures<Driver, String> paramDriver = (CellDataFeatures<Driver, String>) param;
					return new SimpleStringProperty(paramDriver.getValue().getFullName() + "");
				}
			});
			break;
		case 1:
			/*if(drivers != null && drivers.size() > 0) {
				list.getColumns().removeAll(drivers);
			}*/
			list.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<T, String> param) {
					CellDataFeatures<Truck, String> paramTruck = (CellDataFeatures<Truck, String>) param;
					return new SimpleStringProperty(paramTruck.getValue().getOwner() + "");
				}
			});
			break;

		case 2:
			list.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<T, String> param) {
					CellDataFeatures<Trailer, String> paramTrailer = (CellDataFeatures<Trailer, String>) param;
					return new SimpleStringProperty(paramTrailer.getValue().getOwner() + "");
				}
			});
			break;

		}
		
	}
}
