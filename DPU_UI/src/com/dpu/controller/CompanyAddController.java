package com.dpu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PostAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Company;
import com.dpu.model.Failed;
import com.dpu.model.Success;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CompanyAddController extends Application implements Initializable{

	@FXML
	Button btnSaveCompany;
	
	@FXML
	TextField txtCompany, txtContact, txtAddress, txtPosition, txtUnitNo, txtPhone, txtExt, txtCity, txtFax, txtPrefix, 
	txtProvince, txtZip, txtAfterHours, txtEmail, txtTollFree, txtWebsite, txtCellular, txtPager;
	
	@FXML
	private void btnSaveCompanyAction() {
		addCompany();
		closeAddCompanyScreen(btnSaveCompany);
	}
	
	private void closeAddCompanyScreen(Button clickedButton) {
		Stage loginStage = (Stage) clickedButton.getScene().getWindow();
        loginStage.close();
	}
	
	@FXML
	TableView tableBillingLocations;
	
	@FXML public void handleMouseClick(MouseEvent arg0) {
	    System.out.println("clicked on " );
	 
         System.out.println("OK");
      
	 
		System.out.println("[CompanyController] : [start] : Enter Start method.");
		 
		 
       Label label = new Label();
 
       /* Circle circle = new Circle();
        circle.setRadius(80);
        circle.setFill(Color.AQUA);
 
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.setSpacing(5);
 
        root.getChildren().addAll(label, circle);*/
 
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
 
        MenuItem item1 = new MenuItem("ADD");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("EDIT");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 2");
            }
        });
        
        MenuItem item3 = new MenuItem("DELETE");
        item3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2, item3);
        int count = 0;
        if(count == 0){
        // When user right-click on Table
        tableBillingLocations.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
  
            @Override
            public void handle(ContextMenuEvent event) {
            	
                contextMenu.show(tableBillingLocations, event.getScreenX(), event.getScreenY());
                
            }
           
        });
        count =1;
        }
 
       // Scene scene = new Scene(root, 400, 200);
 
        /*stage.setTitle("JavaFX ContextMenu (o7planning.org)");
        stage.setScene(scene);
        stage.show();*/
		
	}
	
	private void addCompany() {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Company company = setCompanyValue();
					String payload = mapper.writeValueAsString(company);

					String response = PostAPIClient.callPostAPI(Iconstants.URL_SERVER + Iconstants.URL_COMPANY_API, null, payload);
					
					if(response != null && response.contains("message")) {
						Success success = mapper.readValue(response, Success.class);
						JOptionPane.showMessageDialog(null, success.getMessage() , "Info", 1);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage stage) {
		
	    }
	 
	     
	

	public static void main(String[] args) {
		launch(args);
	}
	
	private Company setCompanyValue() {
		Company company = new Company();
		company.setName(txtCompany.getText());
		company.setContact(txtContact.getText());
		company.setAddress(txtAddress.getText());
		company.setPosition(txtPosition.getText());
		company.setUnitNo(txtUnitNo.getText());
		company.setPhone(txtPhone.getText());
		company.setExt(txtExt.getText());
		company.setCity(txtCity.getText());
		company.setFax(txtFax.getText());
		company.setCompanyPrefix(txtPrefix.getText());
		company.setProvinceState(txtProvince.getText());
		company.setZip(txtZip.getText());
		company.setAfterHours(txtAfterHours.getText());
		company.setEmail(txtEmail.getText());
		company.setTollfree(txtTollFree.getText());
		company.setWebsite(txtWebsite.getText());
		company.setCellular(txtCellular.getText());
		company.setPager(txtPager.getText());
		return company;
	}
}