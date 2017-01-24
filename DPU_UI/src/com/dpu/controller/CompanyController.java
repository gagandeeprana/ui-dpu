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
import com.dpu.model.Company;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompanyController extends Application implements Initializable {

	
	static CompanyAddController companyAddController;
	@FXML
	TableView<Company> tblCompany;
	
	public List<Company> cList = null;
	
	@FXML
	TableColumn<Company, String> unitNo, name, email, city, ps, phone, home, fax, afterHours;
	
	@FXML
	private void btnAddCompanyAction() {
		openAddCompanyScreen();
		
		// fetch data for billing Location table
		// CompanyAddController compAddCtrl = new CompanyAddController();
	     //compAddCtrl.fetchBillingLocations();
	}
	
	@FXML
	private void btnEditCompanyAction() {
		Company company = tblCompany.getSelectionModel().getSelectedItem();
		System.out.println(company + "   company:: ");
		if(company != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);
						if(response != null && response.length() > 0) {
							Company c = mapper.readValue(response, Company.class);
							CompanyEditController companyEditController = (CompanyEditController) openEditCompanyScreen();
							companyEditController.initData(c);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
	private void btnDeleteCompanyAction() {
		Company company = tblCompany.getSelectionModel().getSelectedItem();
		if(company != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);
						if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						fetchCompanies();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
	}
	
	private void openAddCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
			
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
	
	private Object openEditCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Company");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchCompanies();
	}

	/*@FXML public void handleMouseClick(MouseEvent arg0) {
	    System.out.println("clicked on " );
	 
         System.out.println("OK");
      
	 
		System.out.println("[CompanyController] : [start] : Enter Start method.");
		 
		 
        Label label = new Label();
 
        Circle circle = new Circle();
        circle.setRadius(80);
        circle.setFill(Color.AQUA);
 
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.setSpacing(5);
 
        root.getChildren().addAll(label, circle);
 
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
 
        MenuItem item1 = new MenuItem("Menu Item 1");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("Menu Item 2");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 2");
            }
        });
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2);
 
        // When user right-click on Circle
        circle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
 
                contextMenu.show(circle, event.getScreenX(), event.getScreenY());
            }
        });
 
        Scene scene = new Scene(root, 400, 200);
 
        stage.setTitle("JavaFX ContextMenu (o7planning.org)");
        stage.setScene(scene);
        stage.show();
		
	}*/
	
	@Override
	public void start(Stage stage) {
		
	}

	public static void main(String[] args) {
		System.out.println("[Company Controller]: Enter main method start.");
		 launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Company, String>) tblCompany.getColumns().get(0);
		name = (TableColumn<Company, String>) tblCompany.getColumns().get(1);
		email = (TableColumn<Company, String>) tblCompany.getColumns().get(2);
		city = (TableColumn<Company, String>) tblCompany.getColumns().get(3);
		ps = (TableColumn<Company, String>) tblCompany.getColumns().get(4);
		phone = (TableColumn<Company, String>) tblCompany.getColumns().get(5);
		home = (TableColumn<Company, String>) tblCompany.getColumns().get(6);
		fax = (TableColumn<Company, String>) tblCompany.getColumns().get(7);
		afterHours = (TableColumn<Company, String>) tblCompany.getColumns().get(8);
	}

	public void fetchCompanies() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null);
					if(response != null && response.length() > 0) {
						Company c[] = mapper.readValue(response, Company[].class);
						cList = new ArrayList<Company>();
						for(Company ccl : c) {
							cList.add(ccl);
						}
						ObservableList<Company> data = FXCollections.observableArrayList(cList);
						
						setColumnValues();
						tblCompany.setItems(data);
			
			            tblCompany.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		unitNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getUnitNo() + "");
			}
		});
		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});
		email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getEmail() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		ps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getProvinceState() + "");
			}
		});
		phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getPhone() + "");
			}
		});
		home.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getCellular() + "");
			}
		});
		fax.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getFax() + "");
			}
		});
		afterHours.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getAfterHours() + "");
			}
		});
	}
}