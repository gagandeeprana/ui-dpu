package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Trailer;
import com.dpu.model.Truck;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TrailerAddController extends Application implements Initializable{

	@FXML
	Button btnSaveTrailer;

	@FXML
	private void btnSaveTrailerAction() {
		addTrailer();
		closeAddTruckScreen(btnSaveTrailer);
	}
	
	private void addTrailer() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Trailer trailer = setTrailerValue();
					String payload = mapper.writeValueAsString(trailer);
					System.out.println(payload);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.trailerController.fetchTrailers();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
	}
	
	private Trailer setTrailerValue() {
		Trailer trailer = new Trailer();
		trailer.setUnitNo(Integer.parseInt(txtUnitNo.getText()));
		return trailer;
	}

	private void closeAddTruckScreen(Button btnSaveTrailer2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}