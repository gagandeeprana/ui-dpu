package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Terminal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TerminalPanelController extends Application implements Initializable {

	@FXML
	Button btnAdd;

	@FXML
	TableView<Terminal> tblTerminal;

	@FXML
	private TableColumn<Terminal, String> terminalName, facility, location;

	@FXML
	public void btnAddAction() {
		try {
			openAddScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			System.out.println("[[initialize]]: intialize method called");
			fetchColumns();
			Thread t = new Thread(new TerminalThread());
			System.out.println(t);
			t.start();

		} catch (Exception e) {
			System.out.println("[initialize] Exception : " + e);
			// TODO: handle exception
		}
	}

	private void openAddScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getClassLoader().getResource(Iconstants.BASE_PACKAGE + "AddTerminal.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			// AnchorPane vBox = (AnchorPane)root;
			// MenuBar mnuBar = (MenuBar)vBox.getChildren().get(0);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("MainScreen");
			stage.setScene(new Scene(root));
			// stage.setMaximized(true);
			// mnuBar.prefWidthProperty().bind(stage.widthProperty());
			// fxmlLoader.setController(MainScreen.class);
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			System.out.println("start");
			Parent parent = FXMLLoader
					.load(getClass().getClassLoader().getResource(Iconstants.BASE_PACKAGE + "ListTerminal.fxml"));
			System.out.println("XML : " + Iconstants.BASE_PACKAGE + "ListTerminal.fxml");
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("main method called");
		launch(args);
	}
	// new added

	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		try {
			System.out.println("[fetchColumns]: Enter ");
			terminalName = (TableColumn<Terminal, String>) tblTerminal.getColumns().get(0);
			facility = (TableColumn<Terminal, String>) tblTerminal.getColumns().get(1);
			location = (TableColumn<Terminal, String>) tblTerminal.getColumns().get(2);
			System.out.println("[fetchColumns]: Exit ");
		} catch (Exception e) {
			System.out.println("[fetchColumns]:Exception " + e);
		}
	}

	// Add One More class as Thread Class
	class TerminalThread implements Runnable {

		@Override
		public void run() {

			try {
				String response = GetAPIClient.callGetAPI("http://localhost:8080/DPUWebProject/terminal", null);
				System.out.println("response " + response);
				ObjectMapper mapper = new ObjectMapper();
				Terminal d[] = mapper.readValue(response, Terminal[].class);

				List<Terminal> cList = new ArrayList<Terminal>();
				for (Terminal ccl : d) {
					cList.add(ccl);
				}
				ObservableList<Terminal> data = FXCollections.observableArrayList(cList);

				setColumnValues();
				tblTerminal.setItems(data);

				tblTerminal.setVisible(true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void setColumnValues() {
			terminalName.setCellValueFactory(new PropertyValueFactory<Terminal, String>("terminalName"));
			facility.setCellValueFactory(new PropertyValueFactory<Terminal, String>("firstName"));
			location.setCellValueFactory(new PropertyValueFactory<Terminal, String>("lastName"));
		}

	}
}