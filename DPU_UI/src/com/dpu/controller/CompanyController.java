
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
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Category;
import com.dpu.model.Company;
import com.dpu.model.Division;
import com.dpu.model.Sale;
import com.dpu.model.Type;
import com.dpu.request.CompanyModel;
import com.dpu.request.CompanyResponse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompanyController extends Application implements Initializable {

	static CompanyAddController companyAddController;
	String filterBy = "Filter By ";
	String newText = filterBy;
	MouseEvent me;

	@FXML
	Pane root, headerPaneCompany;

	private void filterBySelectedValue() {

		tblCompany.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// Check whether item is selected and set value of selected item
				// to Label
				String valu = "";
				Object val = "";
				if (tblCompany.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = tblCompany.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedCells();
					TablePosition tablePosition = (TablePosition) selectedCells.get(0);
					if (!newValue.equals(null))
						val = tablePosition.getTableColumn().getCellData(newValue);
					if (val != null)
						valu = val + "";

					int countt = 0;

					if (valu.length() > 0 && countt == 0) {
						countt = 1;
						String value = valu.toLowerCase();
						ObservableList<CompanyModel> subentries = FXCollections.observableArrayList();

						long count = tblCompany.getColumns().stream().count();
						for (int i = 0; i < tblCompany.getItems().size(); i++) {
							for (int j = 0; j < count; j++) {
								String entry = "" + tblCompany.getColumns().get(j).getCellData(i);
								if (entry.toLowerCase().contains(value)) {
									subentries.add(tblCompany.getItems().get(i));
									break;
								}
							}
						}
						tblCompany.setItems(subentries);
					}

				}

			}

		});

	}

	@FXML
	TableView<CompanyModel> tblCompany;
	@FXML
	private ImageView btnGoCompany;

	@FXML
	private TextField txtGoCompany;

	@FXML
	private void btnGoCompanyAction() {
		String searchCompany = txtGoCompany.getText().trim();

		if (searchCompany != null && searchCompany.length() > 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + searchCompany + "/search",
								null);
						fetchSearchCompanies(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}

		if (searchCompany != null && searchCompany.length() == 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API,
								null);
						fetchSearchCompanies(response);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	public List<CompanyModel> cList = new ArrayList<CompanyModel>();

	@FXML
	TableColumn<CompanyModel, String> unitNo, name, email, city, ps, phone, home, fax, afterHours;

	@FXML
	private void btnAddCompanyAction() {
		/*
		 * String response = null; try { response =
		 * GetAPIClient.callGetAPI(Iconstants.URL_SERVER +
		 * Iconstants.URL_COMPANY_API + "/" + "openAdd", null); } catch
		 * (Exception e) { e.printStackTrace(); }
		 * 
		 * if (response != null) {
		 * 
		 * }
		 */
		CompanyEditController.selectedTabValue = 0;
		CompanyAddController.listOfBilling = new ArrayList<BillingControllerModel>();
		CompanyAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		CompanyAddController.company = new CompanyModel();
		openAddCompanyScreen();

	}

	/*
	 * public TableRow<CompanyModel> rowNumber() { TableRow<CompanyModel> row =
	 * new TableRow<>(); tblCompany.setRowFactory(tv -> {
	 * 
	 * row.setOnMouseClicked(event -> { if (event.getClickCount() == 2 &&
	 * (!row.isEmpty())) { CompanyModel rowData = row.getItem();
	 * System.out.println(rowData); } }); return row; }); return row;
	 * 
	 * }
	 */

	public static Long companyId = 0l;

	@FXML
	private void btnEditCompanyAction() {
		CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
		CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		CompanyEditController.company = new CompanyModel();

		CompanyEditController.selectedTabValue = 0;

		CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
		companyId = companyy.getCompanyId();

		CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();
		if (company != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(),
								null);

						if (response != null && response.length() > 0) {
							CompanyModel c = mapper.readValue(response, CompanyModel.class);

							// ----------------------------------------------

							if (c.getBillingLocations() != null) {
								int billingSize = c.getBillingLocations().size();
								for (int i = 0; i < billingSize; i++) {

									BillingControllerModel bcm = new BillingControllerModel();
									bcm.setCompanyId(c.getCompanyId());
									bcm.setBillingLocationId(c.getBillingLocations().get(i).getBillingLocationId());
									bcm.setAddress(c.getBillingLocations().get(i).getAddress());
									bcm.setCity(c.getBillingLocations().get(i).getCity());
									bcm.setName(c.getBillingLocations().get(i).getName());
									bcm.setContact(c.getBillingLocations().get(i).getContact());
									bcm.setFax(c.getBillingLocations().get(i).getFax());
									bcm.setPhone(c.getBillingLocations().get(i).getPhone());
									bcm.setZip(c.getBillingLocations().get(i).getZip());
									CompanyEditController.listOfBilling.add(bcm);
								}
							}

							if (c.getAdditionalContacts() != null) {
								int addtionalContactSize = c.getAdditionalContacts().size();
								for (int j = 0; j < addtionalContactSize; j++) {
									AdditionalContact additionalContact = new AdditionalContact();

									additionalContact.setCompanyId(c.getCompanyId());
									additionalContact.setAdditionalContactId(
											c.getAdditionalContacts().get(j).getAdditionalContactId());
									additionalContact
											.setCustomerName(c.getAdditionalContacts().get(j).getCustomerName());
									additionalContact.setCellular(c.getAdditionalContacts().get(j).getCellular());
									additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
									additionalContact.setExt(c.getAdditionalContacts().get(j).getExt());
									additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
									additionalContact.setPrefix(c.getAdditionalContacts().get(j).getCellular());
									additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
									additionalContact.setPosition(c.getAdditionalContacts().get(j).getPosition());
									additionalContact.setStatusId("Active");

									CompanyEditController.listOfAdditionalContact.add(additionalContact);
								}
							}

							// -----------------------------------------------------
							CompanyEditController companyAddController = (CompanyEditController) openEditCompanyScreen();
							companyAddController.initData(c);
						}
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	private void btnDeleteCompanyAction() {
		// CompanyModel company =
		// tblCompany.getSelectionModel().getSelectedItem();
		CompanyModel company = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
		if (company != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(),
								null);
						fetchCompanies(response);
						JOptionPane.showMessageDialog(null, "Company Deleted Successfully", "Info", 1);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private void openAddCompanyScreen() {
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
	}

	private Object openEditCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Update Company");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object openAddDuplicateCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add Company");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static boolean unitNumber = true;
	static boolean namee = true;
	static boolean emaill = true;
	static boolean cityy = true;
	static boolean pss = true;
	static boolean phoneNumber = true;
	static boolean homeNumber = true;
	static boolean faxNumber = true;
	static boolean afterHourss = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Login.setWidthForAll(root, tblCompany);
		Login.setWidthForAll(headerPaneCompany, null);
		// textfield.setVisible(false);
		fetchCompanies();
		unitNo.setVisible(unitNumber);
		name.setVisible(namee);
		email.setVisible(emaill);
		city.setVisible(cityy);
		ps.setVisible(pss);
		phone.setVisible(phoneNumber);
		home.setVisible(homeNumber);
		fax.setVisible(faxNumber);
		afterHours.setVisible(afterHourss);
	}

	
	@FXML
	public void clickItem(MouseEvent event) {
		if (event.getClickCount() == 2) // Checking double click
		{
			System.out.println("Doble clicked........");
		}
	}

	int clickCounter = 0;

	@Override
	public void start(Stage stage) {
		/*
		 * System.out.println("START");
		 * 
		 * tblCompany.setOnMouseClicked(new EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { if
		 * (event.getClickCount() > 1) { System.out.println("double clicked!");
		 * } } });
		 * 
		 * tblCompany.setRowFactory(tv -> { TableRow<CompanyModel> row = new
		 * TableRow<>(); row.setOnMouseClicked(event -> { if
		 * (event.getClickCount() == 2 && (!row.isEmpty())) { CompanyModel
		 * rowData = row.getItem(); System.out.println(rowData); } }); return
		 * row; });
		 */
	}

	/*
	 * public static void main(String[] args) { launch(args); }
	 */
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(0);
		name = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(1);
		email = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(2);
		city = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(3);
		ps = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(4);
		phone = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(5);
		home = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(6);
		fax = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(7);
		afterHours = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(8);
	}

	public void fetchCompanies() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ObservableList<CompanyModel> data = null;
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null);
					if (response != null && response.length() > 0) {
						CompanyModel c[] = mapper.readValue(response, CompanyModel[].class);
						cList = new ArrayList<CompanyModel>();
						for (CompanyModel ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblCompany.setItems(data);

					tblCompany.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void fetchCompanies(String response) {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					ObservableList<CompanyModel> data = null;
					cList = new ArrayList<CompanyModel>();
					CompanyResponse compRes = mapper.readValue(response, CompanyResponse.class);

					if (response != null && response.length() > 0) {
						List<CompanyModel> c = compRes.getResultList();
						for (CompanyModel ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblCompany.setItems(data);
					tblCompany.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Againnn.." + e, "Info", 1);
				}
			}
		});
	}

	private void fetchSearchCompanies(String response) {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					ObservableList<CompanyModel> data = null;
					cList = new ArrayList<CompanyModel>();

					if (response != null && response.length() > 0) {
						CompanyModel c[] = mapper.readValue(response, CompanyModel[].class);

						for (CompanyModel ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblCompany.setItems(data);
					tblCompany.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Againnnnnnnnn.." + e, "Info", 1);
				}
			}
		});
	}

	private void setColumnValues() {

		unitNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getUnitNo() + "");
					}
				});
		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});
		ps.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getProvinceState() + "");
					}
				});
		phone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});
		home.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular() + "");
					}
				});
		fax.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});
		afterHours.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CompanyModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
						return new SimpleStringProperty(param.getValue().getAfterHours() + "");
					}
				});
	}

	// ADD MENU

	public int tblCompanyMenuCount = 0;

	// Create ContextMenu
	ContextMenu contextMenu = new ContextMenu();

	@FXML
	public void handleAddContMouseClick(MouseEvent arg0) {
		me = arg0;
		contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		menuAdd(add);
		MenuItem edit = new MenuItem("Edit");
		menuEdit(edit);
		MenuItem delete = new MenuItem("Delete");
		menuDelete(delete);
		MenuItem duplicate = new MenuItem("Duplicate");
		menuDuplicate(duplicate);
		MenuItem personalize = new MenuItem("Personalize");
		menuPersonalize(personalize);
		MenuItem filterBy = new MenuItem("Filter By");
		menuFilterBy(filterBy);
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		menuFilterByExclude(filterByExclude);
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");
		menuClearAllFilter(clearAllFilters);

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude,
				clearAllFilters);

		if (tblCompanyMenuCount == 0) {
			tblCompanyMenuCount++;
			// When user right-click on Table
			tblCompany.setOnMouseClicked(new EventHandler<MouseEvent>() {
				int click = 0;

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

						if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
							contextMenu.show(tblCompany, mouseEvent.getScreenX(), mouseEvent.getScreenY());
							// textfield.setVisible(false);
							// contextMenu1 = new ContextMenu();
							// contextMenu1.hide();
						} else {
							contextMenu.hide();
							click++;
							// System.out.println("now doublyyy" + click);

						}
						if (click == 2) {
							btnEditCompanyAction();
							// System.out.println("try doble====");
							click = 0;
						}

					}
					/*
					 * try { Thread.sleep(2000); if (click == 1) { click = 0; }
					 * } catch (Exception e) {
					 * 
					 * }
					 */
				}

			});

		}

	}

	private void menuClearAllFilter(MenuItem item8) {
		item8.setStyle("-fx-padding: 0 10 0 10;");
		item8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}
		});
	}

	private void menuFilterByExclude(MenuItem item7) {
		item7.setStyle("-fx-padding: 0 10 0 10;");
		item7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}
		});
	}

	private void menuFilterBy(MenuItem item6) {
		item6.setStyle("-fx-padding: 0 10 0 10;");

		item6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// textfield.setVisible(true);
				item6.setText(newText);
				filterBySelectedValue();
			}
		});
	}

	private void menuPersonalize(MenuItem item5) {
		item5.setStyle("-fx-padding: 0 10 0 10;");
		item5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_PERSONALIZE_SCREEN));

					Parent root = (Parent) fxmlLoader.load();

					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("PERSONALIZE");
					stage.setScene(new Scene(root));
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void menuDuplicate(MenuItem item4) {
		item4.setStyle("-fx-padding: 0 10 0 10;");
		item4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
				CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				CompanyEditController.company = new CompanyModel();

				CompanyEditController.selectedTabValue = 0;

				CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				companyId = companyy.getCompanyId();

				CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();

				if (company != null) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								ObjectMapper mapper = new ObjectMapper();
								String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);

								if (response != null && response.length() > 0) {
									CompanyModel c = mapper.readValue(response, CompanyModel.class);

									if (c.getBillingLocations() != null) {
										int billingSize = c.getBillingLocations().size();
										for (int i = 0; i < billingSize; i++) {

											BillingControllerModel bcm = new BillingControllerModel();
											bcm.setCompanyId(c.getCompanyId());
											bcm.setBillingLocationId(
													c.getBillingLocations().get(i).getBillingLocationId());
											bcm.setAddress(c.getBillingLocations().get(i).getAddress());
											bcm.setCity(c.getBillingLocations().get(i).getCity());
											bcm.setName(c.getBillingLocations().get(i).getName());
											bcm.setContact(c.getBillingLocations().get(i).getContact());
											bcm.setFax(c.getBillingLocations().get(i).getFax());
											bcm.setPhone(c.getBillingLocations().get(i).getPhone());
											bcm.setZip(c.getBillingLocations().get(i).getZip());
											CompanyEditController.listOfBilling.add(bcm);
										}
									}

									if (c.getAdditionalContacts() != null) {
										int addtionalContactSize = c.getAdditionalContacts().size();
										for (int j = 0; j < addtionalContactSize; j++) {
											AdditionalContact additionalContact = new AdditionalContact();

											additionalContact.setCompanyId(c.getCompanyId());
											additionalContact.setAdditionalContactId(
													c.getAdditionalContacts().get(j).getAdditionalContactId());
											additionalContact.setCustomerName(
													c.getAdditionalContacts().get(j).getCustomerName());
											additionalContact
													.setCellular(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
											additionalContact.setExt(c.getAdditionalContacts().get(j).getExt());
											additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
											additionalContact.setPrefix(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
											additionalContact
													.setPosition(c.getAdditionalContacts().get(j).getPosition());
											additionalContact.setStatusId("Active");

											CompanyEditController.listOfAdditionalContact.add(additionalContact);
										}
									}

									CompanyAddController companyAddController = (CompanyAddController) openAddDuplicateCompanyScreen();
									companyAddController.initData(c);
								}
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
							}
						}
					});
				}

			}
		});
	}

	private void menuDelete(MenuItem item3) {
		item3.setStyle("-fx-padding: 0 10 0 10;");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CompanyModel company = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				if (company != null) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER
										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);

								fetchCompanies(response);
								JOptionPane.showMessageDialog(null, "Company Deleted Successfully", "Info", 1);
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
							}
						}
					});
				}
			}
		});
	}

	private void menuEdit(MenuItem item2) {
		item2.setStyle("-fx-padding: 0 10 0 10;");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
				CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				CompanyEditController.company = new CompanyModel();

				CompanyEditController.selectedTabValue = 0;

				CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				companyId = companyy.getCompanyId();
				CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();
				if (company != null) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								ObjectMapper mapper = new ObjectMapper();
								String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER
										+ Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);

								if (response != null && response.length() > 0) {
									CompanyModel c = mapper.readValue(response, CompanyModel.class);

									if (c.getBillingLocations() != null) {
										int billingSize = c.getBillingLocations().size();
										for (int i = 0; i < billingSize; i++) {

											BillingControllerModel bcm = new BillingControllerModel();
											bcm.setCompanyId(c.getCompanyId());
											bcm.setBillingLocationId(
													c.getBillingLocations().get(i).getBillingLocationId());
											bcm.setAddress(c.getBillingLocations().get(i).getAddress());
											bcm.setCity(c.getBillingLocations().get(i).getCity());
											bcm.setName(c.getBillingLocations().get(i).getName());
											bcm.setContact(c.getBillingLocations().get(i).getContact());
											bcm.setFax(c.getBillingLocations().get(i).getFax());
											bcm.setPhone(c.getBillingLocations().get(i).getPhone());
											bcm.setZip(c.getBillingLocations().get(i).getZip());
											CompanyEditController.listOfBilling.add(bcm);
										}
									}

									if (c.getAdditionalContacts() != null) {
										int addtionalContactSize = c.getAdditionalContacts().size();
										for (int j = 0; j < addtionalContactSize; j++) {
											AdditionalContact additionalContact = new AdditionalContact();

											additionalContact.setCompanyId(c.getCompanyId());
											additionalContact.setAdditionalContactId(
													c.getAdditionalContacts().get(j).getAdditionalContactId());
											additionalContact.setCustomerName(
													c.getAdditionalContacts().get(j).getCustomerName());
											additionalContact
													.setCellular(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
											additionalContact.setExt(c.getAdditionalContacts().get(j).getExt());
											additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
											additionalContact.setPrefix(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
											additionalContact
													.setPosition(c.getAdditionalContacts().get(j).getPosition());
											additionalContact.setStatusId("Active");

											CompanyEditController.listOfAdditionalContact.add(additionalContact);
										}
									}

									CompanyEditController companyEditController = (CompanyEditController) openEditCompanyScreen();
									companyEditController.initData(c);
								}
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
							}
						}
					});
				}
			}
		});
	}

	private void menuAdd(MenuItem item1) {
		item1.setStyle("-fx-padding: 0 10 0 10;");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				CompanyEditController.selectedTabValue = 0;
				CompanyAddController.listOfBilling = new ArrayList<BillingControllerModel>();
				CompanyAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				CompanyAddController.company = new CompanyModel();
				openAddCompanyScreen();

			}
		});
	}

	public void getLoadNewMenu(MouseEvent mouseEvent) {
		contextMenu = new ContextMenu();
		MenuItem add = new MenuItem("Add");
		menuAdd(add);
		MenuItem edit = new MenuItem("Edit");
		menuEdit(edit);
		MenuItem delete = new MenuItem("Delete");
		menuDelete(delete);
		MenuItem duplicate = new MenuItem("Duplicate");
		menuDuplicate(duplicate);
		MenuItem personalize = new MenuItem("Personalize");
		menuPersonalize(personalize);
		MenuItem filterBy = new MenuItem(newText);
		menuFilterBy(filterBy);
		MenuItem filterByExclude = new MenuItem("Filter By Exclude");
		menuFilterByExclude(filterByExclude);
		MenuItem clearAllFilters = new MenuItem("Clear All Filters");
		menuClearAllFilter(clearAllFilters);

		contextMenu.getItems().addAll(add, edit, delete, duplicate, personalize, filterBy, filterByExclude,
				clearAllFilters);

		if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
			if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.SECONDARY)) {
				// textfield.setVisible(false);
				contextMenu.show(tblCompany, mouseEvent.getScreenX(), mouseEvent.getScreenY());

			} else {
				contextMenu.hide();
			}
		}
		// contextMenu1 = new ContextMenu();
	}

}