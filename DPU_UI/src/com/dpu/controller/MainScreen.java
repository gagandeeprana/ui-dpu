package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
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
		System.out.println("111111");
		mnuBarDatamaintenance.setVisible(true);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	/*private void showCompanyPanel() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CompanyPanel.fxml"));
	        Parent root = (Parent) fxmlLoader.load();
	        Pane pane  = (Pane)root;
	        
	        ObservableList<Node> nodes = mainScreenVBox.getChildren();
	        nodes.add(pane);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}*/
}
