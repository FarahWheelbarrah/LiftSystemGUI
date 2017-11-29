/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import au.edu.uts.ap.javafx.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import model.*;

/**
 *
 * @author andre
 */
public class PeopleController extends Controller<Building> {
    
    @FXML private TableView<Person> peopleTv;
    @FXML private TableColumn<Person, String> idClm;
    @FXML private TableColumn<Person, String> nameClm;
    @FXML private TableColumn<Person, String> levelClm;
    @FXML private TableColumn<Person, String> destinationClm;
    @FXML private TableColumn<Person, String> aboardClm;
    
    public Building getBuilding() { return model; }
    
    @FXML private void initialize() {
        idClm.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getId()).asString());
        nameClm.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getName()));
        levelClm.setCellValueFactory(cellData -> {
            ReadOnlyIntegerProperty level = cellData.getValue().levelProperty();
            StringProperty stringLevel = new SimpleStringProperty();
            stringLevel.bind(Bindings.concat("Level ").concat(level));
            return stringLevel;
        });
        destinationClm.setCellValueFactory(cellData -> {
            IntegerProperty destination = cellData.getValue().destinationProperty();
            StringProperty stringDestination = new SimpleStringProperty();
            stringDestination.bind(Bindings.concat("Level ").concat(destination));
            return stringDestination;
        });
        aboardClm.setCellValueFactory(cellData -> {
        Person person = cellData.getValue();
        return Bindings.format(yesOrNo(person), person.aboardProperty());
        });
        
        
    }
    
    @FXML public void handleClose(ActionEvent event) {
        stage.close();
    }
    
    private String yesOrNo(Person person) {
        if (person.isAboard())
            return "Yes";
        else 
            return"No";
    }
}

// the return statement must be explicity specified when writing a multi-
// line lambda expression (i.e. when using curly braces {})