package model;

import java.util.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A Lift carries people to their destinations.
 */
public class Lift {
    private final int number;
    private final int bottom;
    private final int top;
    private IntegerProperty level = new SimpleIntegerProperty();
    private IntegerProperty direction = new SimpleIntegerProperty();
    private ObservableList<Person> passengers = FXCollections.observableArrayList();
    private ObservableList<Person> queue = FXCollections.observableArrayList();

    public Lift(int number, int bottom, int top, int level) {
        this.number = number;
        this.bottom = bottom;
        this.top = top;
        this.level.set(level);
        direction.set(Direction.STATIONARY);
        
    }

    // PROPERTIES

    public final int getNumber() {
        return number;
    }

    public final int getBottom() {
        return bottom;
    }

    public final int getTop() {
        return top;
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

    public final int getDirection() {
        return direction.get();
    }
    
    private final void setDirection(int direction) {
        this.direction.set(direction);
    }
    
    public ReadOnlyIntegerProperty directionProperty() {
        return direction;
    }

    public ObservableList<Person> getPassengers() {
        return passengers;
    }

    public ObservableList<Person> getQueue() {
        return queue;
    }

    // FUNCTIONS and PROCEDURES

    public void call(Person person) {
        queue.add(person);
    }

    private void board(Person person) {
        queue.remove(person);
        passengers.add(person);
        person.board();
    }

    private void alight(Person person) {
        passengers.remove(person);
        person.alight();
    }

    /**
     * This procedure carries out the operation of a lift for one step of time.
     * It is intended to be called repeatedly.
     */
    public void operate() {
        // This is slightly different from Assignment 1
        Person nextPerson = nextPerson();
        if (getDirection() == Direction.STATIONARY) {
            if (nextPerson != null) {
                setDirection(nextPerson.liftDirection(getLevel()));
            }
        }
        if (alight() || board()) {
            if (passengers.isEmpty()) {
                setDirection(Direction.STATIONARY);
            }
        }
        else {
            move();
            if (isAtBoundary() || passengers.isEmpty() && anyoneWaitingHere()) {
                setDirection(Direction.STATIONARY);
            }
        }
    }

    private boolean anyoneWaitingHere() {
        if (queue.isEmpty())
            return false;
        Person person = queue.get(0);
        return person.isAt(getLevel());
    }

    private void move() {
        setLevel(getLevel() + getDirection());
        for (Person person : passengers)
            person.move(getDirection());
    }

    /**
     * Determine the next person to service.
     */
    private Person nextPerson() {
        // Take the next passenger if there is one
        if (passengers.size() > 0)
            return passengers.get(0);
        // Otherwise go to pick up the next waiting if there is one
        else if (queue.size() > 0)
            return queue.get(0);
        // Otherwise there is no next person
        else
            return null;
    }

    private boolean board() {
        int count = 0;
        for (Person person : new LinkedList<Person>(queue))
            if (person.canBoard(getLevel(), getDirection())) {
                board(person);
                count++;
            }
        return count > 0;
    }

    private boolean alight() {
        int count = 0;
        for (Person person : new ArrayList<Person>(passengers))
            if (person.hasArrived()) {
                alight(person);
                count++;
            }
        return count > 0;
    }

    private boolean isAtBoundary() {
        return getLevel() == bottom || getLevel() == top;
    }

    private int distanceTo(int target) {
        return Math.abs(target - getLevel());
    }

    public int suitability(int distance, int start, int destination) {
        if (!canTake(start, destination))
            return 0;
        else if (getDirection() * Direction.fromTo(getLevel(), start) < 0)
            return 1;
        else if (getDirection() == -Direction.fromTo(start, destination))
            return distance + 1 - distanceTo(start);
        else
            return distance + 2 - distanceTo(start);
    }

    private boolean canTake(int start, int destination) {
        return canReach(start) && canReach(destination);
    }

    private boolean canReach(int level) {
        return level >= bottom && level <= top;
    }

    @Override
    public String toString() {
        return "Lift " + number;
    }
    
    
}
