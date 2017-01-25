package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingLocations;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompanyAddController extends Application implements Initializable {

	@FXML
	Pane addCompanyPane;
	
	static CompanyAddController companyAddController;
	
	@FXML
	private void btnSaveBillingLocationAction() {
		companyAddController = (CompanyAddController) showPanel(Iconstants.COMPANY_BASE_PACKAGE, Iconstants.XML_COMPANY_ADD_SCREEN);
	}
	
	int count = 0;
	
	@FXML
	Button btnSaveCompany;
	
	@FXML
	TextField txtCompany, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtZip, txtAfterHours, txtEmail, txtTollFree, txtWebsite, txtCellular, txtPager;
	
	@FXML
	private void btnSaveCompanyAction() {
		addCompany();
		closeAddCompanyScreen(btnSaveCompany);
	}
	
	private void closeAddCompanyScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	@FXML
	TableView<BillingLocations> tableBillingLocations;
	
	public List<BillingLocations> billLocList = null;
	
	@FXML
	TableColumn<BillingLocations, String> name, address, unitNo, city, provinceState, zip, arCDN, arUS, status,
	contact, position, email, cellular, phone,ext,fax,prefix,tollfree,companyId;
	
	@FXML
	TableView<AdditionalContact> tableAdditionalContact;
	
	public List<AdditionalContact> AddConList = null;
	
	@FXML
	TableColumn<AdditionalContact, String> additionalContact,positionn, phoneNumber,ExtensionNumber,faxNumber,pagerNumber,
	 cellularNumber,statuss,emaill;
	
	
	 
	//for Additional Contact Screen table
	@FXML public void handleAddContMouseClick(MouseEvent arg0) {
		System.out.println("[CompanyController] : [start] : Enter Start method.");
		 
       Label label = new Label();
       
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
 
        MenuItem item1 = new MenuItem("ADD");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("[btnAddBillingLocationAction]  : Enter");
            	openAddAdditionalScreenScreen();
               // label.setText("Select Menu Item 1");
            }
        });
        
        MenuItem item2 = new MenuItem("EDIT");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 2");
            }
        });
        
        MenuItem item3 = new MenuItem("DELETE");
        item3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2, item3);
        int count = 0;
        if(count == 0){
        // When user right-click on Table
        	tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
  
            @Override
            public void handle(ContextMenuEvent event) {
            	
                contextMenu.show(tableBillingLocations, event.getScreenX(), event.getScreenY());
                
            }
           
        });
        count =1;
        }
 
       // Scene scene = new Scene(root, 400, 200);
 
        /*stage.setTitle("JavaFX ContextMenu (o7planning.org)");
        stage.setScene(scene);
        stage.show();*/
		
	}
	
	
	// for Billing Location
	@FXML public void handleMouseClick(MouseEvent arg0) {
	    System.out.println("clicked on " );
	 
         System.out.println("OK");
      
	 
		System.out.println("[CompanyController] : [start] : Enter Start method.");
		 
		 
       Label label = new Label();
  
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
 
        MenuItem item1 = new MenuItem("ADD");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	System.out.println("[btnAddBillingLocationAction]  : Enter");
        		openAddBillingLocationScreen();
               // label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("EDIT");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 2");
            }
        });
        
        MenuItem item3 = new MenuItem("DELETE");
        item3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2, item3);
        int count = 0;
        if(count == 0){
        // When user right-click on Table
        tableBillingLocations.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
  
            @Override
            public void handle(ContextMenuEvent event) {
            	
                contextMenu.show(tableBillingLocations, event.getScreenX(), event.getScreenY());
                
            }
           
        });
        count =1;
        }
  
		
	}
	
	// method added for add billing location screen
	private void openAddBillingLocationScreen() {
		System.out.println("[openAddDBillingLocScreen]  : Enter");
		try {
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_ADD_BILLING_LOCATION_SCREEN));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_ADD_BILLING_LOCATION_SCREEN));
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add Billing Location");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception :"+e);
		}
	}
	
	// open Additional Contact Screen
	 
		private void openAddAdditionalScreenScreen() {
			System.out.println("[openAddAdditionalScreenScreen]  : Enter");
			try {
				//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_ADD_BILLING_LOCATION_SCREEN));
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_ADD_ADDITIONAL_CONTACT_SCREEN));
		        Parent root = (Parent) fxmlLoader.load();
		        
		        Stage stage = new Stage();
		        stage.initModality(Modality.APPLICATION_MODAL);
		        stage.setTitle("ADD ADDITIONAL CONTACT");
		        stage.setScene(new Scene(root)); 
		        stage.show();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception :"+e);
			}
		}
	private void addCompany() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Company company = setCompanyValue();
					String payload = mapper.writeValueAsString(company);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.companyController.fetchCompanies();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	 
	
	
	@SuppressWarnings("unchecked")
	public    void fetchColumns() {
		System.out.println("[fetchColumns] : Enter :");
		name = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(0);
		address = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(1);
		city = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(2);
		phone = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(3);
		contact = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(4);
		zip = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(5);
		fax = (TableColumn<BillingLocations, String>) tableBillingLocations.getColumns().get(6);
		System.out.println("[fetchColumns] : Enter :");
		 
	}
	//fetch Billing Locations
	public void fetchBillingLocations(String response) {
		System.out.println("[fetchBillingLocations] : Enter :");
		fetchColumns();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("[fetchBillingLocations] [run ]: Enter :");
				try {
					ObjectMapper mapper = new ObjectMapper();
//					String response = "[{\"billingLocationId\":18,\"name\":\"XXX\",\"address\":\"XXX\",\"city\":\"XXX\",\"zip\":\"0000\",\"status\":1,\"contact\":\"XXX\",\"position\":\"XXX\",\"email\":\"XXX\",\"cellular\":\"(868) 686-8686\",\"phone\":\"(777) 777-7777\",\"ext\":\"7777\",\"fax\":\"(876) 876-8768\",\"tollfree\":\"(___) ___-____\"},{\"billingLocationId\":24,\"name\":\"AAA\",\"address\":\"AAA\",\"city\":\"AAA\",\"zip\":\"6786\",\"status\":1,\"contact\":\"AAA\",\"position\":\"AAA\",\"email\":\"AAA\",\"cellular\":\"(868) 686-8686\",\"phone\":\"(777) 777-7777\",\"ext\":\"7777\",\"fax\":\"(876) 876-8768\",\"tollfree\":\"(___) ___-____\"},{\"billingLocationId\":25,\"name\":\"AAA\",\"address\":\"AAA\",\"city\":\"AAA\",\"zip\":\"6786\",\"status\":1,\"contact\":\"AAA\",\"position\":\"AAA\",\"email\":\"AAA\",\"cellular\":\"(868) 686-8686\",\"phone\":\"(777) 777-7777\",\"ext\":\"7777\",\"fax\":\"(876) 876-8768\",\"tollfree\":\"(___) ___-____\"}]";
					System.out.println("RESPONSE------"+response);
					 
					if(response != null && response.length() > 0) {
						BillingLocations c[] = mapper.readValue(response, BillingLocations[].class);
						System.out.println("c: "+c.length);
						billLocList = new ArrayList<BillingLocations>();
						for(BillingLocations ccl : c) {
							System.out.println("ccl: "+ccl);
							billLocList.add(ccl);
						}
						System.out.println("bill loca : "  + billLocList.size());
						ObservableList<BillingLocations> data = FXCollections.observableArrayList(billLocList);
						
						setColumnValues();
						tableBillingLocations.setItems(data);
			
						tableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	
// set columns value for BillingLocations
private void setColumnValues() {
		System.out.println("Enter setColumns");
		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});
		address.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getAddress() + "");
			}
		});
		phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		contact.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getContact() + "");
			}
		});
		zip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getZip() + "");
			}
		});
		fax.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BillingLocations,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BillingLocations, String> param) {
				return new SimpleStringProperty(param.getValue().getFax() + "");
			}
		});
		System.out.println("Exit setColumns");
		 
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("location :  "+location);
		String loc = location.toString();
		System.out.println("loc:"+loc);
		if(loc.endsWith("AddBillingLocationScreen.fxml")){
			Iconstants.val = 1;
			System.out.println("AddBillingLocationScreen.xml");
		}
		if(loc.endsWith("AddCompany.fxml")){
			Iconstants.val = 0;
			System.out.println("AddCompany.xml");
		}
		 
		 
		if(count == Iconstants.val){
			//fetchBillingLocations();
			
		}
	}

	@Override
	public void start(Stage stage) {
		
	    }
	 
	     
	

	public static void main(String[] args) {
		launch(args);
	}
	
	private Company setCompanyValue() {
		Company company = new Company();
		company.setName(txtCompany.getText());
		company.setContact(txtContact.getText());
		company.setAddress(txtAddress.getText());
		company.setPosition(txtPosition.getText());
		company.setUnitNo(txtUnitNo.getText());
		company.setPhone(txtPhone.getText());
		company.setExt(txtExt.getText());
		company.setCity(txtCity.getText());
		company.setFax(txtFax.getText());
		company.setCompanyPrefix(txtPrefix.getText());
		company.setProvinceState(txtProvince.getText());
		company.setZip(txtZip.getText());
		company.setAfterHours(txtAfterHours.getText());
		company.setEmail(txtEmail.getText());
		company.setTollfree(txtTollFree.getText());
		company.setWebsite(txtWebsite.getText());
		company.setCellular(txtCellular.getText());
		company.setPager(txtPager.getText());
		return company;
	}
	
public void fillBillingLocation(String response) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			if(response != null && response.length() > 0) {
				BillingLocations c[] = mapper.readValue(response, BillingLocations[].class);
				billLocList = new ArrayList<BillingLocations>();
				for(BillingLocations ccl : c) {
					billLocList.add(ccl);
				}
				ObservableList<BillingLocations> data = FXCollections.observableArrayList(billLocList);
				
				setColumnValues();
				tableBillingLocations.setItems(data);
	
				tableBillingLocations.setVisible(true);
			}
		} catch (Exception e) {
			System.out.println("EquipmentController: fillEquipments(): "+ e.getMessage());
		}
	}
	 
private Object showPanel(String basePackage, String screenName) {
	try {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(basePackage + screenName));
		Parent root = (Parent) fxmlLoader.load();
		Pane pane = (Pane) root;

		ObservableList<Node> nodes = addCompanyPane.getChildren();

		if (nodes != null && nodes.size() >= 4 && nodes.get(3) != null) {
			nodes.remove(3);
			nodes.add(3, pane);
		} else {
			nodes.add(pane);
		}
		return fxmlLoader.getController();
	} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
	}
	return null;
}
}