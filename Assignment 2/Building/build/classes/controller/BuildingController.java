package controller;

import au.edu.uts.ap.javafx.*;
import java.io.IOException;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import model.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The controller for building.fxml.
 */
public class BuildingController extends Controller<Building> {
    @FXML private Slider delaySl;
    @FXML private TableView<Lift> liftsTv;
    @FXML private Button viewBtn;
    @FXML private TableColumn<Lift, String> liftClm;
    @FXML private TableColumn<Lift, String> levelClm;
    @FXML private TableColumn<Lift, String> directionClm;
    @FXML private TableColumn<Lift, String> passengersClm;
    @FXML private TableColumn<Lift, String> waitingClm;

    public final Building getBuilding() { return model; }

    @FXML private void initialize() {
        levelClm.getStyleClass().add("monospaced");
        getDelay().bindBidirectional(getBuilding().delayProperty());
        viewBtn.disableProperty().bind(liftsTv.getSelectionModel().selectedItemProperty().isNull());
        liftClm.setCellValueFactory(cellData -> {
        IntegerProperty number = new SimpleIntegerProperty(cellData.getValue().getNumber());
        StringProperty stringNumber = new SimpleStringProperty();
        stringNumber.bind(Bindings.concat("Lift ").concat(number));
        return stringNumber;
        });
        levelClm.setCellValueFactory(cellData -> {
        Lift lift = cellData.getValue();
        return Bindings.format(toLiftString(lift), lift.levelProperty());
        });
        directionClm.setCellValueFactory(cellData -> {
        Lift lift = cellData.getValue();
        return Bindings.format(getRawStringDirection(lift), lift.directionProperty());
        });
        passengersClm.setCellValueFactory(cellData -> {
        IntegerBinding passengersSizeProperty = Bindings.size(cellData.getValue().getPassengers());
        return passengersSizeProperty.asString();
        });
        waitingClm.setCellValueFactory(cellData -> {
        IntegerBinding queueSizeProperty = Bindings.size(cellData.getValue().getQueue());
        return queueSizeProperty.asString();
        });
        // Start up the building. Don't forget to also shutdown the building
        // when the user clicks the "Exit" button.
        getBuilding().startup();
    }
    
    private Lift getSelectedLift() {
        return liftsTv.getSelectionModel().getSelectedItem();
    }
    
   @FXML public void handleCall(ActionEvent event) throws IOException {
       ViewLoader.showStage(getBuilding(), "/view/call_lift.fxml", "Call lift", new Stage());
   }
   
   @FXML public void handleView(ActionEvent event) throws IOException {
       Lift selectedLift = getSelectedLift();
       ViewLoader.showStage(selectedLift, "/view/lift.fxml", "Lift", new Stage());
       
    }
   
   @FXML public void handlePeople(ActionEvent event) throws IOException {
       ViewLoader.showStage(getBuilding(), "/view/people.fxml", "People", new Stage());
   }
   
   @FXML public void handleExit(ActionEvent event) {
       getBuilding().shutdown();
       stage.close();
   }

    /**
     * This accessor method returns the selected number on the delay slider.
     *
     * @return the the selected delay
     */
    private DoubleProperty getDelay() {
        return delaySl.valueProperty();
    }
    
    private String toLiftString(Lift lift) {
        String stringlift = "";
        for (int j = 0; j < lift.getBottom(); j++) {
        stringlift += " ";
    }
    stringlift += "|";
    for (int i = lift.getBottom(); i < lift.getTop() + 1; i++) {
        if (i == lift.getLevel())
        stringlift += "" + lift.getLevel();
        else
        stringlift += " ";
    }
    stringlift += "|";
    return stringlift;
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
