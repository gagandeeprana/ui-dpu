/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Division;
import com.dpu.model.Status;

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
	Button btnUpdateDivision, btnCancel;

	Long divisionId = 0l;

	@FXML
	TextField txtDivisionName, txtDivisionCode, txtFedral, txtProvincial, txtSCAC, txtCarrierCode, txtContractPrefix,
			txtInvoicePrefix;

	@FXML
	CheckBox chkIncludeInManagementReporting, chkIncludeInAccountingTransfers;

	@FXML
	ComboBox<String> ddlStatus;

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
					System.out.println("update payload: " + payload);
					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + divisionId, null, payload);
					MainScreen.divisionController.fillDivisions(response);

					/*if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
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
		division.setDivisionCode(txtDivisionCode.getText());
		division.setDivisionName(txtDivisionName.getText());
		division.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		division.setFedral(txtFedral.getText());
		division.setProvincial(txtProvincial.getText());
		division.setScac(txtSCAC.getText());
		division.setCarrierCode(txtCarrierCode.getText());
		division.setContractPrefix(txtContractPrefix.getText());
		division.setInvoicePrefix(txtInvoicePrefix.getText());
		return division;
	}

	List<Status> statusList = null;
	
	public void initData(Division d) {
		divisionId = d.getDivisionId();
		txtDivisionCode.setText(d.getDivisionCode());
		txtDivisionName.setText(d.getDivisionName());
		statusList = d.getStatusList();
		for(int i = 0; i< d.getStatusList().size();i++) {
			Status status = d.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if(status.getId() == d.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
		txtFedral.setText(d.getFedral());
		txtProvincial.setText(d.getProvincial());
		txtSCAC.setText(d.getScac());
		txtCarrierCode.setText(d.getCarrierCode());
		txtContractPrefix.setText(d.getContractPrefix());
		txtInvoicePrefix.setText(d.getInvoicePrefix());
	}

}
