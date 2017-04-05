package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ValidationController extends Application implements Initializable {

	@FXML
	public Text txtMsg;

	@FXML
	Button btnGotIt;

	public static String str = "";

	@FXML
	private void btnGotItAction() {
		closeAddServiceScreen(btnGotIt);
	}

	private void closeAddServiceScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtMsg.setText(str);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
