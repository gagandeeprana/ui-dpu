package com.dpu.controller;
 
import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.BillingControllerModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BillingAddController extends CompanyAddController{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSaveBillingLocation;
    
   

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtCompany;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtFax;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtZip;


    @FXML
    void btnSaveBillingLocationAction(ActionEvent event) {
    	try{
    		
    	System.out.println("clicked on save Button.");
    	
    	
    	
    	//CompanyAddController companyAddController =  new CompanyAddController();
    	
    	String company = txtCompany.getText();
    	String address = txtAddress.getText();
    	String city = txtCity.getText();
    	String phone = txtPhone.getText();
    	String contact = txtContact.getText();
    	String zip = txtZip.getText();
    	String fax = txtFax.getText();
    	BillingControllerModel bcm1 = new BillingControllerModel(company,address,city,phone,contact,zip,fax);
    	CompanyAddController.listOfBilling.add(bcm1);
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE+Iconstants.XML_COMPANY_ADD_SCREEN));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Company");
        stage.setScene(new Scene(root)); 
        stage.show();
        
     
        
     
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	closeAddBillingScreen(btnSaveBillingLocation);
    	
    	// set Vlaue to text field.
    	/*CompanyAddController companyAdd = new CompanyAddController();
    	companyAdd.setValueToCompanyTextField();*/
    }
    
    private void closeAddBillingScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}

    @FXML
    void initialize() {
        assert btnSaveBillingLocation != null : "fx:id=\"btnSaveBillingLocation\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtCompany != null : "fx:id=\"txtCompany\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtContact != null : "fx:id=\"txtContact\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";
        assert txtZip != null : "fx:id=\"txtZip\" was not injected: check your FXML file 'AddBillingLocationScreen.fxml'.";


    }

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//companyAddController.fetchBillingLocations();
		
		
	}
}