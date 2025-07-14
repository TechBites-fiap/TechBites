package br.com.techchallenge.techbites.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s not found with %s = '%s'", resource, field, value));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}