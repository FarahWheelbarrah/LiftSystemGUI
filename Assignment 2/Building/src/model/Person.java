package model;

import javafx.beans.property.*;

/**
 * A person boards and alights lifts.
 */
public class Person {
    private final int id;
    private final String name;
    private IntegerProperty level = new SimpleIntegerProperty();
    private IntegerProperty destination = new SimpleIntegerProperty();
    private BooleanProperty aboard = new SimpleBooleanProperty();

    public Person(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level.set(level);
        this.destination.set(level);
        
    }

    // PROPERTIES

    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final int getLevel() {
        return level.get();
    }
    
    private final void setLevel(int level) {
        this.level.set(level);
    }
    
    public ReadOnlyIntegerProperty levelProperty() {
        return level;
    }

    public final int getDestination() {
        return destination.get();
    }
    
    public final void setDestination(int destination) {
        this.destination.set(destination);
    }
    
    public IntegerProperty destinationProperty() {
        return destination;
    }

    public final boolean isAboard() {
        return aboard.get();
    }
    
    private final void setAboard(boolean aboard) {
        this.aboard.set(aboard);
    }
    
    public ReadOnlyBooleanProperty aboardProperty() {
        return aboard;
    }

    // FUNCTIONS and PROCEDURES

    public void call(int destination) {
        setDestination(destination);
    }

    public void move(int direction) {
        setLevel(getLevel() + direction);
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    public boolean canBoard(int liftLevel, int liftDirection) {
        return isAt(liftLevel) && isHeadingIn(liftDirection);
    }

    public boolean isAt(int level) {
        return getLevel() == level;
    }

    public boolean isHeadingIn(int direction) {
        return direction == direction();
    }

    public int direction() {
        return Direction.fromTo(getLevel(), getDestination());
    }

    public boolean isIdle() {
        return !isAboard() && getLevel() == getDestination();
    }

    public boolean isWaiting() {
        return !isAboard() && getLevel() != getDestination();
    }

    public boolean hasArrived() {
        return getLevel() == getDestination();
    }

    /**
     * Determine the direction that this person wants the lift to go in.
     */
    public int liftDirection(int liftLevel) {
        return Direction.fromTo(liftLevel, getLevel() == liftLevel ? getDestination() : getLevel());
    }

    public void board() {
        setAboard(true);
       
    }

    public void alight() {
        setAboard(false);
        
    }

    @Override
    public String toString() {
        return name;
    }
}
