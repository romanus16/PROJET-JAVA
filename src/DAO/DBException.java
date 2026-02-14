/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */

public class DBException extends Exception {

    public DBException() {
        super();
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Exception e) {
        super(e);
    }

    public DBException(String message, Exception e) {
        super(message, e);
    }
}
