package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScreen extends Application implements Initializable {

	// @FXML
	// MenuItem mnuCompany;

	@FXML
	VBox mainScreenVBox;

	@FXML
	MenuBar mnuBarDatamaintenance;

	static TruckController truckController;

	static TrailerController trailerController;

	static ShipperController shipperController;

	static CompanyController companyController;
	
	static TerminalPanelController terminalController;
	
	static DivisionController divisionController;

	static ServiceController serviceController;

	
	static DriverController driverController;
	
	static CategoryController categoryController;
	
	static EquipmentController equipmentController;


	@FXML
	private void miDriverAction() {
		driverController = (DriverController) showPanel(Iconstants.DRIVER_BASE_PACKAGE, Iconstants.XML_DRIVER_SCREEN);
	}

	@FXML
	private void miDivisionAction() {
		divisionController = (DivisionController) showPanel(Iconstants.DIVISION_BASE_PACKAGE, Iconstants.XML_DIVISION_SCREEN);
	}

	@FXML
	private void miCategoryAction() {
		categoryController = (CategoryController)showPanel(Iconstants.CATEGORY_BASE_PACKAGE, Iconstants.XML_CATEGORY_SCREEN);
	}

	@FXML
	private void miEquipmentAction() {
		equipmentController = (EquipmentController) showPanel(Iconstants.EQUIPMENT_BASE_PACKAGE, Iconstants.XML_EQUIPMENT_SCREEN);
	}

	@FXML
	private void miServiceAction() {
		serviceController = (ServiceController) showPanel(Iconstants.SERVICE_BASE_PACKAGE, Iconstants.XML_SERVICE_SCREEN);
	}

	@FXML
	private void miTrailersAction() {
		trailerController = (TrailerController) showPanel(Iconstants.TRAILER_BASE_PACKAGE, Iconstants.XML_TRAILER_SCREEN);
	}

	@FXML
	private void lblLocationsHeaderAction() {
		shipperController = (ShipperController) showPanel(Iconstants.SHIPPER_BASE_PACKAGE, Iconstants.XML_SHIPPER_SCREEN);
	}

	@FXML
	private void miTrucksAction() {
		truckController = (TruckController) showPanel(Iconstants.TRUCK_BASE_PACKAGE, Iconstants.XML_TRUCK_SCREEN);
	}

	@FXML
	private void lblCompanyHeaderAction() {
		companyController = (CompanyController) showPanel(Iconstants.COMPANY_BASE_PACKAGE,
				Iconstants.XML_COMPANY_SCREEN);
	}

	@FXML
	private void lblDAction() {
		mnuBarDatamaintenance.setVisible(true);
	}

	@FXML
	private void miTerminalAction() {
		terminalController = (TerminalPanelController)showPanel(Iconstants.TERMINAL_BASE_PACKAGE, Iconstants.XML_TERMINAL_SCREEN);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	private Object showPanel(String basePackage, String screenName) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(basePackage + screenName));
			Parent root = (Parent) fxmlLoader.load();
			Pane pane = (Pane) root;
			StackPane stackPane = new StackPane();
			stackPane.setMinWidth(Login.width);
			stackPane.setPrefWidth(Login.width);
			stackPane.setMaxWidth(Login.width);
			stackPane.setAlignment(Pos.TOP_LEFT);
			stackPane.getChildren().add(pane);

			ObservableList<Node> nodes = mainScreenVBox.getChildren();

			if (nodes != null && nodes.size() >= 4 && nodes.get(3) != null) {
//				ProgressIndicator pi = new ProgressIndicator();
//				VBox box = new VBox(pi);
//				box.setAlignment(Pos.CENTER);
//				// Grey Background
//				pane.setDisable(true);
//				stackPane.getChildren().add(box);
				nodes.remove(3);
				nodes.add(3, stackPane);
				
			} else {
//				ProgressIndicator pi = new ProgressIndicator();
//				VBox box = new VBox(pi);
//				box.setAlignment(Pos.CENTER);
//				// Grey Background
//				pane.setDisable(true);
//				stackPane.getChildren().add(box);
				nodes.add(stackPane);
			}
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
}
