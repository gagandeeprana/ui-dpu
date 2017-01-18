package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Terminal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TerminalAddController extends Application implements Initializable{
	@FXML
	Button btnSaveTerminal;
	
	@FXML
	Button btnCancelTerminal;
	
	@FXML
	TextField txtTerminalName, txtLocation;
	
	@FXML
	ComboBox<Object> ddlAvailableServices;
	
	@FXML
	private void btnSaveTerminalAction() {
		addTerminal();
		closeAddTerminalScreen(btnSaveTerminal);
	}
	
	private void closeAddTerminalScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	@FXML
	private void btnCancelTerminalAction(){
		
	}
	
	private void addTerminal() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Terminal terminal = setTerminalValue();
					String payload = mapper.writeValueAsString(terminal);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
//					TerminalPanelController tm=new TerminalPanelController();
//					tm.fetchTerminals();
					MainScreen.terminalController.fetchTerminals();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ddlAvailableServices.setValue("Service1");
		ddlAvailableServices.setValue("Service2");
		ddlAvailableServices.setValue("Service3");
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private Terminal setTerminalValue() {
		Terminal terminal = new Terminal();
		terminal.setTerminalName(txtTerminalName.getText());
		terminal.setLocation(txtLocation.getText());
		terminal.setAvailableServices(ddlAvailableServices.getSelectionModel().getSelectedItem().toString());		
		return terminal;
	}
}
