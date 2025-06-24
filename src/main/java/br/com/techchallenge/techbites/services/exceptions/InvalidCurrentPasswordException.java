package br.com.techchallenge.techbites.services.exceptions;

public class InvalidCurrentPasswordException extends RuntimeException {
    public InvalidCurrentPasswordException() {
        super("The current password provided is incorrect.");
    }

}
