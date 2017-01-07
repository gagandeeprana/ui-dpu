package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CategoryController extends Application implements Initializable {

	@FXML
	TableView<Category> tblCategory;
	
	@FXML
	TableColumn<Category, String> type, category;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchCategories();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		type = (TableColumn<Category, String>) tblCategory.getColumns().get(0);
		category = (TableColumn<Category, String>) tblCategory.getColumns().get(1);
	}

	private void fetchCategories() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API, null);
					Category c[] = mapper.readValue(response, Category[].class);
					List<Category> cList = new ArrayList<Category>();
					for(Category ccl : c) {
						cList.add(ccl);
					}
					ObservableList<Category> data = FXCollections.observableArrayList(cList);
					
					setColumnValues();
					tblCategory.setItems(data);
		
		            tblCategory.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Category,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Category, String> param) {
				return new SimpleStringProperty(param.getValue().getTypeId() + "");
			}
		});
		category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Category,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Category, String> param) {
				return new SimpleStringProperty(param.getValue().getCategoryId() + "");
			}
		});

	}
}