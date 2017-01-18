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
import com.dpu.model.Company;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Success;

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

public class DivisionController extends Application implements Initializable {

	@FXML
	TableView<Division> tblDivision;

	public List<Division> divisionList = null;

	@FXML
	TableColumn<Division, String> code, name, fedralaccount, provincialaccount;

	@FXML
	private void btnAddDivisionAction() {
		openAddDivisionScreen();
	}

	@FXML
	private void btnEditDivisionAction() {
		Division division = tblDivision.getSelectionModel().getSelectedItem();
		if (division != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + division.getDivisionId(),
								null);
						System.out.println("resp " + response);
						if (response != null && response.length() > 0) {

							System.out.println("1111111111111");
							Division division = mapper.readValue(response, Division.class);
							System.out.println("2222222222   " + division.getDivisionId());
							DivisionEditController divisionEditController = (DivisionEditController) openEditDivisionScreen();
							System.out.println("333333333");
							divisionEditController.initData(division);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
					}
				}
			});
		}
	}

	@FXML
	private void btnDeleteDivisionAction() {
		Division division = tblDivision.getSelectionModel().getSelectedItem();
		if (division != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(
								Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API + "/" + division.getDivisionId(),
								null);
						if (response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage(), "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}
						fetchDivisions();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
					}
				}
			});
		}
	}

	private void openAddDivisionScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.DIVISION_BASE_PACKAGE + Iconstants.XML_DIVISION_ADD_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Add New Division");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Object openEditDivisionScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
					.getResource(Iconstants.DIVISION_BASE_PACKAGE + Iconstants.XML_DIVISION_EDIT_SCREEN));

			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit Division");
			stage.setScene(new Scene(root));
			stage.show();
			return fxmlLoader.getController();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchDivisions();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		code = (TableColumn<Division, String>) tblDivision.getColumns().get(0);
		name = (TableColumn<Division, String>) tblDivision.getColumns().get(1);
		fedralaccount = (TableColumn<Division, String>) tblDivision.getColumns().get(2);
		provincialaccount = (TableColumn<Division, String>) tblDivision.getColumns().get(3);
	}

	public void fetchDivisions() {

		fetchColumns();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_DIVISION_API,
							null);
					if (response != null && response.length() > 0) {
						Division d[] = mapper.readValue(response, Division[].class);
						divisionList = new ArrayList<Division>();
						for (Division dl : d) {
							divisionList.add(dl);
						}
						ObservableList<Division> data = FXCollections.observableArrayList(divisionList);

						setColumnValues();
						tblDivision.setItems(data);

						tblDivision.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}

	private void setColumnValues() {

		code.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getDivisionCode() + "");
					}
				});
		name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getDivisionName() + "");
					}
				});

		fedralaccount.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getFedral() + "");
					}
				});
		provincialaccount.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Division, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Division, String> param) {
						return new SimpleStringProperty(param.getValue().getProvincial() + "");
					}
				});
	}
}