package br.com.techchallenge.techbites.application.exceptions;

public class HandleNewPasswordNotSameAsConfirmPassword extends RuntimeException {
    public HandleNewPasswordNotSameAsConfirmPassword() {
        super("New password and confirmation do not match.");
    }
}
