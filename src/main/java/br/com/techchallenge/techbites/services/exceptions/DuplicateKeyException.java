package br.com.techchallenge.techbites.services.exceptions;

public class DuplicateKeyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateKeyException(String resource, String field, String value) {
        super(String.format("Duplicate %s with %s = '%s'", resource, field, value));
    }

    public DuplicateKeyException(String message) {
        super(message);
    }
}