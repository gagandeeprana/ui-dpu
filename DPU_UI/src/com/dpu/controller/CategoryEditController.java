package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CategoryEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateCategory;

	Long categoryId = 0l;

	@FXML
	TextField txtCategory;

	@FXML
	ComboBox<String> ddlType, ddlStatus, ddlHighlight;
	Validate validate = new Validate();

	@FXML
	private void btnUpdateCategoryAction() {
		boolean response = validateEditCategoryScreen();
		if (response) {
			editCategory();
			closeEditCategoryScreen(btnUpdateCategory);

		}

	}

	private void closeEditCategoryScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private boolean validateEditCategoryScreen() {
		String name = txtCategory.getText();
		String textField = ddlType.getSelectionModel().getSelectedItem();
		String association = ddlStatus.getSelectionModel().getSelectedItem();
		String status = ddlHighlight.getSelectionModel().getSelectedItem();

		boolean result = validate.validateEmptyness(name);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			txtCategory.setStyle("-fx-focus-color: red;");
			txtCategory.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(textField);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			ddlHighlight.setStyle("-fx-focus-color: red;");
			ddlHighlight.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(association);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			ddlStatus.setStyle("-fx-focus-color: red;");
			ddlStatus.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(status);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			ddlType.setStyle("-fx-focus-color: red;");
			ddlType.requestFocus();
			return result;
		}
		return result;
	}

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String name = txtCategory.getText();
		String highLight = ddlHighlight.getSelectionModel().getSelectedItem();
		String association = ddlStatus.getSelectionModel().getSelectedItem();
		String status = ddlType.getSelectionModel().getSelectedItem();
		if (name.equals("")) {
			strBuff.append("Category Name is Mandatory\n");
		}
		if (highLight == null) {
			strBuff.append("Hiighlight is Mandatory\n");
		}
		if (association == null) {
			strBuff.append("Status is Mandatory\n");
		}
		if (status == null) {
			strBuff.append("Type is Mandatory\n");
		}
		return strBuff;
	}

	private Object openValidationScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMMON_BASE_PACKAGE + Iconstants.XML_VALIDATION_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Warning");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void editCategory() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Category category = setCategoryValue();
					String payload = mapper.writeValueAsString(category);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + categoryId, null, payload);

					// MainScreen.categoryController.fillCategories(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Category> categoryList = (List<Category>) success.getResultList();
						String res = mapper.writeValueAsString(categoryList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.categoryController.fillCategories(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
					/*
					 * if(response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage() , "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	private Category setCategoryValue() {
		Category category = new Category();
		category.setTypeId(typeList.get(ddlType.getSelectionModel().getSelectedIndex()).getTypeId());
		category.setName(txtCategory.getText());
		category.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		category.setHighlightId(highlightList.get(ddlHighlight.getSelectionModel().getSelectedIndex()).getTypeId());
		return category;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) {
	}

	List<Type> typeList, highlightList;

	List<Status> statusList;

	public void initData(Category category) {
		System.out.println(category.getCategoryId() + " " + category.getName());
		categoryId = category.getCategoryId();
		txtCategory.setText(category.getName());
		typeList = category.getTypeList();
		highlightList = category.getHighlightList();
		statusList = category.getStatusList();
		for (int i = 0; i < category.getTypeList().size(); i++) {
			Type type = category.getTypeList().get(i);
			ddlType.getItems().add(type.getTypeName());
			if (type.getTypeId() == category.getTypeId()) {
				ddlType.getSelectionModel().select(i);
			}
		}

		for (int i = 0; i < category.getHighlightList().size(); i++) {
			Type type = category.getHighlightList().get(i);
			ddlHighlight.getItems().add(type.getTypeName());
			if (type.getTypeId() == category.getHighlightId()) {
				ddlHighlight.getSelectionModel().select(i);
			}
		}
		//
		for (int i = 0; i < category.getStatusList().size(); i++) {
			Status status = category.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == category.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
	}
}