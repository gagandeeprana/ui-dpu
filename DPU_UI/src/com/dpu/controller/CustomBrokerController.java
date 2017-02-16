package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.CustomBroker;
import com.dpu.model.Truck;

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

public class CustomBrokerController extends Application implements Initializable {

	@FXML
	TableView<CustomBroker> tblCustomBroker;

	@FXML
	TableColumn<CustomBroker, String> customBroker, contact, phoneNo, phoneExt, faxNo, email, webSite;

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
		customBroker = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(0);
		contact = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(1);
		phoneNo = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(2);
		phoneExt = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(3);
		faxNo = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(4);
		email = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(5);
		webSite = (TableColumn<CustomBroker, String>) tblCustomBroker.getColumns().get(6);

	}

	public void fetchCustomBrokers() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_CUSTOM_BROKER_API,
							null);
					CustomBroker c[] = mapper.readValue(response, CustomBroker[].class);
					List<CustomBroker> cbList = new ArrayList<CustomBroker>();
					for (CustomBroker customBroker : c) {
						cbList.add(customBroker);
					}
					ObservableList<CustomBroker> data = FXCollections.observableArrayList(cbList);

					setColumnValues();
					tblCustomBroker.setItems(data);

					tblCustomBroker.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void setColumnValues() {

		customBroker.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getCustomBroker() + "");
					}
				});
		contact.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getContactName() + "");
					}
				});
		phoneNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getPhoneNo() + "");
					}
				});
		phoneExt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getExtension() + "");
					}
				});
		faxNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getFaxNo() + "");
					}
				});
		email.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getEmailAddress() + "");
					}
				});
		webSite.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomBroker, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CustomBroker, String> param) {
						return new SimpleStringProperty(param.getValue().getWebSite() + "");
					}
				});
	}

}
