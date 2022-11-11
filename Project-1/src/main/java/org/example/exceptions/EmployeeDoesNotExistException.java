package org.example.exceptions;

public class EmployeeDoesNotExistException extends RuntimeException {

    public EmployeeDoesNotExistException() {
        super("The user does not exist");
    }
}
