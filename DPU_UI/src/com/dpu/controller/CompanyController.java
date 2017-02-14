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
import com.dpu.model.Failed;
import com.dpu.model.Success;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompanyController extends Application implements Initializable {

	static CompanyAddController companyAddController;
	@FXML
	TableView<CompanyModel> tblCompany;

	public List<CompanyModel> cList = null;

	@FXML
	TableColumn<CompanyModel, String> unitNo, name, email, city, ps, phone, home, fax, afterHours;

	@FXML
	private void btnAddCompanyAction() {
		CompanyEditController.selectedTabValue = 0 ;
		CompanyAddController.listOfBilling = new ArrayList<BillingControllerModel>();
		CompanyAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		CompanyAddController.company = new CompanyModel();
		openAddCompanyScreen();

	}

	public static Long companyId = 0l;

	@FXML
	private void btnEditCompanyAction() {
		CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
		CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		CompanyEditController.company = new CompanyModel();
		
		CompanyEditController.selectedTabValue = 0 ;

		CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
		companyId = Long.parseLong(companyy.getCompanyId());

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
									bcm.setCompanyId(Long.parseLong(c.getCompanyId()));
									bcm.setBillingLocationId(c.getBillingLocations().get(i).getBillingLocationId());
									bcm.setAddress(c.getBillingLocations().get(i).getAddress());
									bcm.setCity(c.getBillingLocations().get(i).getCity());
									bcm.setCompany(c.getBillingLocations().get(i).getName());
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

									additionalContact.setCompanyId(Long.parseLong(c.getCompanyId()));
									additionalContact.setAdditionalContactId(
											c.getAdditionalContacts().get(j).getAdditionalContactId());
									additionalContact
											.setAdditionalContact(c.getAdditionalContacts().get(j).getCustomerName());
									additionalContact.setCellular(c.getAdditionalContacts().get(j).getCellular());
									additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
									additionalContact.setExtension(c.getAdditionalContacts().get(j).getExt());
									additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
									additionalContact.setPager(c.getAdditionalContacts().get(j).getCellular());
									additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
									additionalContact.setPosition(c.getAdditionalContacts().get(j).getPosition());
									additionalContact.setStatus(c.getAdditionalContacts().get(j).getStatus() + "");

									CompanyEditController.listOfAdditionalContact.add(additionalContact);
								}
							}

							// -----------------------------------------------------
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchCompanies();
	}

	@Override
	public void start(Stage stage) {

	}

	public static void main(String[] args) {
		launch(args);
	}

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
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null);
					if (response != null && response.length() > 0) {
						CompanyModel c[] = mapper.readValue(response, CompanyModel[].class);
						cList = new ArrayList<CompanyModel>();
						for (CompanyModel ccl : c) {
							cList.add(ccl);
						}
						ObservableList<CompanyModel> data = FXCollections.observableArrayList(cList);

						setColumnValues();
						tblCompany.setItems(data);

						tblCompany.setVisible(true);
					}
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
					// String response =
					// GetAPIClient.callGetAPI(Iconstants.URL_SERVER +
					// Iconstants.URL_COMPANY_API, null);
					if (response != null && response.length() > 0) {
						CompanyModel c[] = mapper.readValue(response, CompanyModel[].class);
						cList = new ArrayList<CompanyModel>();
						for (CompanyModel ccl : c) {
							cList.add(ccl);
						}
						ObservableList<CompanyModel> data = FXCollections.observableArrayList(cList);

						setColumnValues();
						tblCompany.setItems(data);

						tblCompany.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
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

	@FXML
	public void handleAddContMouseClick(MouseEvent arg0) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				 
				System.out.println("Clicked on Add Button.");
				CompanyEditController.selectedTabValue = 0 ;
				CompanyAddController.listOfBilling = new ArrayList<BillingControllerModel>();
				CompanyAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				CompanyAddController.company = new CompanyModel();
				openAddCompanyScreen();
				 

			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				 
				System.out.println("Clicked on EDIT Button.");
				CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
				CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
				CompanyEditController.company = new CompanyModel();
				
				CompanyEditController.selectedTabValue = 0 ;

				CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
				companyId = Long.parseLong(companyy.getCompanyId());

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
											bcm.setCompanyId(Long.parseLong(c.getCompanyId()));
											bcm.setBillingLocationId(c.getBillingLocations().get(i).getBillingLocationId());
											bcm.setAddress(c.getBillingLocations().get(i).getAddress());
											bcm.setCity(c.getBillingLocations().get(i).getCity());
											bcm.setCompany(c.getBillingLocations().get(i).getName());
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

											additionalContact.setCompanyId(Long.parseLong(c.getCompanyId()));
											additionalContact.setAdditionalContactId(
													c.getAdditionalContacts().get(j).getAdditionalContactId());
											additionalContact
													.setAdditionalContact(c.getAdditionalContacts().get(j).getCustomerName());
											additionalContact.setCellular(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
											additionalContact.setExtension(c.getAdditionalContacts().get(j).getExt());
											additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
											additionalContact.setPager(c.getAdditionalContacts().get(j).getCellular());
											additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
											additionalContact.setPosition(c.getAdditionalContacts().get(j).getPosition());
											additionalContact.setStatus(c.getAdditionalContacts().get(j).getStatus() + "");

											CompanyEditController.listOfAdditionalContact.add(additionalContact);
										}
									}

									// -----------------------------------------------------
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

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked on DELETE Button.");
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
		});
		
		MenuItem item4 = new MenuItem("DUPLICATE");
		item4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked on DUPLICATE Button.");
			}
		});
		
		MenuItem item5 = new MenuItem("PERSONALIZE");
		item5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked on PERSONALIZE Button.");
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
		
		MenuItem item6 = new MenuItem("FILTER BY");
		item6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked on FILTER BY Button.");
			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3, item4, item5 , item6);

		if (tblCompanyMenuCount == 0) {
			tblCompanyMenuCount++;
			// When user right-click on Table
			tblCompany.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tblCompany, event.getScreenX(), event.getScreenY());

				}

			});
		}
	}

}