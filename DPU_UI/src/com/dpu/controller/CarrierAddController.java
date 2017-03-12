/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.CarrierModel;

import javafx.application.Application;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author jagvir
 *
 */
public class CarrierAddController extends Application implements Initializable {

	@FXML
	private Pane addCarrierPane;

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
	private TableView<AdditionalContact> tableAdditionalContact;

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
	public TextField txtPSZE;

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

	public static int addEditIndex = -1;
	public static int add = 0;
	public static int addAddtionalContact = 0;
	public static int selectedTabValue = 0;
	public static AdditionalContact additionalContactModel = new AdditionalContact();
	public static ArrayList<AddtionalCarrierContact> listOfAdditionalContact = new ArrayList<AddtionalCarrierContact>();
	public static CarrierModel carrierModel = new CarrierModel();

	int additionalContactCountMenu = 0;

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
		carrierModel.setProvinceState("province");
		carrierModel.setTollfree(txtTollFree.getText());
		carrierModel.setUnitNo(txtUnit.getText());
		carrierModel.setWebsite(txtWebsite.getText());
		carrierModel.setZip("zp");
		carrierModel.setAdditionalContacts(listOfAdditionalContact);
		return carrierModel;
	}

	@FXML
	void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				openAddAdditionalContactScreen();

				try {
					closeAddCarrierScreen(btnSaveCarrier);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}

			}
		});
		// MenuItem item2 = new MenuItem("EDIT");
		// item2.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// add = 0;
		// selectedTabValue = 1;
		// addAddtionalContact = 0;
		// addEditIndex =
		// tableAdditionalContact.getSelectionModel().getSelectedIndex();
		// additionalContactModel =
		// tableAdditionalContact.getSelectionModel().getSelectedItem();
		// openAddAdditionalContactScreen();
		// closeAddCarrierScreen(btnSaveCarrier);
		//
		// }
		// });
		//
		// MenuItem item3 = new MenuItem("DELETE");
		// item3.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// selectedTabValue = 1;
		// addEditIndex =
		// tableAdditionalContact.getSelectionModel().getSelectedIndex();
		// CompanyEditController.listOfAdditionalContact.remove(addEditIndex);
		// addEditIndex = -1;
		//
		// try {
		// FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
		// .getResource(Iconstants.COMPANY_BASE_PACKAGE +
		// Iconstants.XML_COMPANY_ADD_SCREEN));
		// Parent root = (Parent) fxmlLoader.load();
		// Stage stage = new Stage();
		// stage.initModality(Modality.APPLICATION_MODAL);
		// stage.setTitle("Add New Company");
		// stage.setScene(new Scene(root));
		// stage.show();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// closeAddCarrierScreen(btnSaveCarrier);
		//
		// }
		// });
		//
		// // Add MenuItem to ContextMenu
		// contextMenu.getItems().addAll(item1, item2, item3);
		//
		// if (additionalContactCountMenu == 0) {
		// additionalContactCountMenu++;
		// // When user right-click on Table
		// tableAdditionalContact.setOnContextMenuRequested(new
		// EventHandler<ContextMenuEvent>() {
		// @Override
		// public void handle(ContextMenuEvent event) {
		// contextMenu.show(tableAdditionalContact, event.getScreenX(),
		// event.getScreenY());
		//
		// }
		//
		// });
		//
		// }

	}

	@FXML
	void initialize() {
		assert addCarrierPane != null : "fx:id=\"addCarierPane\" was not injected: check your FXML file 'AddCarrier.fxml'.";
		assert txtCarrier != null : "fx:id=\"txtCarier\" was not injected: check your FXML file 'AddCarrier.fxml'.";
		assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtUnit != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtCity != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPS != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtWebsite != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtContact != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPosition != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPhone != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtExt != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtFax != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtPrefix != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtTollFree != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtCell != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
		assert txtEmail != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'AddCompany.fxml'.";
	}

	private void openAddAdditionalContactScreen() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.CARRIER_BASE_PACKAGE + Iconstants.XML_ADD_ADDITIONAL_CONTACT_SCREEN));
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

	private void closeAddCarrierScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
