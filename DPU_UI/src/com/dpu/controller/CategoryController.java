package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Company;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
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
	
	@FXML
	private void btnAddCategoryAction() {
		openAddCategoryScreen();
	}
	
	@FXML
	private void btnEditCategoryAction() {
		Category category = tblCategory.getSelectionModel().getSelectedItem();
		System.out.println(category + "   category:: ");
		if(category != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + category.getCategoryId(), null);
						System.out.println("Category response: " + response);
						if(response != null && response.length() > 0) {
							Category c = mapper.readValue(response, Category.class);
							CategoryEditController categoryEditController = (CategoryEditController) openEditCategoryScreen();
							categoryEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	private Object openEditCategoryScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.CATEGORY_BASE_PACKAGE + Iconstants.XML_CATEGORY_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Category");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private void openAddCategoryScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.CATEGORY_BASE_PACKAGE + Iconstants.XML_CATEGORY_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Company");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
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
	
	@FXML
	private void btnDeleteCategoryAction() {
		Category category = tblCategory.getSelectionModel().getSelectedItem();
		if(category != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + category.getCategoryId(), null);
						if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						fetchCategories();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}

	public void fetchCategories() {
	
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
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});

	}
}