package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.controller.ValidationController;
import com.dpu.model.AdditionalContact;
import com.dpu.model.VendorBillingLocation;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Vendor;
import com.dpu.model.VendorBillingLocation;
import com.dpu.request.CompanyModel;
import com.dpu.util.RightMenu;
import com.dpu.util.Validate;

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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VendorAddController extends Application implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane addVendorPane;

	@FXML
	private TableColumn<AdditionalContact, String> additionalContact, position, phoneNo, faxNo, cellular, email, 
	extension, pager, status;

	@FXML
	private Button btnSaveVendor, btnCancelVendor;

	@FXML
	private TableColumn<VendorBillingLocation, String> address, city, contact, fax, name, phone, zip;

	@FXML
	public TableView<VendorBillingLocation> tableBillingLocations;

	@FXML
	private TableView<AdditionalContact> tableAdditionalContact;

	@FXML
	public TextField txtAddress, txtAfterHours, txtCellular, txtCity, txtCompany, txtContact, txtEmail, txtExt, txtFax, txtPager, 
	txtPhone, txtPosition, txtPrefix, txtProvince, txtTollFree, txtUnitNo, txtWebsite, txtZip;

	@FXML
	private TabPane tabPane;

	Validate validate = new Validate();
	public static int addAddtionalContact = 0;
	public static VendorBillingLocation VendorBillingLocation = new VendorBillingLocation();
	public static AdditionalContact additionalContactModel = new AdditionalContact();
	public static ArrayList<VendorBillingLocation> listOfBilling = new ArrayList<VendorBillingLocation>();
	public static ArrayList<AdditionalContact> listOfAdditionalContact = new ArrayList<AdditionalContact>();
	public static Vendor vendor = new Vendor();
	int additionalContactCountMenu = 0;
	public static int selectedTabValue = 0;
	ContextMenu contextMenu = new ContextMenu();
	int billingLocationCountMenu = 0;
	public static int addEditIndex = -1;
	// public static int editIndex = -1;
	public static int add = 0;
	MouseEvent me;

	public StringBuffer validsteFields() {
		StringBuffer strBuff = new StringBuffer();
		String customerName = txtCompany.getText();
		String email = txtEmail.getText();

		if (customerName == null || customerName.trim().equals("")) {
			txtCompany.setStyle("-fx-focus-color: #87CEEB;");
			strBuff.append("Company Name is Mandatory\n");
		}
		if (email == null || email.trim().equals("")) {
			txtEmail.setStyle("-fx-focus-color: #87CEEB;");
			strBuff.append("Email is Mandatory\n");
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

	private boolean validateAddEquipmentScreen() {
		String customerName = txtCompany.getText();
		String email = txtEmail.getText();

		boolean result = validate.validateEmptyness(customerName);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			return result;
		}
		result = validate.validateEmptyness(email);
		if (!result) {
			ValidationController.str = validsteFields();
			openValidationScreen();
			txtEmail.setStyle("-fx-focus-color: red;");
			txtEmail.requestFocus();
			return result;
		}

		return result;
	}
	
	@FXML
	public void billingLocationMouseClick(MouseEvent arg0) {
		me = arg0;
		contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		rightMenu.menuAdd(add, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_BILLING_LOCATION_ADD_SCREEN, "Add New Billing Location");
		MenuItem edit = new MenuItem("Edit");
		rightMenu.menuEdit(edit, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_BILLING_LOCATION_EDIT_SCREEN, "Edit Billing Location");
		MenuItem delete = new MenuItem("Delete");
		MenuItem duplicate = new MenuItem("Duplicate");
		MenuItem personalize = new MenuItem("Personalize");
		MenuItem filterBy = new MenuItem("Filter By");
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude, clearAllFilters);
			// When user right-click on Table
		tableBillingLocations.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
						contextMenu.show(tableBillingLocations, mouseEvent.getScreenX(), mouseEvent.getScreenY());
					} else {
						contextMenu.hide();
					}
				}
			}
		});
	}
	
	RightMenu rightMenu = new RightMenu();
	
	@FXML
	public void additionalContactMouseClick(MouseEvent arg0) {
		me = arg0;
		contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		rightMenu.menuAdd(add, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_ADD_SCREEN, "Add New Additional Contact");
		MenuItem edit = new MenuItem("Edit");
		rightMenu.menuEdit(edit, Iconstants.VENDOR_BASE_PACKAGE, Iconstants.XML_VENDOR_ADDITIONAL_CONTACT_EDIT_SCREEN, "Edit Additional Contact");
		MenuItem delete = new MenuItem("Delete");
		MenuItem duplicate = new MenuItem("Duplicate");
		MenuItem personalize = new MenuItem("Personalize");
		MenuItem filterBy = new MenuItem("Filter By");
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude, clearAllFilters);
			// When user right-click on Table
		tableAdditionalContact.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
						contextMenu.show(tableAdditionalContact, mouseEvent.getScreenX(), mouseEvent.getScreenY());
					} else {
						contextMenu.hide();
					}
				}
			}
		});
	}
	
	public void fetchBillingLocations() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (VendorEditController.listOfBilling != null & !(VendorEditController.listOfBilling.isEmpty())) {
						ObservableList<VendorBillingLocation> data = FXCollections.observableArrayList(VendorEditController.listOfBilling);
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
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});
		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});
		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});
		phone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		contact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getContact() + "");
					}
				});
		zip.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
						return new SimpleStringProperty(param.getValue().getZip() + "");
					}
				});
		fax.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
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

					if (VendorEditController.listOfAdditionalContact != null
							& !(VendorEditController.listOfAdditionalContact.isEmpty())) {
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(VendorEditController.listOfAdditionalContact);
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
						return new SimpleStringProperty(param.getValue().getCustomerName() + "");
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
						return new SimpleStringProperty(param.getValue().getCellular() + "");
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
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});
		pager.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPrefix() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getStatusId() + "");
					}
				});
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	

	// Create ContextMenu
	@FXML
	public void handleMouseClick(MouseEvent arg0) {

	/*	// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				add = 1;
				selectedTabValue = 0;
				vendor.setName(txtCompany.getText());
				vendor.setAddress(txtAddress.getText());
				vendor.setUnitNo(txtUnitNo.getText());
				vendor.setCity(txtCity.getText());
				vendor.setProvinceState(txtProvince.getText());
				vendor.setZip(txtZip.getText());
				vendor.setEmail(txtEmail.getText());
				vendor.setWebsite(txtWebsite.getText());
				vendor.setContact(txtContact.getText());
				vendor.setPosition(txtPosition.getText());
				vendor.setPhone(txtPhone.getText());
				vendor.setExt(txtExt.getText());
				vendor.setFax(txtFax.getText());
				vendor.setVendorPrefix(txtPrefix.getText());
				vendor.setTollfree(txtTollFree.getText());
				vendor.setCellular(txtCellular.getText());
				vendor.setPager(txtPager.getText());
				vendor.setAfterHours(txtAfterHours.getText());

				openAddBillingLocationScreen();

				try {
					closeAddVendorScreen(btnSaveVendor);

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
				selectedTabValue = 0;
				add = 0;
				addEditIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorBillingLocation = tableBillingLocations.getSelectionModel().getSelectedItem();
				openAddBillingLocationScreen();
				closeAddVendorScreen(btnSaveVendor);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;
				addEditIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				VendorEditController.listOfBilling.remove(addEditIndex);
				addEditIndex = -1;

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("Add New Company");
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				closeAddVendorScreen(btnSaveVendor);
			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);
		if (billingLocationCountMenu == 0) {
			billingLocationCountMenu++;
			// When user right-click on Table
			tableBillingLocations.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableBillingLocations, event.getScreenX(), event.getScreenY());
				}
			});
		}*/
	}

	private void openAddBillingLocationScreen() {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_ADD_BILLING_LOCATION_SCREEN));
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

	private void openAddAdditionalContactScreen() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_ADD_ADDITIONAL_CONTACT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Additional Contact");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void btnSaveVendorAction() {

