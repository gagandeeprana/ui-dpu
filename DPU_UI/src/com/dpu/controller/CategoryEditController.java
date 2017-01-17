package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Driver;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryEditController extends Application implements Initializable{

	@FXML
	Button btnUpdateCategory;
	
	int categoryId = 0;
	
	@FXML
	TextField txtCategory;
	
	@FXML
	ComboBox<String> ddlType, ddlStatus, ddlHighlight;
	
	@FXML
	private void btnUpdateCategoryAction() {
		editCategory();
		closeEditCategoryScreen(btnUpdateCategory);
		
	}
	
	private void closeEditCategoryScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	private void editCategory() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Category category = setCategoryValue();
					String payload = mapper.writeValueAsString(category);

					String response = PutAPIClient.callPutAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + categoryId, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.categoryController.fetchCategories();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}
	
	private Category setCategoryValue() {
		Category category = new Category();
		category.setTypeId(ddlType.getSelectionModel().getSelectedItem().equals("Customers")?3:0);
		category.setName(txtCategory.getText());
		category.setStatus(ddlStatus.getSelectionModel().getSelectedItem().equals("Active")?1:0);
		category.setHighlight(ddlHighlight.getSelectionModel().getSelectedItem());
		return category;
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
	
	public void initData(Category category) {
		categoryId = category.getCategoryId();
		ddlType.setValue(category.getTypeId() == 3 ? "Customers" : "Type1");
		txtCategory.setText(category.getName());
		ddlStatus.setValue(category.getStatus() == 1 ? "Active":"Inactive");
		ddlHighlight.setValue(category.getHighlight());
	}
}