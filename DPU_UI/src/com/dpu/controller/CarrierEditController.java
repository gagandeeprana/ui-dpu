/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.CarrierModel;

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

/**
 * @author jagvir
 *
 */
public class CarrierEditController extends Application implements Initializable {

	@FXML
	private Pane addCarrierPane;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> additionalContact;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> position;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> phoneNo;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> faxNo;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> cellular;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> email;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> extension;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> pager;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> status;

	@FXML
	private TableView<AddtionalCarrierContact> tableAdditionalContact;

	@FXML
	private Button btnSaveCarrier;

	@FXML
	private Button btnCancelCarrier;

	@FXML
	public TextField txtCarrier;

	@FXML
	public TextField txtAddress;

	@FXML
	public TextField txtUnit;

	@FXML
	public TextField txtCity;

	@FXML
	public TextField txtPS;

	@FXML
	public TextField txtPCZe;

	@FXML
	public TextField txtWebsite;

	@FXML
	public TextField txtContact;

	@FXML
	public TextField txtPosition;

	@FXML
	public TextField txtPhone;

	@FXML
	public TextField txtExt;

	@FXML
	public TextField txtFax;

	@FXML
	public TextField txtPrefix;

	@FXML
	public TextField txtTollFree;

	@FXML
	public TextField txtCell;

	@FXML
	public TextField txtEmail;

	@FXML
	private TabPane tabPane;

	Long carrierId = 0l;
	public static int add = 0;
	public static int addEditIndex = -1;
	public static int selectedTabValue = 0;
	public static int addAddtionalContact = 0;
	public static AddtionalCarrierContact additionalContactModel = new AddtionalCarrierContact();
	public static ArrayList<AddtionalCarrierContact> listOfAdditionalContact = new ArrayList<AddtionalCarrierContact>();
	public static CarrierModel carrierModel = new CarrierModel();

	public void initData(CarrierModel c) {
		CarrierEditController.carrierModel = c;
		carrierId = c.getCarrierId();
		txtAddress.setText(c.getAddress());
		txtCarrier.setText(c.getName());
		txtCell.setText(c.getCellular());
		txtCity.setText(c.getCity());
		txtContact.setText(c.getContact());
		txtEmail.setText(c.getEmail());
		txtExt.setText(c.getExt());
		txtFax.setText(c.getFax());
		txtPCZe.setText("null");
		txtPhone.setText(c.getPhone());
		txtPosition.setText(c.getPosition());
		txtPrefix.setText(c.getPrefix());
		txtPS.setText(c.getProvinceState());
		txtTollFree.setText(c.getTollfree());
		txtUnit.setText(c.getUnitNo());
		txtWebsite.setText(c.getWebsite());

	}

