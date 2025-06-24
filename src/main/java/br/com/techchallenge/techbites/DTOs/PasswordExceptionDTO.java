package br.com.techchallenge.techbites.DTOs;

public record PasswordExceptionDTO(String error , String message, int status, String method, String path) {
}
