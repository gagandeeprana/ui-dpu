package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompanyAddController extends Application implements Initializable{

	@FXML
	Button btnSaveCompany;
	
	@FXML
	TextField txtCompany, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtZip, txtAfterHours, txtEmail, txtTollFree, txtWebsite, txtCellular, txtPager;
	
	@FXML
	private void btnSaveCompanyAction() {
		addCompany();
		closeAddCompanyScreen(btnSaveCompany);
	}
	
	private void closeAddCompanyScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addCompany() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Company company = setCompanyValue();
					String payload = mapper.writeValueAsString(company);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null, payload);
					
					/*FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_SCREEN));
					CompanyController c = (CompanyController) fxmlLoader.getController();
					c.fetchCompanies();*/
					MainScreen.companyController.fetchCompanies();
					JOptionPane.showMessageDialog(null, response , "Info", 1);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private Company setCompanyValue() {
		Company company = new Company();
		company.setName(txtCompany.getText());
		company.setContact(txtContact.getText());
		company.setAddress(txtAddress.getText());
		company.setPosition(txtPosition.getText());
		company.setUnitNo(txtUnitNo.getText());
		company.setPhone(txtPhone.getText());
		company.setExt(txtExt.getText());
		company.setCity(txtCity.getText());
		company.setFax(txtFax.getText());
		company.setCompanyPrefix(txtPrefix.getText());
		company.setProvinceState(txtProvince.getText());
		company.setZip(txtZip.getText());
		company.setAfterHours(txtAfterHours.getText());
		company.setEmail(txtEmail.getText());
		company.setTollfree(txtTollFree.getText());
		company.setWebsite(txtWebsite.getText());
		company.setCellular(txtCellular.getText());
		company.setPager(txtPager.getText());
		return company;
	}
}