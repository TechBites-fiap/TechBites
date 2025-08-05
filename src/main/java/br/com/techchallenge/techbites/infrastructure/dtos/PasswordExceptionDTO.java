package br.com.techchallenge.techbites.infrastructure.dtos;

public record PasswordExceptionDTO(String error , String message, int status, String method, String path) {
}
