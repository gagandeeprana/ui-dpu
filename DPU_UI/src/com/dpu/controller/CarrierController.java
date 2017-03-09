package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.CarrierModel;
import com.dpu.request.CompanyModel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
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

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		carrierName = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		address = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		unit = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		city = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		ps = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		postalCode = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		contact = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		contactPosition = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		phoneNumber = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		extension = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		faxNumber = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		tollFree = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		email = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		apCodeCDN = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);
		apCodeUS = (TableColumn<CarrierModel, String>) tblCarrier.getColumns().get(0);

	}

	private void setColumnValues() {

		carrierName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CarrierModel, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CarrierModel, String> param) {
						return new SimpleStringProperty("no value" + "");
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
						return new SimpleStringProperty("no value" + "");
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

}