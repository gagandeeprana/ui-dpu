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
		System.out.println("dId:: " + divisionId);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Division division = setDivisionValue();
					System.out.println("editDivision()::::::: " + division.getDivisionCode() + "  "
							+ division.getDivisionName() + "  " + division.getFedral() + "  " + division.getSCAC());
					String payload = mapper.writeValueAsString(division);
<<<<<<< HEAD
					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + divisionId, null, payload);
					System.out.println("res:: " + response);
					if (response != null && response.contains("message")) {
=======
					System.out.println("update payload: " + payload);
					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + divisionId, null, payload);
					MainScreen.divisionController.fillDivisions(response);

					/*if (response != null && response.contains("message")) {
>>>>>>> de161973cf2dd6940475f2799d7f67299d76fcb4
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
						System.out.println("editDivision()::::::: 1111111111 " + success);
					} else {
						System.out.println("editDivision()::::::: 2222222222");

						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
<<<<<<< HEAD
					}

					MainScreen.divisionController.fetchDivisions();
					// new DivisionController().fetchDivisions();

=======
					}*/
>>>>>>> de161973cf2dd6940475f2799d7f67299d76fcb4
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					e.printStackTrace();
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
<<<<<<< HEAD
		System.out.print("dId::: " + d.getDivisionId());
		divisionId = d.getDivisionId();
		txtCarrierCode.setText(d.getCarrierCode());
		txtContractPrefix.setText(d.getContractPrefix());
=======
		divisionId = d.getDivisionId();
>>>>>>> de161973cf2dd6940475f2799d7f67299d76fcb4
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
