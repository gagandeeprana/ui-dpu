package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;

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

public class CompanyController extends Application implements Initializable {

	@FXML
	TableView<Company> tblCompany;
	
	public List<Company> cList = null;
	
	@FXML
	TableColumn<Company, String> unitNo, name, email, city, ps, phone, home, fax, afterHours;
	
	@FXML
	private void btnAddCompanyAction() {
		openAddCompanyScreen();
	}
	
	private void openAddCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_ADD_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Add New Company");
	        stage.setScene(new Scene(root)); 
	        stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchCompanies();
	}

	@Override
	public void start(Stage primaryStage) {
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<Company, String>) tblCompany.getColumns().get(0);
		name = (TableColumn<Company, String>) tblCompany.getColumns().get(1);
		email = (TableColumn<Company, String>) tblCompany.getColumns().get(2);
		city = (TableColumn<Company, String>) tblCompany.getColumns().get(3);
		ps = (TableColumn<Company, String>) tblCompany.getColumns().get(4);
		phone = (TableColumn<Company, String>) tblCompany.getColumns().get(5);
		home = (TableColumn<Company, String>) tblCompany.getColumns().get(6);
		fax = (TableColumn<Company, String>) tblCompany.getColumns().get(7);
		afterHours = (TableColumn<Company, String>) tblCompany.getColumns().get(8);
	}

	public void fetchCompanies() {
	
		fetchColumns();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null);
					if(response != null && response.length() > 0) {
						Company c[] = mapper.readValue(response, Company[].class);
						cList = new ArrayList<Company>();
						for(Company ccl : c) {
							cList.add(ccl);
						}
						ObservableList<Company> data = FXCollections.observableArrayList(cList);
						
						setColumnValues();
						tblCompany.setItems(data);
			
			            tblCompany.setVisible(true);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		unitNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getUnitNo() + "");
			}
		});
		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});
		email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getEmail() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		ps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getProvinceState() + "");
			}
		});
		phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getPhone() + "");
			}
		});
		home.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getCellular() + "");
			}
		});
		fax.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getFax() + "");
			}
		});
		afterHours.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Company, String> param) {
				return new SimpleStringProperty(param.getValue().getAfterHours() + "");
			}
		});
	}

}