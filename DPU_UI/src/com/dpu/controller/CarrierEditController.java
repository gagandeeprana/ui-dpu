/**
 * 
 */
package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.dpu.model.AddtionalCarrierContact;
import com.dpu.model.CarrierModel;
import com.dpu.request.CompanyModel;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

	private void closeEditCarrierScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
		loginStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tabPane.getSelectionModel().select(1);
		// TODO Auto-generated method stub

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
