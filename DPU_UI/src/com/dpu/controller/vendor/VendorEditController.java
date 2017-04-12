package com.dpu.controller.vendor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.DeleteAPIClient;
import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.controller.MainScreen;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.Vendor;
import com.dpu.model.VendorBillingLocation;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VendorEditController extends Application implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane addVendorPane;

	@FXML
	private TabPane tabPane;

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

	@FXML
	private TableColumn<BillingControllerModel, String> address;

	@FXML
	private Button btnSaveVendor;

	@FXML
	private Button btnCancelVendor;

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
	public TableView<BillingControllerModel> tableBillingLocations;

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
	public TableColumn<VendorBillingLocation, String> zip;

	@FXML
	Button btnUpdateVendor;

	public static int editIndex = -1;
	public static int addBillingLocation = 0;
	public static int addAddtionalContact = 0;
	public static BillingControllerModel billingControllerModel = new BillingControllerModel();
	public static AdditionalContact additionalContactModel = new AdditionalContact();
	public static ArrayList<BillingControllerModel> listOfBilling = new ArrayList<BillingControllerModel>(); 
	public static ArrayList<AdditionalContact> listOfAdditionalContact = new ArrayList<AdditionalContact>();

	public static Vendor vendor = new Vendor();

	Long vendorId = 0l;

	public static int selectedTabValue = 0;

	@FXML
	private void btnUpdateVendorAction() {
		editVendor();
		closeEditVendorScreen(btnUpdateVendor);

	}

	private void closeEditVendorScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	private void editVendor() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Vendor vendor = setVendorValue();
					String payload = mapper.writeValueAsString(vendor);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + vendorId, null, payload);

					if (response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
					} else {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
					}
					MainScreen.vendorController.fetchVendors(response);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchBillingLocations();
		fetchAdditionalContacts();

		txtCompany.setText(vendor.getName());
		txtAddress.setText(vendor.getAddress());
		txtUnitNo.setText(vendor.getUnitNo());
		txtCity.setText(vendor.getCity());
		txtProvince.setText(vendor.getProvinceState());
		txtZip.setText(vendor.getZip());
		txtEmail.setText(vendor.getEmail());
		txtWebsite.setText(vendor.getWebsite());
		txtContact.setText(vendor.getContact());
		txtPosition.setText(vendor.getPosition());
		txtPhone.setText(vendor.getPhone());
		txtExt.setText(vendor.getExt());
		txtFax.setText(vendor.getFax());
		txtPrefix.setText(vendor.getVendorPrefix());
		txtTollFree.setText(vendor.getTollfree());
		txtCellular.setText(vendor.getCellular());
		txtPager.setText(vendor.getPager());
		txtAfterHours.setText(vendor.getAfterHours());
		tabPane.getSelectionModel().select(selectedTabValue);

	}

	@Override
	public void start(Stage primaryStage) {

	}

	/*public static void main(String[] args) {
		launch(args);
	}*/

	private Vendor setVendorValue() {

		List<VendorBillingLocation> billingLocations = new ArrayList<VendorBillingLocation>();
		List<AdditionalContact> additionalContacts = new ArrayList<AdditionalContact>();

		// company.setCompanyId(companyId.toString());
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

		if (listOfBilling != null) {
			int sizeOfBilling = listOfBilling.size();
			for (int i = 0; i < sizeOfBilling; i++) {
				VendorBillingLocation billingLocation = new VendorBillingLocation();
				BillingControllerModel billingModel = listOfBilling.get(i);
				if (billingModel.getBillingLocationId() != null)
					billingLocation.setBillingLocationId(billingModel.getBillingLocationId());
				billingLocation.setName(billingModel.getName());
				billingLocation.setAddress(billingModel.getAddress());
				billingLocation.setCity(billingModel.getCity());
				billingLocation.setZip(billingModel.getZip());
				// need to get Status
				billingLocation.setStatus(1);
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
		if (listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				AdditionalContact additionalContactModel = listOfAdditionalContact.get(i);
				AdditionalContact additionalContact = new AdditionalContact();

				if (additionalContactModel.getAdditionalContactId() != null)
					additionalContact.setAdditionalContactId(additionalContactModel.getAdditionalContactId());
				additionalContact.setCustomerName(additionalContactModel.getCustomerName());
				additionalContact.setPosition(additionalContactModel.getPosition());
				additionalContact.setPhone(additionalContactModel.getPhone());
				additionalContact.setExt(additionalContactModel.getExt());
				additionalContact.setFax(additionalContactModel.getFax());
				additionalContact.setPrefix(additionalContactModel.getPrefix());
				additionalContact.setCellular(additionalContactModel.getCellular());
				additionalContact.setStatus(1l);
				additionalContact.setEmail(additionalContactModel.getEmail());
				additionalContacts.add(additionalContact);
			}
		}
		vendor.setAdditionalContacts(additionalContacts);

		return vendor;
	}

	public void initData(Vendor c) {
		vendorId =  c.getVendorId();
		txtCompany.setText(c.getName());
		txtContact.setText(c.getContact());
		txtAddress.setText(c.getAddress());
		txtPosition.setText(c.getPosition());
		txtUnitNo.setText(c.getUnitNo());
		txtPhone.setText(c.getPhone());
		txtExt.setText(c.getExt());
		txtCity.setText(c.getCity());
		txtFax.setText(c.getFax());
		txtPrefix.setText(c.getVendorPrefix());
		txtProvince.setText(c.getProvinceState());
		txtZip.setText(c.getZip());
		txtAfterHours.setText(c.getAfterHours());
		txtEmail.setText(c.getEmail());
		txtTollFree.setText(c.getTollfree());
		txtWebsite.setText(c.getWebsite());
		txtCellular.setText(c.getCellular());
		txtPager.setText(c.getPager());
	}

	@FXML
	private void btnCancelVendorAction() {
		listOfBilling = new ArrayList<BillingControllerModel>();
		listOfAdditionalContact = new ArrayList<AdditionalContact>();
		vendor = new Vendor();
		closeAddVendorScreen(btnCancelVendor);
	}

	@FXML
	private void btnSaveCompanyAction() {

		vendorId = VendorController.vendorId;
		// remove from listOfBilling if available in db

		/*
		 * if(listOfBilling != null && !(listOfBilling.isEmpty())){ int index =
		 * 0 ; int billingSize = listOfBilling.size(); for(int
		 * i=0;i<billingSize;i++){
		 * 
		 * if(listOfBilling.get(index).getBillingLocationId() != null){
		 * BillingControllerModel bcm = listOfBilling.get(index);
		 * listOfBilling.remove(bcm); }else{ index++; } } }
		 */
		// remove from listOfAdditionalContact if available in db

		/*
		 * if(listOfAdditionalContact != null &&
		 * !(listOfAdditionalContact.isEmpty())){ int index = 0 ; int
		 * additionalContactSize = listOfAdditionalContact.size(); for(int
		 * i=0;i<additionalContactSize;i++){
		 * 
		 * if(listOfAdditionalContact.get(index).getAdditionalContactId() !=
		 * null){ AdditionalContact additionalContact =
		 * listOfAdditionalContact.get(index);
		 * listOfAdditionalContact.remove(additionalContact);
		 * 
		 * }else{ index++; } } }
		 */

		// listOfBilling = new ArrayList<BillingControllerModel>();
		// listOfAdditionalContact = new ArrayList<AdditionalContact>();
		addVendor();
		closeAddVendorScreen(btnSaveVendor);
	}

	int additionalContactMenuCount = 0;

	/*@FXML
	void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//add = 1;
				setValuesToCmpanyTextField();
				addAddtionalContact = 1;
				selectedTabValue = 1;
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
				setValuesToCmpanyTextField();
				selectedTabValue = 1;
				addAddtionalContact = 0;
				editIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				additionalContactModel = tableAdditionalContact.getSelectionModel().getSelectedItem();

				if (additionalContactModel.getAdditionalContactId() != null)
					additionalContactIdPri = additionalContactModel.getAdditionalContactId();
				openAddAdditionalContactScreen();
				closeAddCompanyScreen(btnSaveCompany);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 1;
				setValuesToCmpanyTextField();
				editIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
 
				Long additionalontactId = listOfAdditionalContact.get(editIndex).getAdditionalContactId();
				Long companyId = listOfAdditionalContact.get(editIndex).getCompanyId();

				if (additionalontactId == null) {
					listOfAdditionalContact.remove(editIndex);
					JOptionPane.showMessageDialog(null, "Additional Contact Deleted SuccessFully.", "Info", 1);

				} else {

					// hit api to delete Additional Conatct
					try {
						String response = DeleteAPIClient
								.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_DELETE_BILLING_LOCATION_API + "/"
										+ companyId + "/additionalcontacts/" + additionalontactId, null);
						listOfAdditionalContact.remove(editIndex);

						ObjectMapper mapper = new ObjectMapper();

						if (response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// }
				editIndex = -1;

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("Update Company");
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				closeAddCompanyScreen(btnSaveCompany);

			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);
		if (additionalContactMenuCount == 0) {
			additionalContactMenuCount++;
			// When user right-click on Table
			tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableAdditionalContact, event.getScreenX(), event.getScreenY());

				}

			});

		}

	}*/

	private void openAddAdditionalContactScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_EDIT_ADDITIONAL_CONTACT_SCREEN));
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
					// companyId = Long.parseLong(company.getCompanyId());

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_VENDOR_API + "/" + vendorId, null, payload);

					if (response != null) {
						// Success success = mapper.readValue(response,
						// Success.class);
						JOptionPane.showMessageDialog(null, "Vendor Updated Successfully.", "Info", 1);
					} else {
						// Failed failed = mapper.readValue(response,
						// Failed.class);
						JOptionPane.showMessageDialog(null, "Failed to Updated Vendor.", "Info", 1);
					}

					closeEditVendorScreen(btnSaveVendor);
					MainScreen.vendorController.fetchVendors(response);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	int billingLocationCountMenu = 0;
	public static Long billingLocationIdPri = 0l;
	public static Long additionalContactIdPri = 0l;

	/*@FXML
	public void handleMouseClick(MouseEvent arg0) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addBillingLocation = 1;
				selectedTabValue = 0;
				//add = 1;
				setValuesToCmpanyTextField();
				openAddBillingLocationScreen();

				try {
					closeAddCompanyScreen(btnSaveVendor);

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
				setValuesToCmpanyTextField();
				addBillingLocation = 0;
				editIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				billingControllerModel = tableBillingLocations.getSelectionModel().getSelectedItem();

				if (billingControllerModel.getBillingLocationId() != null)
					billingLocationIdPri = billingControllerModel.getBillingLocationId();

				openAddBillingLocationScreen();
				closeAddCompanyScreen(btnSaveCompany);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;

				editIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();

				// hit API to remove record from db.
				// if (listOfBilling.get(editIndex).getBillingLocationId() ==
				// null
				// || listOfBilling.get(editIndex).getBillingLocationId() != 0l)
				// {
				Long billingId = listOfBilling.get(editIndex).getBillingLocationId();
				Long companyId = listOfBilling.get(editIndex).getCompanyId();

				if (billingId == null) {
					JOptionPane.showMessageDialog(null, "Billing Location Deleted SuccessFully.", "Info", 1);
					listOfBilling.remove(editIndex);
				} else {

					try {
						String response = DeleteAPIClient
								.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_DELETE_BILLING_LOCATION_API + "/"
										+ companyId + "/billinglocations/" + billingId, null);
						listOfBilling.remove(editIndex);

						ObjectMapper mapper = new ObjectMapper();

						if (response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// }
				editIndex = -1;

				setValuesToCmpanyTextField();

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("Add New Company");
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

				closeAddCompanyScreen(btnSaveCompany);

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
		}
	}*/

	private void openAddBillingLocationScreen() {

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.VENDOR_BASE_PACKAGE + Iconstants.XML_EDIT_BILLING_LOCATION_SCREEN));
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

	// new added
	public void fetchBillingLocations() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfBilling != null & !(listOfBilling.isEmpty())) {
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

	public void fetchAdditionalContacts() {
		// fetchColumns();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (listOfAdditionalContact != null & !(listOfAdditionalContact.isEmpty())) {
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(listOfAdditionalContact);
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

	private void setColumnValues() {

		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BillingControllerModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BillingControllerModel, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
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
				new Callback<TableColumn.CellDataFeatures<VendorBillingLocation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<VendorBillingLocation, String> param) {
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

	public void setValuesToCmpanyTextField() {
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

	}

}