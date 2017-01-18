/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author jagvir
 *
 */
public class DivisionEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateDivision,btnCancel;

	int divisionId = 0;

	@FXML
	TextField txtDivisionName, txtDivisionCode, txtFedral, txtProvincial, txtSCAC, txtCarrierCode, txtContractPrefix,
			txtInvoicePrefix;

	@FXML
	CheckBox chkIncludeInManagementReporting, chkIncludeInAccountingTransfers;

	@FXML
	ComboBox ddlStatus;

	@FXML
	private void btnUpdateDivisionAction() {
		editDivision();
		closeEditDivisionScreen(btnUpdateDivision);

	}

	private void closeEditDivisionScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editDivision() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Division division = setDivisionValue();
					String payload = mapper.writeValueAsString(division);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + divisionId, null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
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

	private Division setDivisionValue() {
		Division division = new Division();
		division.setDivisionId(divisionId);
		division.setCarrierCode(txtCarrierCode.getText());
		division.setContractPrefix(txtContractPrefix.getText());
		division.setDivisionCode(txtDivisionCode.getText());
		division.setDivisionName(txtDivisionName.getText());
		division.setFedral(txtFedral.getText());
		division.setInvoicePrefix(txtInvoicePrefix.getText());
		division.setProvincial(txtProvincial.getText());
		division.setSCAC(txtSCAC.getText());
		return division;
	}

	public void initData(Division d) {
		divisionId = d.getDivisionId();
		txtCarrierCode.setText(d.getCarrierCode());
		txtContractPrefix.setText(d.getContractPrefix());
		txtDivisionCode.setText(d.getDivisionName());
		txtDivisionName.setText(d.getDivisionName());
		txtFedral.setText(d.getFedral());
		txtInvoicePrefix.setText(d.getInvoicePrefix());
		txtProvincial.setText(d.getProvincial());
		txtSCAC.setText(d.getSCAC());
	}

}
