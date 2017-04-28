package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CategoryController extends Application implements Initializable {

	@FXML
	TableView<Category> tblCategory;

	@FXML
	TableColumn<Category, String> type, category;

	@FXML
	TextField txtSearchCategory;

	@FXML
	AnchorPane anchorPaneCategory, root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Login.setWidthForAll(root, tblCategory);
		Login.setWidthForAll(anchorPaneCategory, null);
		fetchCategories();
	}

	@FXML
	private void btnAddCategoryAction() {
		openAddCategoryScreen();
	}

	@FXML
	private void btnEditCategoryAction() {
		Category category = cList.get(tblCategory.getSelectionModel().getSelectedIndex());
		if (category != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + category.getCategoryId(),
								null);
						System.out.println("OpenEditResponse: " + response);
						if (response != null && response.length() > 0) {
							Category c = mapper.readValue(response, Category.class);
							CategoryEditController categoryEditController = (CategoryEditController) openEditCategoryScreen();
							categoryEditController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditCategoryScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CATEGORY_BASE_PACKAGE + Iconstants.XML_CATEGORY_EDIT_SCREEN));

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
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CATEGORY_BASE_PACKAGE + Iconstants.XML_CATEGORY_ADD_SCREEN));

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

	ObjectMapper mapper = new ObjectMapper();

	public void fillCategories(String response) {

		try {
			cList = new ArrayList<Category>();
			setColumnValues();
			ObservableList<Category> data = null;
			if (response != null && response.length() > 0) {
				Category c[] = mapper.readValue(response, Category[].class);
				for (Category ccl : c) {
					cList.add(ccl);
				}
				data = FXCollections.observableArrayList(cList);

			} else {
				data = FXCollections.observableArrayList(cList);
			}
			tblCategory.setItems(data);

			tblCategory.setVisible(true);
		} catch (Exception e) {
			System.out.println("CategoryController: fillCategories(): " + e.getMessage());
		}
	}

	DPUService temp;
	Date lastClickTime;

	@FXML
	private void handleRowSelect() {
		tblCategory.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					System.out.println("clicked:::::::::::::");
				}
			}
		});
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		type = (TableColumn<Category, String>) tblCategory.getColumns().get(0);
		category = (TableColumn<Category, String>) tblCategory.getColumns().get(1);
	}

	@FXML
	private void btnGoCategoryAction() {
		String searchCategory = txtSearchCategory.getText();

		if (searchCategory != null && searchCategory.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + searchCategory + "/search",
								null);
						fillCategories(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}

		if (searchCategory != null && searchCategory.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API,
								null);
						fillCategories(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	private void btnDeleteCategoryAction() {
		Category category = tblCategory.getSelectionModel().getSelectedItem();
		if (category != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API + "/" + category.getCategoryId(),
								null);
						// fillCategories(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<Category> categoryList = (List<Category>) success.getResultList();
							String res = mapper.writeValueAsString(categoryList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillCategories(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
						/*
						 * if(response != null && response.contains("message"))
						 * { Success success = mapper.readValue(response,
						 * Success.class); JOptionPane.showMessageDialog(null,
						 * success.getMessage() , "Info", 1); } else { Failed
						 * failed = mapper.readValue(response, Failed.class);
						 * JOptionPane.showMessageDialog(null,
						 * failed.getMessage(), "Info", 1); }
						 */
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	List<Category> cList = null;

	public void fetchCategories() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CATEGORY_API,
							null);
					Category c[] = mapper.readValue(response, Category[].class);
					cList = new ArrayList<Category>();
					for (Category ccl : c) {
						cList.add(ccl);
					}
					ObservableList<Category> data = FXCollections.observableArrayList(cList);

					setColumnValues();
					tblCategory.setItems(data);

					tblCategory.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	private void setColumnValues() {

		type.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Category, String> param) {
						return new SimpleStringProperty(param.getValue().getTypeName() + "");
					}
				});
		category.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Category, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});

	}

	// ADD MENU

	public int tblCategoryMenuCount = 0;

	@FXML
	public void handleAddContMouseClick(MouseEvent event) {

		tblCategory.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					System.out.println("clicked:::::::::::::");
					Category category = cList.get(tblCategory.getSelectionModel().getSelectedIndex());
					if (category != null) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									ObjectMapper mapper = new ObjectMapper();
									String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
											+ Iconstants.URL_CATEGORY_API + "/" + category.getCategoryId(), null);
									System.out.println("OpenEditResponse: " + response);
									if (response != null && response.length() > 0) {
										Category c = mapper.readValue(response, Category.class);
										CategoryEditController categoryEditController = (CategoryEditController) openEditCategoryScreen();
										categoryEditController.initData(c);
									}
								} catch (Exception e) {
									e.printStackTrace();
									JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
								}
							}
						});
					}
				}
			}
		});
		/*
		 * // Create ContextMenu ContextMenu contextMenu = new ContextMenu();
		 * 
		 * MenuItem item1 = new MenuItem("ADD"); item1.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { }
		 * 
		 * }); MenuItem item2 = new MenuItem("EDIT"); item2.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * 
		 * } });
		 * 
		 * MenuItem item3 = new MenuItem("DELETE"); item3.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * 
		 * } });
		 * 
		 * MenuItem item4 = new MenuItem("PERSONALIZE"); item1.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { }
		 * 
		 * }); MenuItem item5 = new MenuItem("DUPLICATE"); item2.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * 
		 * } });
		 * 
		 * MenuItem item6 = new MenuItem("FILTER BY"); item3.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * 
		 * } });
		 * 
		 * // Add MenuItem to ContextMenu contextMenu.getItems().addAll(item1,
		 * item2, item3, item4, item5, item6); if (tblCategoryMenuCount == 0) {
		 * tblCategoryMenuCount++; // When user right-click on Table
		 * tblCategory.setOnContextMenuRequested(new
		 * EventHandler<ContextMenuEvent>() {
		 * 
		 * @Override public void handle(ContextMenuEvent event) {
		 * contextMenu.show(tblCategory, event.getScreenX(),
		 * event.getScreenY());
		 * 
		 * }
		 * 
		 * });
		 * 
		 * }
		 */

	}
}