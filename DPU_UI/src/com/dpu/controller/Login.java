package com.dpu.controller;
	
import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login extends Application implements Initializable{
	
	@FXML
	TextField txtUsername;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	Button btnLogin;
	
	@FXML
	Button btnCancel;
	
	@FXML
	MenuBar mnuBarDatamaintenance;
	
	@FXML
	private void btnLoginAction() {
		try {
			authenticateUser();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnCancelAction() {
		try {
			closeLoginScreen(btnCancel);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void start(Stage primaryStage) { 
		try {
			Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_LOGIN_SCREEN));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtUsername.setText("admin");
		txtPassword.setText("admin");
	}
	
	private void closeLoginScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void authenticateUser() throws Exception{
		String un  = txtUsername.getText();
		String pwd = txtPassword.getText();
		if(un.equals("admin") && pwd.equals("admin")) {
			openMainScreen();
			closeLoginScreen(btnLogin);
		} else {
			showDialog("Message", null, "Invalid Credentials");
		}
	}
	
	private void openMainScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_MAIN_SCREEN));
	        Parent root = (Parent) fxmlLoader.load();
	        VBox vBox  = (VBox)root;
	        MenuBar mnuBar = (MenuBar) vBox.getChildren().get(0);
	        MenuBar mnuBarDatamaintenance = (MenuBar) vBox.getChildren().get(2);
	        mnuBarDatamaintenance.setVisible(false);
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	//        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("Dashboard");
	        stage.setScene(new Scene(root)); 
	        stage.setMaximized(true);
	        mnuBar.prefWidthProperty().bind(stage.widthProperty());
	//        fxmlLoader.setController(MainScreen.class);
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void showDialog(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		alert.show();
	}
}
