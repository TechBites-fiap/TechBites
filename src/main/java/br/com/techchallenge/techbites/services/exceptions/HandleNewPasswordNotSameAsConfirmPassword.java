package br.com.techchallenge.techbites.services.exceptions;

public class HandleNewPasswordNotSameAsConfirmPassword extends RuntimeException {
    public HandleNewPasswordNotSameAsConfirmPassword() {
        super("New password and confirmation do not match.");
    }
}
