package br.com.techchallenge.techbites.application.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String field, String value) {
        super("User", field, value);
    }
}
