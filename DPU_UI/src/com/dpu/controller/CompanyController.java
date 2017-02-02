package com.dpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.GetAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.AdditionalContact;
import com.dpu.model.BillingControllerModel;
import com.dpu.request.CompanyModel;

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

	
	static CompanyAddController companyAddController;
	@FXML
	TableView<CompanyModel> tblCompany;
	
	public List<CompanyModel> cList = null;
	
	@FXML
	TableColumn<CompanyModel, String> unitNo, name, email, city, ps, phone, home, fax, afterHours;
	
	@FXML
	private void btnAddCompanyAction() {
		CompanyAddController.listOfBilling = new ArrayList<BillingControllerModel>();
		CompanyAddController.listOfAdditionalContact = new ArrayList<AdditionalContact>();
		CompanyAddController.company = new CompanyModel();
		openAddCompanyScreen();
		 
	}
	
	@FXML
	private void btnEditCompanyAction() {
		CompanyModel companyy = cList.get(tblCompany.getSelectionModel().getSelectedIndex());
		System.out.println(companyy);
		System.out.println("selected Index company Id : "+companyy.getCompanyId());
		
		CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();
//		System.out.println("selected company Id : "+company.getCompanyId());
		System.out.println(company + "   company:: ");
		if(company != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String response = GetAPIClient.callGetAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);
						
						if(response != null && response.length() > 0) {
							CompanyModel c = mapper.readValue(response, CompanyModel.class);
							
				 
							//----------------------------------------------
							
							int billingSize = c.getBillingLocations().size();
							for(int i=0 ;i<billingSize;i++){
						
								BillingControllerModel bcm = new BillingControllerModel();
								bcm.setAddress(c.getBillingLocations().get(i).getAddress());
								bcm.setCity(c.getBillingLocations().get(i).getCity());
								bcm.setCompany(c.getBillingLocations().get(i).getName());
								bcm.setContact(c.getBillingLocations().get(i).getContact());
								bcm.setFax(c.getBillingLocations().get(i).getFax());
								bcm.setPhone(c.getBillingLocations().get(i).getPhone());
								bcm.setZip(c.getBillingLocations().get(i).getZip());
								CompanyEditController.listOfBilling.add(bcm);
							}
							
							int addtionalContactSize = c.getAdditionalContacts().size();
							for(int j=0;j<addtionalContactSize;j++){
								AdditionalContact additionalContact = new AdditionalContact();
								additionalContact.setAdditionalContact(c.getAdditionalContacts().get(j).getCustomerName());
								additionalContact.setCellular(c.getAdditionalContacts().get(j).getCellular());
								additionalContact.setEmail(c.getAdditionalContacts().get(j).getEmail());
								additionalContact.setExtension(c.getAdditionalContacts().get(j).getExt());
								additionalContact.setFax(c.getAdditionalContacts().get(j).getFax());
								additionalContact.setPager(c.getAdditionalContacts().get(j).getCellular());
								additionalContact.setPhone(c.getAdditionalContacts().get(j).getPhone());
								additionalContact.setPosition(c.getAdditionalContacts().get(j).getPosition());
								additionalContact.setStatus(c.getAdditionalContacts().get(j).getStatus()+"");
								
								CompanyEditController.listOfAdditionalContact.add(additionalContact);
							}
							
							//-----------------------------------------------------
							CompanyEditController companyEditController = (CompanyEditController) openEditCompanyScreen();
							companyEditController.initData(c);
						} 
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." + e , "Info", 1);
					}
				}
			});
		}
	}
	
	@FXML
	private void btnDeleteCompanyAction() {
		CompanyModel company = tblCompany.getSelectionModel().getSelectedItem();
		if(company != null) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					try {
						/*ObjectMapper mapper = new ObjectMapper();
						String response = DeleteAPIClient.callDeleteAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API + "/" + company.getCompanyId(), null);
						if(response != null && response.contains("message")) {
							Success success = mapper.readValue(response, Success.class);
							JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
						} else {
							Failed failed = mapper.readValue(response, Failed.class);
							JOptionPane.showMessageDialog(null, failed.getMessage(), "Info", 1);
						}*/
						fetchCompanies();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Try Again.." , "Info", 1);
					}
				}
			});
		}
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
			e.printStackTrace();
		}
	}
	
	private Object openEditCompanyScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Iconstants.COMPANY_BASE_PACKAGE + Iconstants.XML_COMPANY_EDIT_SCREEN));
			
	        Parent root = (Parent) fxmlLoader.load();
	        
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Edit Company");
	        stage.setScene(new Scene(root)); 
	        stage.show();
	        return fxmlLoader.getController();
		} catch (Exception e) {
			e.printStackTrace();		}
		return null;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchCompanies();
	}

	 
	@Override
	public void start(Stage stage) {
		
	}

	public static void main(String[] args) {
		System.out.println("[Company Controller]: Enter main method start.");
		 launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void fetchColumns() {
		unitNo = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(0);
		name = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(1);
		email = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(2);
		city = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(3);
		ps = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(4);
		phone = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(5);
		home = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(6);
		fax = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(7);
		afterHours = (TableColumn<CompanyModel, String>) tblCompany.getColumns().get(8);
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
						CompanyModel c[] = mapper.readValue(response, CompanyModel[].class);
						cList = new ArrayList<CompanyModel>();
						for(CompanyModel ccl : c) {
							cList.add(ccl);
						}
						ObservableList<CompanyModel> data = FXCollections.observableArrayList(cList);
						
						setColumnValues();
						tblCompany.setItems(data);
			
			            tblCompany.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Try Again.." + e, "Info", 1);
				}
			}
		});
	}
	
	private void setColumnValues() {
		
		unitNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getUnitNo() + "");
			}
		});
		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getName() + "");
			}
		});
		email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getEmail() + "");
			}
		});
		city.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getCity() + "");
			}
		});
		ps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getProvinceState() + "");
			}
		});
		phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getPhone() + "");
			}
		});
		home.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getCellular() + "");
			}
		});
		fax.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getFax() + "");
			}
		});
		afterHours.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyModel,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CompanyModel, String> param) {
				return new SimpleStringProperty(param.getValue().getAfterHours() + "");
			}
		});
	}
}