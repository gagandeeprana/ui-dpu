package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Driver;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DriverPanelController extends Application implements Initializable {

	@FXML
	Button btnAdd;

	@FXML
	TableView<Driver> driverTable;
	
	@FXML
	private TableColumn<Driver, String> driverCode, firstName, lastName, address, unit, city, pvs, postalCode, home,faxNo,cellular,pager,email,classId;

	@FXML
	public void btnAddAction() {
		try {
			openAddScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Save Driver
	@FXML
	Button btnSave;
	
	@FXML
	public void btnSaveAction() {
		try {
			saveDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Cancel screen
	@FXML
	Button btnCancel;
	
	@FXML
	public void btnCancelAction() {
		try {
			cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			System.out.println("[[initialize]]: intialize method called");
			fetchColumns();
			Thread t = new Thread(new DriverThread());
			System.out.println(t);
			t.start();

		} catch (Exception e) {
			System.out.println("[initialize] Exception : "+e);
			// TODO: handle exception
		}
	}

	// Save Driver
	private void saveDriver() {
		try {
			System.out.println("[Save]: Enter");
			
			driverCode.getText();
			System.out.println("[Save]: Enter"+driverCode.getText());
			 System.out.println("[Save]: Driver saved successsfully");
			 
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	// Cancel screen
	private void cancel() {
		try {
			 System.out.println("[Cancel] : Driver Screen Cancel successsfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void openAddScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource(Iconstants.DRIVER_BASE_PACKAGE + Iconstants.ADD_DRIVER_SCREEN));
			Parent root = (Parent) fxmlLoader.load();
			// AnchorPane vBox = (AnchorPane)root;
			// MenuBar mnuBar = (MenuBar)vBox.getChildren().get(0);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("MainScreen");
			stage.setScene(new Scene(root));
			// stage.setMaximized(true);
			// mnuBar.prefWidthProperty().bind(stage.widthProperty());
			// fxmlLoader.setController(MainScreen.class);
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			System.out.println("start");
			Parent parent = FXMLLoader
					.load(getClass().getClassLoader().getResource(Iconstants.DRIVER_BASE_PACKAGE +Iconstants.XML_DRIVER_SCREEN));
			System.out.println("XML : "+Iconstants.DRIVER_BASE_PACKAGE+Iconstants.XML_DRIVER_SCREEN);
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("main method called");
		launch(args);
	}
	// new added
	
			@SuppressWarnings("unchecked")
			private void fetchColumns() {
				try{
				System.out.println("[fetchColumns]: Enter ");
				driverCode = (TableColumn<Driver, String>) driverTable.getColumns().get(0);
				
				firstName = (TableColumn<Driver, String>) driverTable.getColumns().get(1);
				lastName = (TableColumn<Driver, String>) driverTable.getColumns().get(2);
				address = (TableColumn<Driver, String>) driverTable.getColumns().get(3);
				unit = (TableColumn<Driver, String>) driverTable.getColumns().get(4);
				city = (TableColumn<Driver, String>) driverTable.getColumns().get(5);
				pvs = (TableColumn<Driver, String>) driverTable.getColumns().get(6);
				postalCode = (TableColumn<Driver, String>) driverTable.getColumns().get(7);
				home = (TableColumn<Driver, String>) driverTable.getColumns().get(8);
				
				faxNo = (TableColumn<Driver, String>) driverTable.getColumns().get(9);
				cellular = (TableColumn<Driver, String>) driverTable.getColumns().get(10);
				pager = (TableColumn<Driver, String>) driverTable.getColumns().get(11);
				email = (TableColumn<Driver, String>) driverTable.getColumns().get(12);
				classId=(TableColumn<Driver, String>) driverTable.getColumns().get(13);
				System.out.println("[fetchColumns]: Exit ");
				}catch(Exception e){
					System.out.println("[fetchColumns]:Exception "+e);
				}
				
				
				
			}

	
	// Add One More class as Thread Class
	class DriverThread implements Runnable {

		 
		@Override
		public void run() {

//			try {
//				String response = GetAPIClient.callGetAPI(Iconstants.URL_GET_ALL_DRIVER_LIST, null);
//				ObjectMapper mapper = new ObjectMapper();
//				Driver d[] = mapper.readValue(response, Driver[].class);
//				
//				List<Driver> cList = new ArrayList<Driver>();
//				for(Driver ccl : d) {
//					cList.add(ccl);
//				}
//				ObservableList<Driver> data = FXCollections.observableArrayList(cList);
//				
//				setColumnValues();
//				driverTable.setItems(data);
//
//				driverTable.setVisible(true);
//
//			 
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	 
		private void setColumnValues() {
			driverCode.setCellValueFactory(new PropertyValueFactory<Driver, String>("driverCode"));
			firstName.setCellValueFactory(new PropertyValueFactory<Driver, String>("firstName"));
			lastName.setCellValueFactory(new PropertyValueFactory<Driver, String>("lastName"));
			address.setCellValueFactory(new PropertyValueFactory<Driver, String>("address"));
			unit.setCellValueFactory(new PropertyValueFactory<Driver, String>("unit"));
			city.setCellValueFactory(new PropertyValueFactory<Driver, String>("city"));
			pvs.setCellValueFactory(new PropertyValueFactory<Driver, String>("pvs"));
			postalCode.setCellValueFactory(new PropertyValueFactory<Driver, String>("postalCode"));
			home.setCellValueFactory(new PropertyValueFactory<Driver, String>("home"));
			faxNo.setCellValueFactory(new PropertyValueFactory<Driver, String>("faxNo"));
			cellular.setCellValueFactory(new PropertyValueFactory<Driver, String>("cellular"));
			pager.setCellValueFactory(new PropertyValueFactory<Driver, String>("pager"));
			email.setCellValueFactory(new PropertyValueFactory<Driver, String>("email"));
			classId.setCellValueFactory(new PropertyValueFactory<Driver, String>("classId"));


		}
		
		

	}
}
