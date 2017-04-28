package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

//import org.apache.commons.validator.EmailValidator;
import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.model.Category;
import com.dpu.model.Company;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Sale;
import com.dpu.model.Success;
import com.dpu.model.Type;
import com.dpu.request.BillingLocation;
import com.dpu.request.CompanyModel;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

	@FXML
	Label lblCompany, lblAddress, lblUnitNo, lblCity, lblEmail, lblWebsite, lblProvince, lblZip, lblAfterHours,
			lblCellular, lblTollFree, lblFax, lblPhone, lblPosition, lblContact, lblPager, lblExt, lblPrefix;

	Validate validate = new Validate();

	@FXML
	private void companyNameKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
		}
	}
	
	@FXML
	private void companyDdlCategoryKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
		}
	}
	
	@FXML
	private void companyDdlDivisionKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
		}
	}
	
	@FXML
	private void companyDdlSaleKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
		}
	}
	
	@FXML
	private void companyDdlCountryKeyPressed() {

		String name = txtCompany.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblCompany.setTextFill(Color.BLACK);
			txtCompany.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtCompany.setStyle("-fx-focus-color: red;");
			txtCompany.requestFocus();
			lblCompany.setVisible(true);
		}
	}

	/*
	 * @FXML private void companyAddressKeyPressed() {
	 * 
	 * String name = txtAddress.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblAddress.setTextFill(Color.BLACK);
	 * txtAddress.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtAddress.setStyle("-fx-focus-color: red;"); txtAddress.requestFocus();
	 * lblAddress.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblAddress.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyUnitNoKeyPressed() {
	 * 
	 * String name = txtUnitNo.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblUnitNo.setTextFill(Color.BLACK);
	 * txtUnitNo.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtUnitNo.setStyle("-fx-focus-color: red;"); txtUnitNo.requestFocus();
	 * lblUnitNo.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblUnitNo.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyCityKeyPressed() {
	 * 
	 * String name = txtCity.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblCity.setTextFill(Color.BLACK);
	 * txtCity.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtCity.setStyle("-fx-focus-color: red;"); txtCity.requestFocus();
	 * lblCity.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblCity.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyEmailKeyPressed() {
	 * 
	 * String name = txtEmail.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblEmail.setTextFill(Color.BLACK);
	 * txtEmail.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtEmail.setStyle("-fx-focus-color: red;"); txtEmail.requestFocus();
	 * lblEmail.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblEmail.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyWebsiteKeyPressed() {
	 * 
	 * String name = txtWebsite.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblWebsite.setTextFill(Color.BLACK);
	 * txtWebsite.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtWebsite.setStyle("-fx-focus-color: red;"); txtWebsite.requestFocus();
	 * lblWebsite.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblWebsite.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyProvinceKeyPressed() {
	 * 
	 * String name = txtProvince.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblProvince.setTextFill(Color.BLACK);
	 * txtProvince.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtProvince.setStyle("-fx-focus-color: red;");
	 * txtProvince.requestFocus(); lblProvince.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblProvince.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyZipKeyPressed() {
	 * 
	 * String name = txtZip.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblZip.setTextFill(Color.BLACK);
	 * txtZip.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtZip.setStyle("-fx-focus-color: red;"); txtZip.requestFocus();
	 * lblZip.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblZip.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyAfterHoursKeyPressed() {
	 * 
	 * String name = txtAfterHours.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblAfterHours.setTextFill(Color.BLACK);
	 * txtAfterHours.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtAfterHours.setStyle("-fx-focus-color: red;");
	 * txtAfterHours.requestFocus(); lblAfterHours.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblAfterHours.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyCellularKeyPressed() {
	 * 
	 * String name = txtCellular.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblCellular.setTextFill(Color.BLACK);
	 * txtCellular.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtCellular.setStyle("-fx-focus-color: red;");
	 * txtCellular.requestFocus(); lblCellular.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblCellular.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyTollFreeKeyPressed() {
	 * 
	 * String name = txtTollFree.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblTollFree.setTextFill(Color.BLACK);
	 * txtTollFree.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtTollFree.setStyle("-fx-focus-color: red;");
	 * txtTollFree.requestFocus(); lblTollFree.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblTollFree.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyFaxKeyPressed() {
	 * 
	 * String name = txtFax.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblFax.setTextFill(Color.BLACK);
	 * txtFax.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtFax.setStyle("-fx-focus-color: red;"); txtFax.requestFocus();
	 * lblFax.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblFax.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyPhoneKeyPressed() {
	 * 
	 * String name = txtPhone.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblPhone.setTextFill(Color.BLACK);
	 * txtPhone.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtPhone.setStyle("-fx-focus-color: red;"); txtPhone.requestFocus();
	 * lblPhone.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblPhone.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyPositionKeyPressed() {
	 * 
	 * String name = txtPosition.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblPosition.setTextFill(Color.BLACK);
	 * txtPosition.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtPosition.setStyle("-fx-focus-color: red;");
	 * txtPosition.requestFocus(); lblPosition.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblPosition.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyContactKeyPressed() {
	 * 
	 * String name = txtContact.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblContact.setTextFill(Color.BLACK);
	 * txtContact.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtContact.setStyle("-fx-focus-color: red;"); txtContact.requestFocus();
	 * lblContact.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblContact.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyPagerKeyPressed() {
	 * 
	 * String name = txtPager.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblPager.setTextFill(Color.BLACK);
	 * txtPager.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtPager.setStyle("-fx-focus-color: red;"); txtPager.requestFocus();
	 * lblPager.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblPager.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyExtKeyPressed() {
	 * 
	 * String name = txtExt.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblExt.setTextFill(Color.BLACK);
	 * txtExt.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtExt.setStyle("-fx-focus-color: red;"); txtExt.requestFocus();
	 * lblExt.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblExt.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void companyPrefixKeyPressed() {
	 * 
	 * String name = txtPrefix.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblPrefix.setTextFill(Color.BLACK);
	 * txtPrefix.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtPrefix.setStyle("-fx-focus-color: red;"); txtPrefix.requestFocus();
	 * lblPrefix.setVisible(true); //
	 * lblCompany.setText("Company Name is Mandatory");
	 * lblPrefix.setTextFill(Color.RED); } }
	 */
	private boolean validateAddCompanyScreen() {
		boolean result = true;
		String customerName = txtCompany.getText();
		// String address = txtAddress.getText();
		String email = txtEmail.getText();
		// String phone = txtPhone.getText();
		// String fax = txtFax.getText();
		// String unitNo = txtUnitNo.getText();
		// String city = txtCity.getText();
		// String province = txtProvince.getText();
		// String zip = txtZip.getText();
		// String website = txtWebsite.getText();
		// String contact = txtContact.getText();
		// String position = txtPosition.getText();
		// String afterHours = txtAfterHours.getText();
		// String tollFree = txtTollFree.getText();
		// String cellular = txtCellular.getText();
		// String pager = txtPager.getText();
		// String ext = txtExt.getText();
		// String prefix = txtPrefix.getText();

		boolean blnName = validate.validateEmptyness(customerName);
		if (!blnName) {
			result = false;
			txtCompany.setStyle("-fx-text-box-border: red;");
			lblCompany.setVisible(true);
			// lblCompany.setText("Company Name is Mandatory");
			// lblCompany.setTextFill(Color.RED);

		}

		/*EmailValidator validator = EmailValidator.getInstance();
		if (validator.isValid(email)) {
			result = true;
		} else {
			result = false;
			txtEmail.setStyle("-fx-text-box-border: red;");
			lblEmail.setVisible(true);
		}*/

		/*
		 * boolean blnAddress = validate.validateEmptyness(address); if
		 * (!blnAddress) { result = false;
		 * txtAddress.setStyle("-fx-text-box-border: red;");
		 * lblAddress.setVisible(true); //
		 * lblAddress.setText("Address is Mandatory");
		 * lblAddress.setTextFill(Color.RED); }
		 * 
		 * boolean blnEmail = validate.validateEmptyness(email); if (!blnEmail)
		 * { result = false; txtEmail.setStyle("-fx-text-box-border: red;");
		 * lblEmail.setVisible(true); // lblEmail.setText("Email is Mandatory");
		 * lblEmail.setTextFill(Color.RED); }
		 * 
		 * boolean blnPhone = validate.validateEmptyness(phone); if (!blnPhone)
		 * { result = false; txtPhone.setStyle("-fx-text-box-border: red;");
		 * lblPhone.setVisible(true); //
		 * lblPhone.setText("Phone Number is Mandatory");
		 * lblPhone.setTextFill(Color.RED); }
		 * 
		 * boolean blnFax = validate.validateEmptyness(fax); if (!blnFax) {
		 * result = false; txtFax.setStyle("-fx-text-box-border: red;");
		 * lblFax.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblFax.setTextFill(Color.RED); }
		 * 
		 * boolean blnUnitNo = validate.validateEmptyness(unitNo); if
		 * (!blnUnitNo) { result = false;
		 * txtUnitNo.setStyle("-fx-text-box-border: red;");
		 * lblUnitNo.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblUnitNo.setTextFill(Color.RED); }
		 * 
		 * boolean blnCity = validate.validateEmptyness(city); if (!blnCity) {
		 * result = false; txtCity.setStyle("-fx-text-box-border: red;");
		 * lblCity.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblCity.setTextFill(Color.RED); }
		 * 
		 * boolean blnProvince = validate.validateEmptyness(province); if
		 * (!blnProvince) { result = false;
		 * txtProvince.setStyle("-fx-text-box-border: red;");
		 * lblProvince.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblProvince.setTextFill(Color.RED); }
		 * 
		 * boolean blnZip = validate.validateEmptyness(zip); if (!blnZip) {
		 * result = false; txtZip.setStyle("-fx-text-box-border: red;");
		 * lblZip.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblZip.setTextFill(Color.RED); }
		 * 
		 * boolean blnWebsite = validate.validateEmptyness(website); if
		 * (!blnWebsite) { result = false;
		 * txtWebsite.setStyle("-fx-text-box-border: red;");
		 * lblWebsite.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblWebsite.setTextFill(Color.RED); }
		 * 
		 * boolean blnContact = validate.validateEmptyness(contact); if
		 * (!blnContact) { result = false;
		 * txtContact.setStyle("-fx-text-box-border: red;");
		 * lblContact.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblContact.setTextFill(Color.RED); }
		 * 
		 * boolean blnPosition = validate.validateEmptyness(position); if
		 * (!blnPosition) { result = false;
		 * txtPosition.setStyle("-fx-text-box-border: red;");
		 * lblPosition.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblPosition.setTextFill(Color.RED); }
		 * 
		 * boolean blnAfterHours = validate.validateEmptyness(afterHours); if
		 * (!blnAfterHours) { result = false;
		 * txtAfterHours.setStyle("-fx-text-box-border: red;");
		 * lblAfterHours.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblAfterHours.setTextFill(Color.RED); }
		 * 
		 * boolean blnTollFree = validate.validateEmptyness(tollFree); if
		 * (!blnTollFree) { result = false;
		 * txtTollFree.setStyle("-fx-text-box-border: red;");
		 * lblTollFree.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblTollFree.setTextFill(Color.RED); }
		 * 
		 * boolean blnCellular = validate.validateEmptyness(cellular); if
		 * (!blnCellular) { result = false;
		 * txtCellular.setStyle("-fx-text-box-border: red;");
		 * lblCellular.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblCellular.setTextFill(Color.RED); }
		 * 
		 * boolean blnPager = validate.validateEmptyness(pager); if (!blnPager)
		 * { result = false; txtPager.setStyle("-fx-text-box-border: red;");
		 * lblPager.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblPager.setTextFill(Color.RED); }
		 * 
		 * boolean blnExt = validate.validateEmptyness(ext); if (!blnExt) {
		 * result = false; txtExt.setStyle("-fx-text-box-border: red;");
		 * lblExt.setVisible(true); //
		 * lblFax.setText("Fax Number is Mandatory");
		 * lblExt.setTextFill(Color.RED); }
		 * 
		 * boolean blnPrefix = validate.validateEmptyness(prefix); if
		 * (!blnPrefix) { result = false;
		 * txtPrefix.setStyle("-fx-text-box-border: red;");
		 * lblPrefix.setVisible(true); //
		 * lblFax.setText("Unit Number is Mandatory");
		 * lblPrefix.setTextFill(Color.RED); }
		 */

		return result;

	}

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
	private TableColumn<AdditionalContact, String> status,function,country;

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
	public TableView<BillingControllerModel> tableBillingLocations;

	public static TableView<BillingControllerModel> duplicateTableBillingLocations;

	public static TableView<AdditionalContact> duplicateTableAdditionalContact;

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
	private TabPane tabPane;

	@FXML
	public TableColumn<BillingControllerModel, String> zip;

	public static int addEditIndex = -1;
	// public static int editIndex = -1;
	public static int add = 0;
	public static int addAddtionalContact = 0;
	public static BillingControllerModel billingControllerModel = new BillingControllerModel();
	public static AdditionalContact additionalContactModel = new AdditionalContact();
	public static ArrayList<BillingControllerModel> listOfBilling = new ArrayList<BillingControllerModel>();
	public static ArrayList<AdditionalContact> listOfAdditionalContact = new ArrayList<AdditionalContact>();
	public static CompanyModel company = new CompanyModel();

	int additionalContactCountMenu = 0;

	@FXML
	void handleAddContMouseClick(MouseEvent event) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				add = 1;
				selectedTabValue = 1;
				addAddtionalContact = 1;
				openAddAdditionalContactScreen();

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
				openAddAdditionalContactScreen();

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 1;
				addEditIndex = tableAdditionalContact.getSelectionModel().getSelectedIndex();
				CompanyEditController.listOfAdditionalContact.remove(addEditIndex);
				addEditIndex = -1;
				fetchAdditionalContactsUsingDuplicate();

			}
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);

		if (additionalContactCountMenu == 0) {
			additionalContactCountMenu++;
			// When user right-click on Table
			tableAdditionalContact.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
				@Override
				public void handle(ContextMenuEvent event) {
					contextMenu.show(tableAdditionalContact, event.getScreenX(), event.getScreenY());

				}

			});

		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
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

	// new added
	public static void fetchBillingLocationsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (CompanyEditController.listOfBilling != null
							& !(CompanyEditController.listOfBilling.isEmpty())) {
						ObservableList<BillingControllerModel> data = FXCollections
								.observableArrayList(CompanyEditController.listOfBilling);
						duplicateTableBillingLocations.setItems(data);
						duplicateTableBillingLocations.setVisible(true);
					} else {
						CompanyEditController.listOfBilling = new ArrayList<>();
						ObservableList<BillingControllerModel> data = FXCollections
								.observableArrayList(CompanyEditController.listOfBilling);
						duplicateTableBillingLocations.setItems(data);
						duplicateTableBillingLocations.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again..  " + e, "Info", 1);
				}
			}

		});
	}

	public void fetchBillingLocations() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (CompanyEditController.listOfBilling != null
							& !(CompanyEditController.listOfBilling.isEmpty())) {
						ObservableList<BillingControllerModel> data = FXCollections
								.observableArrayList(CompanyEditController.listOfBilling);
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

					if (CompanyEditController.listOfAdditionalContact != null
							& !(CompanyEditController.listOfAdditionalContact.isEmpty())) {
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(CompanyEditController.listOfAdditionalContact);
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

	public static void fetchAdditionalContactsUsingDuplicate() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {

					if (CompanyEditController.listOfAdditionalContact != null
							& !(CompanyEditController.listOfAdditionalContact.isEmpty())) {
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(CompanyEditController.listOfAdditionalContact);
						duplicateTableAdditionalContact.setItems(data);
						duplicateTableAdditionalContact.setVisible(true);
					} else {
						CompanyEditController.listOfAdditionalContact = new ArrayList<>();
						ObservableList<AdditionalContact> data = FXCollections
								.observableArrayList(CompanyEditController.listOfAdditionalContact);
						duplicateTableAdditionalContact.setItems(data);
						duplicateTableAdditionalContact.setVisible(true);
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
		
		function.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AdditionalContact, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AdditionalContact, String> param) {
						return new SimpleStringProperty(param.getValue().getFunction() + "");
					}
				});
		
	}

	public static int selectedTabValue = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		duplicateTableBillingLocations = tableBillingLocations;
		duplicateTableAdditionalContact = tableAdditionalContact;
		fetchBillingLocations();
		fetchAdditionalContacts();
		setColumnValues();
		setAdditionalContactColumnValues();

		// new for check
		tabPane.getSelectionModel().select(selectedTabValue);
		fetchMasterDataForDropDowns();
	//	txtPhone.addEventFilter(KeyEvent.KEY_TYPED, Validate.numeric_Validation(10));
	//	txtFax.addEventFilter(KeyEvent.KEY_TYPED, Validate.numeric_Validation(10));
	//	txtCompany.addEventFilter(KeyEvent.KEY_TYPED, Validate.letter_Validation(10));

	}

	int billingLocationCountMenu = 0;

	@FXML
	public void handleMouseClick(MouseEvent arg0) {

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("ADD");
		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				add = 1;
				selectedTabValue = 0;
				openAddBillingLocationScreen();
			}
		});
		MenuItem item2 = new MenuItem("EDIT");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;
				add = 0;
				addEditIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				billingControllerModel = tableBillingLocations.getSelectionModel().getSelectedItem();
				openAddBillingLocationScreen();

			}
		});

		MenuItem item3 = new MenuItem("DELETE");
		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedTabValue = 0;
				addEditIndex = tableBillingLocations.getSelectionModel().getSelectedIndex();
				CompanyEditController.listOfBilling.remove(addEditIndex);
				addEditIndex = -1;
				fetchBillingLocationsUsingDuplicate();
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

	}

	private void openAddBillingLocationScreen() {

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
		}
	}

	private void openAddAdditionalContactScreen() {
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
		}
	}

	@FXML
	private void btnSaveCompanyAction() {

		boolean result = validateAddCompanyScreen();
		if (result) {
			addCompany();
			closeAddCompanyScreen(btnSaveCompany);
		}

	}

	@FXML
	private void btnCancelCompanyAction() {
		CompanyEditController.listOfBilling = new ArrayList<BillingControllerModel>();
		CompanyEditController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
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

	public void addCompany(String reponse) {

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
		company.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		company.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		company.setSaleId(saleList.get(ddlSale.getSelectionModel().getSelectedIndex()).getSaleId());
		company.setCountryId(countryList.get(ddlCountry.getSelectionModel().getSelectedIndex()).getTypeId());
		// need to use for loop here

		if (CompanyEditController.listOfBilling != null) {
			int sizeOfBilling = CompanyEditController.listOfBilling.size();
			for (int i = 0; i < sizeOfBilling; i++) {
				BillingLocation billingLocation = new BillingLocation();
				BillingControllerModel billingModel = CompanyEditController.listOfBilling.get(i);
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

		company.setBillingLocations(billingLocations);

		// need to use for loop here
		if (CompanyEditController.listOfAdditionalContact != null) {
			int sizeOfAdditionalContact = CompanyEditController.listOfAdditionalContact.size();
			for (int i = 0; i < sizeOfAdditionalContact; i++) {

				AdditionalContact additionalContactModel = CompanyEditController.listOfAdditionalContact.get(i);
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
		company.setAdditionalContacts(additionalContacts);
		return company;

	}

	public void initData(CompanyModel c) {

		txtCompany.setText(c.getName());
		txtAddress.setText(c.getAddress());
		txtUnitNo.setText(c.getUnitNo());
		txtCity.setText(c.getCity());
		txtProvince.setText(c.getProvinceState());
		txtZip.setText(c.getZip());
		txtEmail.setText(c.getEmail());
		txtWebsite.setText(c.getWebsite());
		
	}
	
	//=============================================
	List<Category> categoryList = null;
	List<Division> divisionList = null;
	List<Sale> saleList = null;
	List<Type> countryList = null;
	@FXML
	ComboBox<String> ddlCategory, ddlDivision, ddlSale, ddlCountry;

	private void fetchMasterDataForDropDowns() {

		Platform.runLater(new Runnable() {
			ObjectMapper mapper = new ObjectMapper();
			

			@Override
			public void run() {
				try {
					String response = GetAPIClient
							.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/openAdd", null);
					Company company = mapper.readValue(response, Company.class);
				
					categoryList = company.getCategoryList();
					if(categoryList != null)
					fillDropDown(ddlCategory, categoryList);
					
					divisionList = company.getDivisionList();
					if(divisionList != null)
					fillDropDown(ddlDivision, divisionList);

					saleList = company.getSaleList();
					if(saleList != null)
					fillDropDown(ddlSale, saleList);
					
					countryList = company.getCountryList();
					if(countryList != null)
					fillDropDown(ddlCountry, countryList);
					 
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void fillDropDown(ComboBox<String> comboBox, List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && object instanceof Type) {
				Type type = (Type) object;
				comboBox.getItems().add(type.getTypeName());
			}
			 
			if (object != null && object instanceof Category) {
				Category category = (Category) object;
				comboBox.getItems().add(category.getName());
			}
			if (object != null && object instanceof Division) {
				Division division = (Division) object;
				comboBox.getItems().add(division.getDivisionName());
			}
			if (object != null && object instanceof Sale) {
				Sale sale = (Sale) object;
				comboBox.getItems().add(sale.getName());
			}
		}
	}


}