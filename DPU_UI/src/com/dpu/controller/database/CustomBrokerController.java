package com.dpu.controller.database;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.HandlingModel;
import com.dpu.model.CustomBroker;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CustomBrokerController extends Application implements Initializable {

	@FXML
	TableView<CustomBroker> tblCustomBroker;
	
	@FXML
	TableColumn<CustomBroker, String> customBrokerName;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	TextField txtSearchCustomBroker;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchCustomBroker();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddCustomBrokerAction() {
		openAddCustomBrokerScreen();
	}
	
	@FXML
	private void btnGoCustomBrokerAction() {
		String searchCustomBroker = txtSearchCustomBroker.getText();

		if(searchCustomBroker != null && searchCustomBroker.length() > 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API + "/" + searchCustomBroker + "/search", null);
						fillCustomBrokers(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
		
		if(searchCustomBroker != null && searchCustomBroker.length() == 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API, null);
						fillCustomBrokers(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	List<CustomBroker> customBrokers = null;
	
	public void fillCustomBrokers(String response) {
		
		try {
			customBrokers = new ArrayList<CustomBroker>();
			setColumnValues();
			ObservableList<CustomBroker> data = null;
			if(response != null && response.length() > 0) {
				CustomBroker c[] = mapper.readValue(response, CustomBroker[].class);
				for(CustomBroker ccl : c) {
					customBrokers.add(ccl);
				}
				data = FXCollections.observableArrayList(customBrokers);
				
			} else {
				data = FXCollections.observableArrayList(customBrokers);
			}
			tblCustomBroker.setItems(data);
			
			tblCustomBroker.setVisible(true);
		} catch (Exception e) {
			System.out.println("CustomBrokerController: fillCustomBroker(): "+ e.getMessage());
		}
	}
	
	@FXML
	private void btnEditCustomBrokerAction() {
		CustomBroker customBroker = customBrokers.get(tblCustomBroker.getSelectionModel().getSelectedIndex());
		if(customBroker != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API + "/" + customBroker.getCustomBrokerId(), null);
						if(response != null && response.length() > 0) {
							CustomBroker c = mapper.readValue(response, CustomBroker.class);
							CustomBrokerEditController customBrokerEditController = (CustomBrokerEditController) openEditCustomBrokerScreen();
							customBrokerEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	private Object openEditCustomBrokerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.CUSTOM_BROKER_BASE_PACKAGE + Iconstants.XML_CUSTOM_BROKER_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit CustomBroker");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void openAddCustomBrokerScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(CustomBrokerController.class.getClassLoader().getResource(Iconstants.CUSTOM_BROKER_BASE_PACKAGE + Iconstants.XML_CUSTOM_BROKER_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New CustomBroker");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		customBrokerName = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(0);
	}

	public void fetchCustomBroker() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API, null);
					fillCustomBrokers(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	@FXML
	private void btnDeleteCustomBrokerAction() {
		CustomBroker customBroker = tblCustomBroker.getSelectionModel().getSelectedItem();
		if(customBroker != null) {
			Platform.runLater(new Runnable() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API + "/" + customBroker.getCustomBrokerId(), null);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<CustomBroker> customBrokerList = (List<CustomBroker>) success.getResultList();
							String res = mapper.writeValueAsString(customBrokerList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillCustomBrokers(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	private void setColumnValues() {
		
		customBrokerName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomBroker,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
				return new SimpleStringProperty(param.getValue().getCustomBrokerName() + "");
			}
		});
	}
	
	// ADD MENU
	
		/*	public int tblServicerMenuCount = 0;
			
			@FXML
			public void handleAddContMouseClick(MouseEvent event) {

				// Create ContextMenu
				ContextMenu contextMenu = new ContextMenu();

				MenuItem item1 = new MenuItem("ADD");
				item1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
					}

				});
				MenuItem item2 = new MenuItem("EDIT");
				item2.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});

				MenuItem item3 = new MenuItem("DELETE");
				item3.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});
				
				MenuItem item4 = new MenuItem("PERSONALIZE");
				item1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
					}

				});
				MenuItem item5 = new MenuItem("DUPLICATE");
				item2.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});

				MenuItem item6 = new MenuItem("FILTER BY");
				item3.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

					}
				});



				// Add MenuItem to ContextMenu
				contextMenu.getItems().addAll(item1, item2, item3, item4, item5, item6);
				if (tblServicerMenuCount == 0) {
					tblServicerMenuCount++;
					// When user right-click on Table
					tblHandling.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
						@Override
						public void handle(ContextMenuEvent event) {
							contextMenu.show(tblHandling, event.getScreenX(), event.getScreenY());

						}

					});

				}

			}*/

}