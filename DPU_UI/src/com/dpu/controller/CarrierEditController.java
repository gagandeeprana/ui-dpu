/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
	private TableColumn<AddtionalCarrierContact, String> incCompany;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> policyNumber;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> incBroker;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> brokerContact;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> brokerPhone;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> ext;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> congCoverage;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> email;

	@FXML
	private TableColumn<AddtionalCarrierContact, String> brokerFax;

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

		incCompany.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getIncCompany() + "");
					}
				});
		policyNumber.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getPolicyNumber() + "");
					}
				});
		incBroker.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getIncBroker() + "");
					}
				});
		brokerContact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getBrokerContact() + "");
					}
				});
		brokerPhone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getBrokerPhone() + "");
					}
				});
		ext.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getExt() + "");
					}
				});
		congCoverage.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getCongCoverage() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getEmail() + "");
					}
				});
		brokerFax.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AddtionalCarrierContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AddtionalCarrierContact, String> param) {
						return new SimpleStringProperty(param.getValue().getBrokerFax() + "");
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
		carrierModel.setAdditionalContacts(listOfAdditionalContact);
		return carrierModel;
	}

	private void closeEditCarrierScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tabPane.getSelectionModel().select(1);
		fetchAdditionalCarrierContacts();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	private void btnCancelCarrierAction() {
		closeEditCarrierScreen(btnCancelCarrier);
	}
}
