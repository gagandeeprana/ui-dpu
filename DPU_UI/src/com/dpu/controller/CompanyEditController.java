package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.request.CompanyModel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompanyEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateCompany;
	
	Integer companyId = 0;
	
	@FXML
	TextField txtCompany, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtZip, txtAfterHours, txtEmail, txtTollFree, txtWebsite, txtCellular, txtPager;
	
	@FXML
	private void btnUpdateCompanyAction() {
		editCompany();
		closeEditCompanyScreen(btnUpdateCompany);
		
	}
	
	private void closeEditCompanyScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editCompany() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Company company = setCompanyValue();
					String payload = mapper.writeValueAsString(company);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + companyId, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.companyController.fetchCompanies();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
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
		//company.setCompanyId(companyId);
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

	public void initData(CompanyModel c) {
		companyId = Integer.parseInt(c.getCompanyId());
		txtCompany.setText(c.getName());
		txtContact.setText(c.getContact());
		txtAddress.setText(c.getAddress());
		txtPosition.setText(c.getPosition());
		txtUnitNo.setText(c.getUnitNo());
		txtPhone.setText(c.getPhone());
		txtExt.setText(c.getExt());
		txtCity.setText(c.getCity());
		txtFax.setText(c.getFax());
		txtPrefix.setText(c.getCompanyPrefix());
		txtProvince.setText(c.getProvinceState());
		txtZip.setText(c.getZip());
		txtAfterHours.setText(c.getAfterHours());
		txtEmail.setText(c.getEmail());
		txtTollFree.setText(c.getTollfree());
		txtWebsite.setText(c.getWebsite());
		txtCellular.setText(c.getCellular());
		txtPager.setText(c.getPager());
	}
}