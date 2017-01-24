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
import com.dpu.model.Category;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryAddController extends Application implements Initializable{

	@FXML
	Button btnSaveCategory;
	
	@FXML
	TextField txtCategory;
	
	@FXML
	ComboBox<String> ddlType, ddlStatus, ddlHighlight;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	private void btnSaveCategoryAction() {
		addCategory();
		closeAddCategoryScreen(btnSaveCategory);
	}
	
	private void closeAddCategoryScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void addCategory() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Category category = setCategoryValue();
					String payload = mapper.writeValueAsString(category);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API, null, payload);
					MainScreen.categoryController.fillCategories(response);
					
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
	
	List<Type> typeList, highlightList;
	
	List<Status> statusList;
	
	private void fetchMasterDataForDropDowns() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/openAdd", null);
					Category category = mapper.readValue(response, Category.class);
					typeList = category.getTypeList();
					fillDropDown(ddlType, typeList);
					statusList = category.getStatusList();
					fillDropDown(ddlStatus, statusList);
					highlightList = category.getHighlightList();
					fillDropDown(ddlHighlight, highlightList);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for(int i=0;i<list.size();i++) {
			Object object = list.get(i);
			if(object != null && object instanceof Type && comboBox.equals(ddlType)) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			if(object != null && object instanceof Type && comboBox.equals(ddlHighlight)) {
				Type highlight = (Type) object;
				comboBox.getItems().add(highlight.getTypeName());
			}
			if(object != null && object instanceof Status) {
				Status status = (Status) object;
				comboBox.getItems().add(status.getStatus());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchMasterDataForDropDowns();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private Category setCategoryValue() {
		Category category = new Category();
		category.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		category.setName(txtCategory.getText());
		category.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		category.setHighlightId(highlightList.get(ddlHighlight.getSelectionModel().getSelectedIndex()).getTypeId());
		return category;
	}
}