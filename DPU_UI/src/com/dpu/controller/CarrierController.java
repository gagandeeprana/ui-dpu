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
import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.CarrierModel;
import com.dpu.request.CompanyModel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CarrierController extends Application implements Initializable {

	public List<CarrierModel> cList = new ArrayList<CarrierModel>();
	@FXML
	TableView<CarrierModel> tblCarrier;

	@FXML
	TableColumn<CarrierModel, String> carrierName, address, unit, city, ps, postalCode, contact, contactPosition,
			phoneNumber, extension, faxNumber, tollFree, email, apCodeCDN, apCodeUS;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		fetchCarriers();

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		carrierName = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		address = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(1);
		unit = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(2);
		city = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(3);
		ps = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(4);
		postalCode = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(5);
		contact = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(6);
		contactPosition = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(7);
		phoneNumber = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(8);
		extension = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(9);
		faxNumber = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(10);
		tollFree = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(11);
		email = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(12);
		apCodeCDN = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(13);
		apCodeUS = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(14);

	}

	private void setColumnValues() {

		carrierName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getName() + "");
					}
				});

		address.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getAddress() + "");
					}
				});

		unit.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getUnitNo() + "");
					}
				});

		city.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getCity() + "");
					}
				});

		ps.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getProvinceState() + "");
					}
				});

		postalCode.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty("no value" + "");
					}
				});

		contact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getContact() + "");
					}
				});

		contactPosition.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getPosition() + "");
					}
				});

		phoneNumber.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});

		extension.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});

		faxNumber.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});

		tollFree.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getTollfree() + "");
					}
				});

		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});

		apCodeCDN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty("no value" + "");
					}
				});

		apCodeUS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty("no value" + "");
					}
				});

	}

	public void fetchCarriers() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ObservableList<CarrierModel> data = null;
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CARRIER_API, null);
					if (response != null && response.length() > 0) {
						CarrierModel c[] = mapper.readValue(response, CarrierModel[].class);
						cList = new ArrayList<CarrierModel>();
						for (CarrierModel ccl : c) {
							cList.add(ccl);
						}
						data = FXCollections.observableArrayList(cList);
					} else {
						data = FXCollections.observableArrayList(cList);
					}
					setColumnValues();
					tblCarrier.setItems(data);

					tblCarrier.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@FXML
	private void btnDeleteCarrierAction() {
		CarrierModel carrierModel = cList.get(tblCarrier.getSelectionModel().getSelectedIndex());
		if (carrierModel != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_CARRIER_API + "/" + carrierModel.getCarrierId(),
								null);
						fetchCarriers();
						JOptionPane.showMessageDialog(null, "Carrier Deleted Successfully", "Info", 1);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	public static Long carrierId = 0l;

	@FXML
	private void btnEditCarrierAction() {
		CarrierEditController.listOfAdditionalContact = new ArrayList<AddtionalCarrierContact>();
		CarrierEditController.carrierModel = new CarrierModel();
		CarrierEditController.selectedTabValue = 1;
		CarrierModel carrierModel1 = cList.get(tblCarrier.getSelectionModel().getSelectedIndex());
		carrierId = carrierModel1.getCarrierId();
		CarrierModel carrierModel = cList.get(tblCarrier.getSelectionModel().getSelectedIndex());
		if (carrierModel != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_CARRIER_API + "/" + carrierModel.getCarrierId(),
								null);
						if (response != null && response.length() > 1) {
							CarrierModel carrierModel = mapper.readValue(response, CarrierModel.class);

							if (carrierModel.getAdditionalContacts() != null) {
								int addtionalContactSize = carrierModel.getAdditionalContacts().size();
								for (int j = 0; j < addtionalContactSize; j++) {
									AddtionalCarrierContact additionalContact = new AddtionalCarrierContact();

									additionalContact.setCarrierId(carrierModel.getCarrierId());
									additionalContact.setAdditionalContactId(
											carrierModel.getAdditionalContacts().get(j).getAdditionalContactId());
									additionalContact.setBrokerContact(
											carrierModel.getAdditionalContacts().get(j).getBrokerContact());
									additionalContact
											.setBrokerFax(carrierModel.getAdditionalContacts().get(j).getBrokerFax());
									additionalContact.setBrokerPhone(
											carrierModel.getAdditionalContacts().get(j).getBrokerPhone());
									additionalContact.setCongCoverage(
											carrierModel.getAdditionalContacts().get(j).getCongCoverage());
									additionalContact.setEmail(carrierModel.getAdditionalContacts().get(j).getEmail());
									additionalContact.setExt(carrierModel.getAdditionalContacts().get(j).getExt());
									additionalContact
											.setIncBroker(carrierModel.getAdditionalContacts().get(j).getIncBroker());
									additionalContact
											.setIncCompany(carrierModel.getAdditionalContacts().get(j).getIncCompany());
									additionalContact.setLibilityCoverage(
											carrierModel.getAdditionalContacts().get(j).getLibilityCoverage());
									additionalContact.setPolicyNumber(
											carrierModel.getAdditionalContacts().get(j).getPolicyNumber());

									CarrierEditController.listOfAdditionalContact.add(additionalContact);
								}
							}

							CarrierEditController carrierEditController = (CarrierEditController) openEditCarrierScreen();
							carrierEditController.initData(carrierModel);
						}

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private Object openEditCarrierScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_Edit_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit Carrier");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@FXML
	private void btnGoCarrierAction() {

	}

	@FXML
	private void btnAddCarrierAction() {
		CarrierAddController.listOfAdditionalContact = new ArrayList<AddtionalCarrierContact>();
		CarrierAddController.carrierModel = new CarrierModel();
		openAddCarrierScreen();
	}

	private void openAddCarrierScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Carrier");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
