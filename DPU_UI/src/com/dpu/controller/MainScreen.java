package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScreen extends Application implements Initializable {

//	@FXML
//	MenuItem mnuCompany;
	
	@FXML
	VBox mainScreenVBox;
	
	@FXML
	MenuBar mnuBarDatamaintenance;

	static TruckController truckController;
	
	static CompanyController companyController;
	
	@FXML
	private void miDriverAction() {
		showPanel(Iconstants.DRIVER_BASE_PACKAGE, Iconstants.XML_DRIVER_SCREEN);
	}
	
	@FXML
	private void miDivisionAction() {
		showPanel(Iconstants.DIVISION_BASE_PACKAGE, Iconstants.XML_DIVISION_SCREEN);
	}
	
	@FXML
	private void miCategoryAction() {
		showPanel(Iconstants.CATEGORY_BASE_PACKAGE, Iconstants.XML_CATEGORY_SCREEN);
	}
	
	@FXML
	private void miEquipmentAction() {
		showPanel(Iconstants.EQUIPMENT_BASE_PACKAGE, Iconstants.XML_EQUIPMENT_SCREEN);
	}
	
	@FXML
	private void miServiceAction() {
		showPanel(Iconstants.SERVICE_BASE_PACKAGE, Iconstants.XML_SERVICE_SCREEN);
	}
	
	@FXML
	private void lblTrailerHeaderAction() {
		showPanel(Iconstants.TRAILER_BASE_PACKAGE, Iconstants.XML_TRAILER_SCREEN);
	}
	
	@FXML
	private void miShipperAction() {
		showPanel(Iconstants.SHIPPER_BASE_PACKAGE, Iconstants.XML_SHIPPER_SCREEN);
	}
	
	@FXML
	private void lblTruckHeaderAction() {
		truckController = (TruckController) showPanel(Iconstants.TRUCK_BASE_PACKAGE, Iconstants.XML_TRUCK_SCREEN);
	}
	
	@FXML
	private void lblCompanyHeaderAction() {
		companyController = (CompanyController) showPanel(Iconstants.COMPANY_BASE_PACKAGE, Iconstants.XML_COMPANY_SCREEN);
	}
	
	@FXML
	private void lblDAction() {
		mnuBarDatamaintenance.setVisible(true);
	}
	
	@FXML
	private void miTerminalAction() {
		showPanel(Iconstants.TERMINAL_BASE_PACKAGE, Iconstants.XML_TERMINAL_SCREEN);
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
	        Pane pane  = (Pane)root;
	        
	        ObservableList<Node> nodes = mainScreenVBox.getChildren();
	        
	        if(nodes!= null && nodes.size() >= 4 && nodes.get(3) != null) {
	        	nodes.remove(3);
	        	nodes.add(3, pane);
	        } else {
	        	nodes.add(pane);
	        }
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
}
