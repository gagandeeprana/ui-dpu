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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ServiceController extends Application implements Initializable {

	@FXML
	TableView<DPUService> tblService;
	
	@FXML
	TableColumn<DPUService, String> service, textField, associationWith, status;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@FXML
	TextField txtSearchService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		newMethod();
		fetchServices();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	@FXML
	private void btnAddServiceAction() {
		openAddServiceScreen();
	}
	
	@FXML
	private void btnGoServiceAction() {
		String searchService = txtSearchService.getText();

		if(searchService != null && searchService.length() > 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + searchService + "/search", null);
							System.out.println(response);
						fillServices(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
			
		}
		
		if(searchService != null && searchService.length() == 0) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API, null);
						fillServices(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	List<DPUService> services = null;
	
	/*public void newMethod() {
		txtSearchService.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent event) {
	            if (event.getCode() == KeyCode.TAB) {
	            	
	            }
	        }
	    });
	}*/
	
	public void fillServices(String response) {
		
		try {
			services = new ArrayList<DPUService>();
			setColumnValues();
			ObservableList<DPUService> data = null;
			if(response != null && response.length() > 0) {
				DPUService c[] = mapper.readValue(response, DPUService[].class);
				for(DPUService ccl : c) {
					services.add(ccl);
				}
				data = FXCollections.observableArrayList(services);
				
			} else {
				data = FXCollections.observableArrayList(services);
			}
			tblService.setItems(data);
			
			tblService.setVisible(true);
		} catch (Exception e) {
			System.out.println("ServiceController: fillEquipments(): "+ e.getMessage());
		}
	}
	
	@FXML
	private void btnEditServiceAction() {
		DPUService dpuService = services.get(tblService.getSelectionModel().getSelectedIndex());
		if(dpuService != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + dpuService.getServiceId(), null);
						if(response != null && response.length() > 0) {
							DPUService c = mapper.readValue(response, DPUService.class);
							ServiceEditController serviceEditController = (ServiceEditController) openEditServiceScreen();
							serviceEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	private Object openEditServiceScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.SERVICE_BASE_PACKAGE + Iconstants.XML_SERVICE_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Service");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void openAddServiceScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(ServiceController.class.getClassLoader().getResource(Iconstants.SERVICE_BASE_PACKAGE + Iconstants.XML_SERVICE_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Service");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		service = (TableColumn<DPUService, String>) tblService.getColumns().get(0);
		textField = (TableColumn<DPUService, String>) tblService.getColumns().get(1);
		associationWith = (TableColumn<DPUService, String>) tblService.getColumns().get(2);
		status = (TableColumn<DPUService, String>) tblService.getColumns().get(3);
	}

	public void fetchServices() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API, null);
					DPUService s[] = mapper.readValue(response, DPUService[].class);
					services = new ArrayList<DPUService>();
					for(DPUService ccl : s) {
						services.add(ccl);
					}
					ObservableList<DPUService> data = FXCollections.observableArrayList(services);
					
					setColumnValues();
					tblService.setItems(data);
		
		            tblService.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
				}
			}
		});
	}
	
	@FXML
	private void btnDeleteServiceAction() {
		DPUService service = tblService.getSelectionModel().getSelectedItem();
		if(service != null) {
			Platform.runLater(new Runnable() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_SERVICE_API + "/" + service.getServiceId(), null);
//						fillServices(response);
						try {
							Success success = mapper.readValue(response, Success.class);
							List<DPUService> serviceList = (List<DPUService>) success.getResultList();
							String res = mapper.writeValueAsString(serviceList);
							JOptionPane.showMessageDialog(null, success.getMessage());
							fillServices(res);
						} catch (Exception e) {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage());
						}

						/*if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						fetchServices();*/
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	private void setColumnValues() {
		
		service.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getServiceName() + "");
			}
		});
		textField.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getTextField() + "");
			}
		});
		associationWith.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getAssociationWith() + "");
			}
		});
		status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DPUService,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DPUService, String> param) {
				return new SimpleStringProperty(param.getValue().getStatus());
			}
		});

	}
	
	// ADD MENU
	
			public int tblServicerMenuCount = 0;
			
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
					tblService.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
						@Override
						public void handle(ContextMenuEvent event) {
							contextMenu.show(tblService, event.getScreenX(), event.getScreenY());

						}

					});

				}

			}

}