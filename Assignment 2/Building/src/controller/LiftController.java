/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import au.edu.uts.ap.javafx.*;
import javafx.beans.binding.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import model.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

/**
 *
 * @author andre
 */
public class LiftController extends Controller<Lift> {
    
    @FXML private Text directionTxt;
    public final Lift getLift() { return model; }
    
    @FXML private void initialize() {
        directionTxt.textProperty().bind(Bindings.createStringBinding(() ->
        getRawStringDirection(getLift()), getLift().directionProperty()));
    }
    
    @FXML public void handleClose(ActionEvent event) {
        stage.close();
    }
    
    private String getRawStringDirection(Lift lift) {
        switch(lift.getDirection()) {
            case Direction.STATIONARY: return "--";
            case Direction.DOWN: return "DOWN";
            case Direction.UP: return "UP";
            default: return "";
            
        }
    }
}
