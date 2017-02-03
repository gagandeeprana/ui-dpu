package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.DPUService;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Terminal;
import com.dpu.model.Trailer;
import com.dpu.model.Type;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrailerAddController extends Application implements Initializable{

	@FXML
	Button btnSaveTrailer;
	
	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtOoName, txtFinance;
	
	@FXML
	ComboBox<String> ddlStatus, ddlCategory, ddlDivision, ddlTerminal, ddlTrailerType;
	
	@FXML
	private void btnSaveTrailerAction() {
		addTrailer();
		closeAddTrailerScreen(btnSaveTrailer);
	}
	
	private void addTrailer() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Trailer trailer = setTrailerValue();
					String payload = mapper.writeValueAsString(trailer);
					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API, null, payload);
					MainScreen.trailerController.fillTrailer(response);
					/*if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}*/
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
		
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Type) {
				Type trailerType = (Type) object;
				comboBox.getItems().add(trailerType.getTypeName());
			}
			if(object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
			if(object != null && object instanceof Category) {
				Category category = (Category) object;
				comboBox.getItems().add(category.getName());
			}
			if(object != null && object instanceof Division) {
				Division division = (Division) object;
				comboBox.getItems().add(division.getDivisionName());
			}
			if(object != null && object instanceof Terminal) {
				Terminal terminal = (Terminal) object;
				comboBox.getItems().add(terminal.getTerminalName());
			}
		}
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	List<Category> categoryList = null;
	
	List<Status> statusList = null;

	List<Division> divisionList = null;

	List<Terminal> terminalList = null;

	List<Type> trailerTypeList = null;

	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/openAdd", null);
					Trailer trailer = mapper.readValue(response, Trailer.class);
					categoryList = trailer.getCategoryList();
					fillDropDown(ddlCategory, categoryList);
					statusList = trailer.getStatusList();
					fillDropDown(ddlStatus, statusList);
					divisionList = trailer.getDivisionList();
					fillDropDown(ddlDivision, divisionList);
					terminalList = trailer.getTerminalList();
					fillDropDown(ddlTerminal, terminalList);
					trailerTypeList = trailer.getTrailerTypeList();
					fillDropDown(ddlTrailerType, trailerTypeList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private Trailer setTrailerValue() {
		Trailer trailer = new Trailer();
		trailer.setUnitNo(Long.parseLong(txtUnitNo.getText()));
		trailer.setOwner(txtOwner.getText());
		trailer.setoOName(txtOoName.getText());
		trailer.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		trailer.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		trailer.setUsage(txtUsage.getText());
		trailer.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		trailer.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		trailer.setTrailerTypeId(trailerTypeList.get(ddlTrailerType.getSelectionModel().getSelectedIndex()).getTypeId());
		trailer.setFinance(txtFinance.getText());
		return trailer;
	}

	private void closeAddTrailerScreen(Button btnSaveTrailer) {
		Stage loginStage = (Stage) btnSaveTrailer.getScene().getWindow();
        loginStage.close();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}