	private void setAdditionalContactColumnValues() {

		additionalContact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCustomerName() + "");
					}
				});
		position.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPosition() + "");
					}
				});

		phoneNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPhone() + "");
					}
				});

		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFax() + "");
					}
				});

		cellular.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCellular() + "");
					}
				});

		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		extension.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});
		pager.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPrefix() + "");
					}
				});
		status.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getStatusId() + "");
					}
				});

	}

	public void fetchAdditionalCarrierContacts() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (CarrierEditController.listOfAdditionalContact != null
							& !(CarrierEditController.listOfAdditionalContact.isEmpty())) {

						ObservableList<AddtionalCarrierContact> data = FXCollections
								.observableArrayList(CarrierEditController.listOfAdditionalContact);
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

	private void openEditAdditionalContactScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
					Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_EDIT_CARRIER_ADDITIONAL_CONTACT_SCREEN));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit Additional Carrier Contact");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addAddtionalContact = 1;
				carrierModel.setAddress(txtAddress.getText());
				carrierModel.setCellular(txtCell.getText());
				carrierModel.setCity(txtCity.getText());
				carrierModel.setContact(txtContact.getText());
				carrierModel.setEmail(txtEmail.getText());
				carrierModel.setExt(txtExt.getText());
				carrierModel.setFax(txtFax.getText());
				carrierModel.setPhone(txtPhone.getText());
				carrierModel.setPosition(txtPosition.getText());
				carrierModel.setPrefix(txtPrefix.getText());
				carrierModel.setProvinceState(txtPS.getText());
				carrierModel.setTollfree(txtTollFree.getText());
				carrierModel.setUnitNo(txtUnit.getText());
				carrierModel.setWebsite(txtWebsite.getText());
				carrierModel.setName(txtCarrier.getText());

				openEditAdditionalContactScreen();

				try {
					closeEditCarrierScreen(btnSaveCarrier);
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
				add = 0;
				selectedTabValue = 1;
				addAddtionalContact = 0;
				addEditIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				additionalContactModel = tableAdditionalContact.getSelectionModel().getSelectedItem();
				openEditAdditionalContactScreen();
				closeEditCarrierScreen(btnSaveCarrier);

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 1;
				addEditIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				CarrierEditController.listOfAdditionalContact.remove(addEditIndex);
				addEditIndex = -1;

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
							.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_CARRIER_Edit_SCREEN));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setTitle("Add New Carrier");
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				closeEditCarrierScreen(btnSaveCarrier);

			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);

		// When user right-click on Table
		tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				contextMenu.show(tableAdditionalContact, event.getScreenX(), event.getScreenY());

			}

		});

	}

	private CarrierModel setCarrierValue() {
		List<com.dpu.request.AdditionalContact> additionalContacts = new ArrayList<com.dpu.request.AdditionalContact>();

		// List<AddtionalCarrierContact> addtionalCarrierContacts = new
		// ArrayList<AddtionalCarrierContact>();
		carrierModel.setAddress(txtAddress.getText());
		carrierModel.setCellular("cellular");
		carrierModel.setCity(txtCity.getText());
		carrierModel.setContact(txtContact.getText());
		carrierModel.setEmail(txtEmail.getText());
		carrierModel.setExt(txtExt.getText());
		carrierModel.setFax(txtFax.getText());
		carrierModel.setPhone(txtPhone.getText());
		carrierModel.setPosition(txtPosition.getText());
		carrierModel.setPrefix(txtPrefix.getText());
		carrierModel.setProvinceState(txtPS.getText());
		carrierModel.setTollfree(txtTollFree.getText());
		carrierModel.setUnitNo(txtUnit.getText());
		carrierModel.setWebsite(txtWebsite.getText());
		carrierModel.setName(txtCarrier.getText());
		// carrierModel.setZip("null");

		if (CarrierEditController.listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = CarrierEditController.listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				AddtionalCarrierContact additionalContactModel = CarrierEditController.listOfAdditionalContact.get(i);
				com.dpu.request.AdditionalContact additionalContact = new com.dpu.request.AdditionalContact();
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

		carrierModel.setCarrierAdditionalContactModel(additionalContacts);
		return carrierModel;
	}

	private void closeEditCarrierScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	ObjectMapper mapper = new ObjectMapper();

	private void fetchAdditionalContacts() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					String response = GetAPIClient.callGetAPI(
							Iconstants.URL_SERVER + Iconstants.URL_CARRIER_CONTACTS + "/" + carrierModel.getCarrierId(),
							null);
					List addtionalCarrierContact = mapper.readValue(response, List.class);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);

				}
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("::::initialize:::::");
		// tabPane.getSelectionModel().select(1);
		fetchAdditionalCarrierContacts();
		txtAddress.setText(carrierModel.getAddress());
		txtCarrier.setText(carrierModel.getName());
		txtCell.setText(carrierModel.getCellular());
		txtCity.setText(carrierModel.getCity());
		txtContact.setText(carrierModel.getContact());
		txtEmail.setText(carrierModel.getEmail());
		txtExt.setText(carrierModel.getExt());
		txtFax.setText(carrierModel.getFax());
		txtPCZe.setText("null");
		txtPhone.setText(carrierModel.getPhone());
		txtPosition.setText(carrierModel.getPosition());
		txtPrefix.setText(carrierModel.getPrefix());
		txtPS.setText(carrierModel.getProvinceState());
		txtTollFree.setText(carrierModel.getTollfree());
		txtUnit.setText(carrierModel.getUnitNo());
		txtWebsite.setText(carrierModel.getWebsite());

	}

	private void editCarrier() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					ObjectMapper mapper = new ObjectMapper();
					CarrierModel carrierModel = setCarrierValue();
					String payload = mapper.writeValueAsString(carrierModel);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_CARRIER_API + "/" + carrierId, null, payload);

					if (response != null) {
						JOptionPane.showMessageDialog(null, "Carrier Updated Successfully.", "Info", 1);
					} else {
						JOptionPane.showMessageDialog(null, "Failed to Updated Carrier.", "Info", 1);
					}

					closeEditCarrierScreen(btnSaveCarrier);
					MainScreen.carrierController.fetchCarriers();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	private void btnSaveCarrierAction() {
		carrierId = CarrierController.carrierId;
		editCarrier();
		closeEditCarrierScreen(btnSaveCarrier);
	}

	@FXML
	private void btnCancelCarrierAction() {
		closeEditCarrierScreen(btnCancelCarrier);
	}
}
