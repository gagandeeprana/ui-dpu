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
	
	
	/*@FXML
	private void mnuCompanyAction() {
		showCompanyPanel();
	}*/

	@FXML
	private void lblDAction() {
		mnuBarDatamaintenance.setVisible(true);
	}
	
	@FXML
	private void miTerminalAction() {
		showTerminalPanel();
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	private void showTerminalPanel() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.TERMINAL_BASE_PACKAGE + Iconstants.XML_TERMINAL_SCREEN));
	        Parent root = (Parent) fxmlLoader.load();
	        Pane pane  = (Pane)root;
	        
	        ObservableList<Node> nodes = mainScreenVBox.getChildren();
	        nodes.add(pane);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
