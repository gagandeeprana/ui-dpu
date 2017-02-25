package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Shipper;
import com.dpu.model.Success;
import com.dpu.model.Terminal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TerminalEditController extends Application implements Initializable {
	@FXML
	Button btnUpdateTerminal;

	Long terminalId = 0l;

	@FXML
	TextField txtTerminalName, txtLocation;

	@FXML
	ComboBox<Object> ddlAvailableServices, ddlLocation;

	@FXML
	private void btnUpdateTerminalAction() {
		editTerminal();
		closeEditTerminalScreen(btnUpdateTerminal);

	}

	private void closeEditTerminalScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editTerminal() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Terminal terminal = setTerminalValue();
					String payload = mapper.writeValueAsString(terminal);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_TERMINAL_API + "/" + terminalId, null, payload);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Terminal> terminalList = (List<Terminal>) success.getResultList();
						String res = mapper.writeValueAsString(terminalList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.terminalController.fillTerminal(res);
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
//					MainScreen.terminalController.fetchTerminals();
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

	private Terminal setTerminalValue() {
		Terminal terminal = new Terminal();
		terminal.setTerminalName(txtTerminalName.getText());
		terminal.setShipperId(shipperList.get(ddlLocation.getSelectionModel().getSelectedIndex()).getShipperId());
		List<Long> serviceIds = new ArrayList<>();
		Long serviceId = serviceList.get(ddlAvailableServices.getSelectionModel().getSelectedIndex()).getServiceId();
		terminal.setStatusId(0l);
		serviceIds.add(serviceId);
		terminal.setServiceIds(serviceIds);
		return terminal;
	}

	List<Shipper> shipperList = null;
	
	List<DPUService> serviceList = null;
	
	public void initData(Terminal t) {
		terminalId = t.getTerminalId();
		txtTerminalName.setText(t.getTerminalName());
		shipperList = t.getShipperList();
		for(int i=0;i<t.getShipperList().size();i++) {
			Shipper shipper = t.getShipperList().get(i);
			ddlLocation.getItems().add(shipper.getLocationName());
			if(shipper.getShipperId() == t.getShipperId()) {
				ddlLocation.getSelectionModel().select(i);
			}
		}
		serviceList = t.getServiceList();
		for(int i=0;i<t.getServiceList().size();i++) {
			DPUService service = t.getServiceList().get(i);
			ddlAvailableServices.getItems().add(service.getServiceName());
			for(int j=0;j<t.getServiceIds().size();j++) {
				if(service.getServiceId() == t.getServiceIds().get(j)) {
					ddlAvailableServices.getSelectionModel().select(i);
				}
			}
		}
	}
}
