package br.com.techchallenge.techbites.application.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String action, String resource, String email) {
        super(String.format("User '%s' is not authorized to %s resource '%s'.", email, action, resource));
    }

}

