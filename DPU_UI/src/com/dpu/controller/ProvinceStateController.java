package com.dpu.controller;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProvinceStateController {
 
	private String title;
	private List<?> list;
	private TableView table = new TableView();
	
	public ProvinceStateController(String title, List<?> list) {
		this.title = title;
		this.list = list;
	}
 
    
    public void openWindow() {
    	Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle(title);
        stage.setWidth(300);
        stage.setHeight(500);
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("Code");
        TableColumn lastNameCol = new TableColumn("Name");
        
        table.getColumns().addAll(firstNameCol, lastNameCol);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
}