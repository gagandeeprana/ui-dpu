package com.dpu.controller;
 
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.BillingLocations;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BillingAddController extends Application implements Initializable{
	
	

	@FXML
	Button btnSaveBillingLocation;
	
	@FXML
	TextField txtCompany, txtAddress,txtCity,txtPhone,txtContact,txtZip,txtFax;
	
	 
	@FXML
	private void btnSaveBillingLocationAction() {
		 
			addBillingLocationDetail();
			closeAddBillingScreen(btnSaveBillingLocation);
		 
	}
	
	private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	 
	private void addBillingLocationDetail() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					BillingLocations billingLocation = setBillingLocationsValue();
					String payload = mapper.writeValueAsString(billingLocation);
					
					System.out.println("Add Payload: " + payload);
					 
					String response = "[{\"billingLocationId\":18,\"name\":\"XXX\",\"address\":\"XXX\",\"city\":\"XXX\",\"zip\":\"0000\",\"status\":1,\"contact\":\"XXX\",\"position\":\"XXX\",\"email\":\"XXX\",\"cellular\":\"(868) 686-8686\",\"phone\":\"(777) 777-7777\",\"ext\":\"7777\",\"fax\":\"(876) 876-8768\",\"tollfree\":\"(___) ___-____\"},{\"billingLocationId\":24,\"name\":\"AAA\",\"address\":\"AAA\",\"city\":\"AAA\",\"zip\":\"6786\",\"status\":1,\"contact\":\"AAA\",\"position\":\"AAA\",\"email\":\"AAA\",\"cellular\":\"(868) 686-8686\",\"phone\":\"(777) 777-7777\",\"ext\":\"7777\",\"fax\":\"(876) 876-8768\",\"tollfree\":\"(___) ___-____\"},{\"billingLocationId\":25,\"name\":\"AAA\",\"address\":\"AAA\",\"city\":\"AAA\",\"zip\":\"6786\",\"status\":1,\"contact\":\"AAA\",\"position\":\"AAA\",\"email\":\"AAA\",\"cellular\":\"(868) 686-8686\",\"phone\":\"(777) 777-7777\",\"ext\":\"7777\",\"fax\":\"(876) 876-8768\",\"tollfree\":\"(___) ___-____\"}]";
					System.out.println(response);
					//MainScreen.equipmentController.fillEquipments(response);
					CompanyAddController.companyAddController.fetchBillingLocations(response);

 
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//fetchTypes();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private BillingLocations setBillingLocationsValue() {
		BillingLocations billingLocation = new BillingLocations();
		billingLocation.setName(txtCompany.getText());
		billingLocation.setAddress(txtAddress.getText());
		billingLocation.setCity(txtCity.getText());
		billingLocation.setPhone(txtPhone.getText());
		billingLocation.setContact(txtContact.getText());
		billingLocation.setZip(txtZip.getText());
		billingLocation.setFax(txtFax.getText());
		return billingLocation;
	}
}
