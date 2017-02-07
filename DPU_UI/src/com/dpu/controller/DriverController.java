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
import com.dpu.model.Driver;
import com.dpu.model.Failed;
import com.dpu.model.Success;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DriverController extends Application implements Initializable {

	@FXML
	TableView<Driver> tblDriver;
	
	public List<Driver> dList = null;
	
	@FXML
	TableColumn<Driver, String> driverCode, firstName, lastName, address, unit, city, province, postalCode, home,
	faxNo, cellular, pager, email, driverClass;
	
	@FXML
	TextField txtSearchDriver;
	
	@FXML
	public void btnAddDriverAction() {
		openAddDriverScreen();
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void fillDriver(String response) {
		
		try {
			ObservableList<Driver> data = null;
			dList = new ArrayList<Driver>();
			setColumnValues();
			if(response != null && response.length() > 0) {
				Driver c[] = mapper.readValue(response, Driver[].class);
				for(Driver ccl : c) {
					dList.add(ccl);
				}
				data = FXCollections.observableArrayList(dList);
			} else {
				data = FXCollections.observableArrayList(dList);
			}
			tblDriver.setItems(data);
            tblDriver.setVisible(true);
		} catch (Exception e) {
			System.out.println("DriverController: fillDriver(): "+ e.getMessage());
		}
	}
	
	@FXML
	private void btnGoDriverAction() {
		String searchDriver = txtSearchDriver.getText();
		if(searchDriver != null && searchDriver.length() > 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + searchDriver + "/search", null);
						fillDriver(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
		
		if(searchDriver != null && searchDriver.length() == 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API, null);
						fillDriver(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
	}

	private void openAddDriverScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.DRIVER_BASE_PACKAGE + Iconstants.XML_DRIVER_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Driver");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchDrivers();	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	}
	
	public void fetchDrivers() {
		
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();

					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API, null);
					if(response != null && response.length() > 0) {
						Driver d[] = mapper.readValue(response, Driver[].class);
						dList = new ArrayList<Driver>();
						for(Driver ddl : d) {
							dList.add(ddl);
						}
						ObservableList<Driver> data = FXCollections.observableArrayList(dList);
						
						setColumnValues();
						tblDriver.setItems(data);
			
			            tblDriver.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		driverCode = (TableColumn<Driver, String>) tblDriver.getColumns().get(0);
		firstName = (TableColumn<Driver, String>) tblDriver.getColumns().get(1);
		lastName = (TableColumn<Driver, String>) tblDriver.getColumns().get(2);
		address = (TableColumn<Driver, String>) tblDriver.getColumns().get(3);
		unit = (TableColumn<Driver, String>) tblDriver.getColumns().get(4);
		city = (TableColumn<Driver, String>) tblDriver.getColumns().get(5);
		province = (TableColumn<Driver, String>) tblDriver.getColumns().get(6);
		postalCode = (TableColumn<Driver, String>) tblDriver.getColumns().get(7);
		home = (TableColumn<Driver, String>) tblDriver.getColumns().get(8);
		faxNo = (TableColumn<Driver, String>) tblDriver.getColumns().get(9);
		cellular = (TableColumn<Driver, String>) tblDriver.getColumns().get(10);
		pager = (TableColumn<Driver, String>) tblDriver.getColumns().get(11);
		email = (TableColumn<Driver, String>) tblDriver.getColumns().get(12);
		driverClass = (TableColumn<Driver, String>) tblDriver.getColumns().get(13);
	}
	
	@FXML
	private void btnDeleteDriverAction() {
		Driver driver = tblDriver.getSelectionModel().getSelectedItem();
		if(driver != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driver.getDriverId(), null);
						MainScreen.driverController.fillDriver(response);
						/*if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}*/
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
	private void btnEditDriverAction() {
		Driver driver = tblDriver.getSelectionModel().getSelectedItem();
		System.out.println(driver + "   driver:: ");
		if(driver != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DRIVER_API + "/" + driver.getDriverId(), null);
						if(response != null && response.length() > 0) {
							Driver c = mapper.readValue(response, Driver.class);
							DriverEditController driverEditController = (DriverEditController) openEditDriverScreen();
							driverEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	private Object openEditDriverScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.DRIVER_BASE_PACKAGE + Iconstants.XML_DRIVER_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Driver");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private void setColumnValues() {
		
		driverCode.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getDriverCode() + "");
			}
		});
		firstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getFirstName() + "");
			}
		});
		lastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getLastName() + "");
			}
		});
		address.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getAddress() + "");
			}
		});
		unit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getUnit() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		province.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getPvs() + "");
			}
		});
		postalCode.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getPostalCode() + "");
			}
		});
		home.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getHome() + "");
			}
		});
		faxNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getFaxNo() + "");
			}
		});
		cellular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getCellular() + "");
			}
		});
		pager.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getPager() + "");
			}
		});
		email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getEmail() + "");
			}
		});
		driverClass.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Driver,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Driver, String> param) {
				return new SimpleStringProperty(param.getValue().getDriverClassName() + "");
			}
		});
	}

	// ADD MENU
	
		public int tblDriverMenuCount = 0;
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
			if (tblDriverMenuCount == 0) {
				tblDriverMenuCount++;
				// When user right-click on Table
				tblDriver.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
					@Override
					public void handle(ContextMenuEvent event) {
						contextMenu.show(tblDriver, event.getScreenX(), event.getScreenY());

					}

				});

			}

		}

}