//		boolean result = validateAddEquipmentScreen();
//		if (result) {
			addVendor();
			closeAddVendorScreen(btnSaveVendor);
//		}

	}

	@FXML
	private void btnCancelVendorAction() {
		VendorEditController.listOfBilling = new ArrayList<VendorBillingLocation>();
		VendorEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		vendor = new Vendor();
		closeAddVendorScreen(btnCancelVendor);
	}

	private void closeAddVendorScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void addVendor() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Vendor vendor = setVendorValue();
					String payload = mapper.writeValueAsString(vendor);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API,
							null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.vendorController.fetchVendors(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	/*public void addCompany(String reponse) {

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
	}*/

	private Vendor setVendorValue() {

		List<VendorBillingLocation> billingLocations = new ArrayList<VendorBillingLocation>();
		List<AdditionalContact> additionalContacts = new ArrayList<AdditionalContact>();

		vendor.setName(txtCompany.getText());
		vendor.setAddress(txtAddress.getText());
		vendor.setUnitNo(txtUnitNo.getText());
		vendor.setCity(txtCity.getText());
		vendor.setProvinceState(txtProvince.getText());
		vendor.setZip(txtZip.getText());
		vendor.setEmail(txtEmail.getText());
		vendor.setWebsite(txtWebsite.getText());
		vendor.setContact(txtContact.getText());
		vendor.setPosition(txtPosition.getText());
		vendor.setPhone(txtPhone.getText());
		vendor.setExt(txtExt.getText());
		vendor.setFax(txtFax.getText());
		vendor.setVendorPrefix(txtPrefix.getText());
		vendor.setTollfree(txtTollFree.getText());
		vendor.setCellular(txtCellular.getText());
		vendor.setPager(txtPager.getText());
		vendor.setAfterHours(txtAfterHours.getText());

		// need to use for loop here

		if (VendorEditController.listOfBilling != null) {
			int sizeOfBilling = VendorEditController.listOfBilling.size();
			for (int i = 0; i < sizeOfBilling; i++) {
				VendorBillingLocation billingLocation = new VendorBillingLocation();
				VendorBillingLocation billingModel = VendorEditController.listOfBilling.get(i);
				billingLocation.setName(billingModel.getName());
				billingLocation.setAddress(billingModel.getAddress());
				billingLocation.setCity(billingModel.getCity());
				billingLocation.setZip(billingModel.getZip());
				// need to get Status
				billingLocation.setStatus(1l);
				billingLocation.setContact(billingModel.getContact());
				billingLocation.setPosition(txtPosition.getText());
				billingLocation.setEmail(txtEmail.getText());
				billingLocation.setCellular(txtCellular.getText());
				billingLocation.setPhone(billingModel.getPhone());
				billingLocation.setExt(txtExt.getText());
				billingLocation.setFax(billingModel.getFax());
				billingLocation.setTollfree(billingModel.getName());
				billingLocations.add(billingLocation);
			}
		}

		vendor.setBillingLocations(billingLocations);

		// need to use for loop here
		if (VendorEditController.listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = VendorEditController.listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				AdditionalContact additionalContactModel = VendorEditController.listOfAdditionalContact.get(i);
				AdditionalContact additionalContact = new AdditionalContact();
				additionalContact.setCustomerName(additionalContactModel.getCustomerName());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExt());
				additionalContact.setFax(additionalContactModel.getFax());
				// set Pager in prefix.. chnage it
				additionalContact.setPrefix(additionalContactModel.getPrefix());
				additionalContact.setCellular(additionalContactModel.getCellular());
				// need to set Status here
				additionalContact.setStatus(0l);
				additionalContact.setEmail(additionalContactModel.getEmail());

				additionalContacts.add(additionalContact);
			}
		}
		vendor.setAdditionalContacts(additionalContacts);
		return vendor;

	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		name = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(0);
		address = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(1);
		city = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(2);
		phone = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(3);
		contact = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(4);
		zip = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(5);
		fax = (TableColumn<VendorBillingLocation, String>) tableBillingLocations.getColumns().get(6);

	}

	private Object showPanel(String basePackage, String screenName) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(basePackage + screenName));
			Parent root = (Parent) fxmlLoader.load();
			Pane pane = (Pane) root;

			ObservableList<Node> nodes = addVendorPane.getChildren();

			if (nodes != null && nodes.size() >= 4 && nodes.get(3) != null) {
				nodes.remove(3);
				nodes.add(3, pane);
			} else {
				nodes.add(pane);
			}
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void initData(CompanyModel c) {
		// fetchBillingLocations();
		// fetchAdditionalContacts();
		txtCompany.setText(c.getName());
		txtContact.setText(c.getContact());
		txtAddress.setText(c.getAddress());
		txtPosition.setText(c.getPosition());
		txtUnitNo.setText(c.getUnitNo());
		txtPhone.setText(c.getPhone());
		txtExt.setText(c.getExt());
		txtCity.setText(c.getCity());
		txtFax.setText(c.getFax());
		txtPrefix.setText(c.getCompanyPrefix());
		txtProvince.setText(c.getProvinceState());
		txtZip.setText(c.getZip());
		txtAfterHours.setText(c.getAfterHours());
		txtEmail.setText(c.getEmail());
		txtTollFree.setText(c.getTollfree());
		txtWebsite.setText(c.getWebsite());
		txtCellular.setText(c.getCellular());
		txtPager.setText(c.getPager());
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}