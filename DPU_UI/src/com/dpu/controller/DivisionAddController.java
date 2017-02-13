/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Status;
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
public class DivisionAddController extends Application implements Initializable {

	@FXML
	Button btnSaveDivision, btnCancel;

	@FXML
	TextField txtDivisionName, txtDivisionCode, txtFedral, txtProvincial, txtSCAC, txtCarrierCode, txtContractPrefix,
			txtInvoicePrefix;
	@FXML
	CheckBox chkIncludeInManagementReporting, chkIncludeInAccountingTransfers;
	
	@FXML
	ComboBox<String> ddlStatus;

	@FXML
	private void btnSaveDivisionAction() {
		addDivision();
		closeAddDivisionScreen(btnSaveDivision);
	}
	
	List<Status> cList = null;
	
	public void fetchStatus() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_STATUS_API, null);
					if(response != null && response.length() > 0) {
						Status c[] = mapper.readValue(response, Status[].class);
						cList = new ArrayList<Status>();
						for(Status ccl : c) {
							ddlStatus.getItems().add(ccl.getStatus());
							cList.add(ccl);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..  " + e , "Info", 1);
				}
			}
		});
	}

	private void closeAddDivisionScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addDivision() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Division division = setDivisionValue();
					String payload = mapper.writeValueAsString(division);
					System.out.println(payload + " pykiad" );
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API, null, payload);
					System.out.println(response);
//					MainScreen.divisionController.fillDivisions(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Division> divisionList = (List<Division>) success.getResultList();
						String res = mapper.writeValueAsString(divisionList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.divisionController.fillDivisions(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}

					/*if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private Division setDivisionValue() {
		Division division = new Division();
		division.setDivisionCode(txtDivisionCode.getText());
		division.setDivisionName(txtDivisionName.getText());
		division.setStatusId(cList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		division.setFedral(txtFedral.getText());
		division.setProvincial(txtProvincial.getText());
		division.setScac(txtSCAC.getText());
		division.setCarrierCode(txtCarrierCode.getText());
		division.setContractPrefix(txtContractPrefix.getText());
		division.setInvoicePrefix(txtInvoicePrefix.getText());
		return division;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchStatus();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		launch(args);
	}
}
