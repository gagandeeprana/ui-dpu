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
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.request.BillingLocation;
import com.dpu.request.CompanyModel;

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
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane addCompanyPane;
	
	//-------------------------
	 @FXML
	 private TableColumn<AdditionalContact, String> additionalContact;
	 
	 @FXML
	 private TableColumn<AdditionalContact, String> position;
	 
	 @FXML
	    private TableColumn<AdditionalContact, String> phoneNo;
	 
	 @FXML
	    private TableColumn<AdditionalContact, String> faxNo;
	
	 @FXML
	    private TableColumn<AdditionalContact, String> cellular;
	 
	  @FXML
	    private TableColumn<AdditionalContact, String> email;
	  
	  @FXML
	    private TableColumn<AdditionalContact, String> extension;
	  
	  @FXML
	    private TableColumn<AdditionalContact, String> pager;
	  
	  @FXML
	    private TableColumn<AdditionalContact, String> status;
	//------------------

	@FXML
	private TableColumn<BillingControllerModel, String> address;

	@FXML
	private Button btnSaveCompany;
	
	@FXML
	private Button btnCancelCompany;

	@FXML
	private TableColumn<BillingControllerModel, String> city;

	@FXML
	private TableColumn<BillingControllerModel, String> contact;

	@FXML
	private TableColumn<BillingControllerModel, String> fax;

	@FXML
	private TableColumn<BillingControllerModel, String> name;

	@FXML
	private TableColumn<BillingControllerModel, String> phone;

	@FXML
	private TableView<AdditionalContact> tableAdditionalContact;

	@FXML
	private TableView<BillingControllerModel> tableBillingLocations;

	@FXML
	public TextField txtAddress;

	@FXML
	public TextField txtAfterHours;

	@FXML
	public TextField txtCellular;

	@FXML
	public TextField txtCity;

	@FXML
	public TextField txtCompany;

	@FXML
	public TextField txtContact;

	@FXML
	public TextField txtEmail;

	@FXML
	public TextField txtExt;

	@FXML
	public TextField txtFax;

	@FXML
	public TextField txtPager;

	@FXML
	public TextField txtPhone;

	@FXML
	public TextField txtPosition;

	@FXML
	public TextField txtPrefix;

	@FXML
	public TextField txtProvince;

	@FXML
	public TextField txtTollFree;

	@FXML
	public TextField txtUnitNo;

	@FXML
	public TextField txtWebsite;

	@FXML
	public TextField txtZip;

	@FXML
	public TableColumn<BillingControllerModel, String> zip;

	@FXML
	void handleAddContMouseClick(MouseEvent event) {
		 
		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				company.setName(txtCompany.getText());
				company.setAddress(txtAddress.getText());
				company.setUnitNo(txtUnitNo.getText());
				company.setCity(txtCity.getText());
				company.setProvinceState(txtProvince.getText());
				company.setZip(txtZip.getText());
				company.setEmail(txtEmail.getText());
				company.setWebsite(txtWebsite.getText());
				company.setContact(txtContact.getText());
				company.setPosition(txtPosition.getText());
				company.setPhone(txtPhone.getText());
				company.setExt(txtExt.getText());
				company.setFax(txtFax.getText());
				company.setCompanyPrefix(txtPrefix.getText());
				company.setTollfree(txtTollFree.getText());
				company.setCellular(txtCellular.getText());
				company.setPager(txtPager.getText());
				company.setAfterHours(txtAfterHours.getText());

				openAddAdditionalContactScreen();
				
				try {
					 closeAddCompanyScreen(btnSaveCompany);
					   
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}

				
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

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);
		int count = 0;
		if (count == 0) {
			// When user right-click on Table
			tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableAdditionalContact, event.getScreenX(), event.getScreenY());

				}

			});
			count = 1;
		}

	}

	@FXML
	void initialize() {
		assert addCompanyPane != null : "fx:id=\"addCompanyPane\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert address != null : "fx:id=\"address\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert btnSaveCompany != null : "fx:id=\"btnSaveCompany\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert city != null : "fx:id=\"city\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert contact != null : "fx:id=\"contact\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert fax != null : "fx:id=\"fax\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert tableAdditionalContact != null : "fx:id=\"tableAdditionalContact\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert tableBillingLocations != null : "fx:id=\"tableBillingLocations\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtAfterHours != null : "fx:id=\"txtAfterHours\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtCellular != null : "fx:id=\"txtCellular\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtCompany != null : "fx:id=\"txtCompany\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtContact != null : "fx:id=\"txtContact\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtExt != null : "fx:id=\"txtExt\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtFax != null : "fx:id=\"txtFax\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPager != null : "fx:id=\"txtPager\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPosition != null : "fx:id=\"txtPosition\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPrefix != null : "fx:id=\"txtPrefix\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtProvince != null : "fx:id=\"txtProvince\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtTollFree != null : "fx:id=\"txtTollFree\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtUnitNo != null : "fx:id=\"txtUnitNo\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtWebsite != null : "fx:id=\"txtWebsite\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtZip != null : "fx:id=\"txtZip\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert zip != null : "fx:id=\"zip\" was not injected: check your FXML file 'AddCompany.fxml'.";

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Start method called");
		try {
			 
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Billing Location");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static ArrayList<BillingControllerModel> listOfBilling = new ArrayList<BillingControllerModel>();
	public static ArrayList<AdditionalContact> listOfAdditionalContact = new ArrayList<AdditionalContact>();
	public static CompanyModel company = new CompanyModel();
	// new added
	public void fetchBillingLocations() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if(listOfBilling != null & !(listOfBilling.isEmpty())){
						ObservableList<BillingControllerModel> data = FXCollections.observableArrayList(listOfBilling);
						setColumnValues();
						tableBillingLocations.setItems(data);
						tableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	private void setColumnValues() {

		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCompany() + "");
					}
				});
		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});
		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});
		phone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		contact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getContact() + "");
					}
				});
		zip.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getZip() + "");
					}
				});
		fax.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});
	}
	
	
	public void fetchAdditionalContacts() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if(listOfAdditionalContact != null & !(listOfAdditionalContact.isEmpty())){
						ObservableList<AdditionalContact> data = FXCollections.observableArrayList(listOfAdditionalContact);
						setAdditionalContactColumnValues();
						tableAdditionalContact.setItems(data);
						tableAdditionalContact.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	private void setAdditionalContactColumnValues() {

		additionalContact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getAdditionalContact()+ "");
					}
				});
		position.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPosition() + "");
					}
				});
		phoneNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});
		cellular.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular()+ "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		extension.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getExtension() + "");
					}
				});
		pager.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPager() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getStatus() + "");
					}
				});
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 
		 
			fetchBillingLocations();
			fetchAdditionalContacts();
			
			
			txtCompany.setText(company.getName());
	    	txtAddress.setText( company.getAddress());
	    	txtUnitNo.setText( company.getUnitNo());
	    	txtCity.setText( company.getCity());
	    	txtProvince.setText( company.getProvinceState());
	    	txtZip.setText( company.getZip());
	    	txtEmail.setText( company.getEmail());
	    	txtWebsite.setText( company.getWebsite());
	    	txtContact.setText( company.getContact());
	    	txtPosition.setText( company.getPosition());
	    	txtPhone.setText( company.getPhone());
	    	txtExt.setText( company.getExt());
	    	txtFax.setText( company.getFax());
	    	txtPrefix.setText( company.getCompanyPrefix());
	    	txtTollFree.setText( company.getTollfree());
	    	txtCellular.setText( company.getCellular());
	    	txtPager.setText( company.getPager());
	    	txtAfterHours.setText( company.getAfterHours());
			
		 

	}

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		 
		Label label = new Label();

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				company.setName(txtCompany.getText());
				company.setAddress(txtAddress.getText());
				company.setUnitNo(txtUnitNo.getText());
				company.setCity(txtCity.getText());
				company.setProvinceState(txtProvince.getText());
				company.setZip(txtZip.getText());
				company.setEmail(txtEmail.getText());
				company.setWebsite(txtWebsite.getText());
				company.setContact(txtContact.getText());
				company.setPosition(txtPosition.getText());
				company.setPhone(txtPhone.getText());
				company.setExt(txtExt.getText());
				company.setFax(txtFax.getText());
				company.setCompanyPrefix(txtPrefix.getText());
				company.setTollfree(txtTollFree.getText());
				company.setCellular(txtCellular.getText());
				company.setPager(txtPager.getText());
				company.setAfterHours(txtAfterHours.getText());
				
				openAddBillingLocationScreen();
				
				try {
					 closeAddCompanyScreen(btnSaveCompany);
					   
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}

				
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
		if (count == 0) {
			// When user right-click on Table
			tableBillingLocations.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableBillingLocations, event.getScreenX(), event.getScreenY());

				}

			});
			count = 1;
		}

	}

	private void openAddBillingLocationScreen() {
		System.out.println("[openAddDBillingLocScreen]  : Enter");
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_ADD_BILLING_LOCATION_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Billing Location");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception :" + e);
		}
	}
	

	private void openAddAdditionalContactScreen() {
		System.out.println("[openAddDBillingLocScreen]  : Enter");
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_ADD_ADDITIONAL_CONTACT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Additional Contact");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception :" + e);
		}
	}

	@FXML
	private void btnSaveCompanyAction() {
		
		addCompany();
		closeAddCompanyScreen(btnSaveCompany);
	}
	
	//----------=======-----------
	@FXML
	private void btnCancelCompanyAction() {
		listOfBilling = new ArrayList<BillingControllerModel>();
		listOfAdditionalContact = new ArrayList<AdditionalContact>();
		company = new CompanyModel();
		closeAddCompanyScreen(btnCancelCompany);
	}

	private void closeAddCompanyScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addCompany() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					CompanyModel company = setCompanyValue();
					String payload = mapper.writeValueAsString(company);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API,
							null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
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

	private CompanyModel setCompanyValue() {
		
		
		List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
		List<com.dpu.request.AdditionalContact> additionalContacts = new ArrayList<com.dpu.request.AdditionalContact>();
		
		
		company.setName(txtCompany.getText());
		company.setAddress(txtAddress.getText());
		company.setUnitNo(txtUnitNo.getText());
		company.setCity(txtCity.getText());
		company.setProvinceState(txtProvince.getText());
		company.setZip(txtZip.getText());
		company.setEmail(txtEmail.getText());
		company.setWebsite(txtWebsite.getText());
		company.setContact(txtContact.getText());
		company.setPosition(txtPosition.getText());
		company.setPhone(txtPhone.getText());
		company.setExt(txtExt.getText());
		company.setFax(txtFax.getText());
		company.setCompanyPrefix(txtPrefix.getText());
		company.setTollfree(txtTollFree.getText());
		company.setCellular(txtCellular.getText());
		company.setPager(txtPager.getText());
		company.setAfterHours(txtAfterHours.getText());
		
		// need to use for loop here
		
		BillingLocation billingLocation = new BillingLocation();
		if(listOfBilling != null){
			int sizeOfBilling = listOfBilling.size();
			for(int i=0 ; i<sizeOfBilling ;i++){
				BillingControllerModel billingModel = listOfBilling.get(i);
				billingLocation.setName(billingModel.getCompany());
				billingLocation.setAddress(billingModel.getAddress() );
				billingLocation.setCity(billingModel.getCity() );
				billingLocation.setZip(billingModel.getZip() );
				// need to get Status
				billingLocation.setStatus(1);
				billingLocation.setContact(billingModel.getContact());
				billingLocation.setPosition(txtPosition.getText());
				billingLocation.setEmail(txtEmail.getText());
				billingLocation.setCellular(txtCellular.getText());
				billingLocation.setPhone(billingModel.getPhone());
				billingLocation.setExt(txtExt.getText());
				billingLocation.setFax(billingModel.getFax());
				billingLocation.setTollfree(billingModel.getCompany());
				billingLocations.add(billingLocation);
			}
		}
		
		company.setBillingLocations(billingLocations);
		
		// need to use for loop here
		if(listOfAdditionalContact != null){
			int sizeOfAdditionalContact = listOfAdditionalContact.size();
			for(int i=0 ; i<sizeOfAdditionalContact ;i++){	
				
				AdditionalContact additionalContactModel = listOfAdditionalContact.get(i);
				com.dpu.request.AdditionalContact additionalContact = new com.dpu.request.AdditionalContact();
				additionalContact.setCustomerName(additionalContactModel.getAdditionalContact());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExtension());
				additionalContact.setFax(additionalContactModel.getFax());
				// set Pager in prefix.. chnage it
				additionalContact.setPrefix(additionalContactModel.getPager());
				additionalContact.setCellular(additionalContactModel.getCellular());
				// need to set Status here
				additionalContact.setStatus(1);
				additionalContact.setEmail(additionalContactModel.getEmail());
				
				additionalContacts.add(additionalContact);
			}}
		company.setAdditionalContacts(additionalContacts);
		return company;
		 
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		name = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(0);
		address = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(1);
		city = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(2);
		phone = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(3);
		contact = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(4);
		zip = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(5);
		fax = (TableColumn<BillingControllerModel, String>) tableBillingLocations.getColumns().get(6);

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
	
	public   void setValueToCompanyTextField(){
		 
		System.out.println("name : "+company.getName());
		/*txtCompany.setText(company.getName());
    	txtAddress.setText( company.getAddress());
    	txtUnitNo.setText( company.getUnitNo());
    	txtCity.setText( company.getCity());
    	txtProvince.setText( company.getProvinceState());
    	txtZip.setText( company.getZip());
    	txtEmail.setText( company.getEmail());
    	txtWebsite.setText( company.getWebsite());
    	txtContact.setText( company.getContact());
    	txtPosition.setText( company.getPosition());
    	txtPhone.setText( company.getPhone());
    	txtExt.setText( company.getExt());
    	txtFax.setText( company.getFax());
    	txtPrefix.setText( company.getCompanyPrefix());
    	txtTollFree.setText( company.getTollfree());
    	txtCellular.setText( company.getCellular());
    	txtPager.setText( company.getPager());
    	txtAfterHours.setText( company.getAfterHours());
		*/
	}
}