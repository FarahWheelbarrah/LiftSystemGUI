package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class NoSuitableLiftException extends Exception {
    public NoSuitableLiftException() {
        super("No suitable lift found");
    }
}
