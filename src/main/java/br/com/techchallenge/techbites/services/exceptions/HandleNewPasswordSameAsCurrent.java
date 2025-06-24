package br.com.techchallenge.techbites.services.exceptions;

public class HandleNewPasswordSameAsCurrent extends RuntimeException {
    public HandleNewPasswordSameAsCurrent() {
        super("New password cannot be the same as the current password.");
    }
}
