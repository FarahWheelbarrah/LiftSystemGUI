/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import au.edu.uts.ap.javafx.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import model.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author andre
 */
public class CallLiftController extends Controller<Building> {

    @FXML private TextField destinationTf;
    @FXML private Text errorTxt;
    @FXML private ComboBox<Person> peopleCmb;

    public final Building getBuilding() {
        return model;
    }

    private Person getSelectedPerson() throws NullPointerException {
        Person selectedPerson = peopleCmb.getSelectionModel().getSelectedItem();
        if (selectedPerson == null) {
            throw new NullPointerException("You must select a caller");
        }
        return selectedPerson;
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        stage.close();
    }

    @FXML
    public void handleCall(ActionEvent event) {
        try {
            Person selectedPerson = getSelectedPerson();
            getBuilding().call(selectedPerson, getDestination());
            stage.close();
            
        } 
        catch (NullPointerException e) {
            errorTxt.setText(e.getMessage());
        }
        catch (NumberFormatException e) {
            errorTxt.setText(e.getMessage());
        }
        catch (NoSuitableLiftException e) {
            errorTxt.setText(e.getMessage());
        }
       
    }

    private int getDestination() throws NumberFormatException {
        String destinationText = destinationTf.getText();
        if (!tryParseInt(destinationText))
            throw new NumberFormatException("Destination must be an integer");
        return Integer.parseInt(destinationText);
    }

    private void setDestination(String destination) {
        destinationTf.setText(destination);
    }
    
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
