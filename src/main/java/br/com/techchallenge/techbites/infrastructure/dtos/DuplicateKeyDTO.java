package br.com.techchallenge.techbites.infrastructure.dtos;

public record DuplicateKeyDTO(String message, int status, String method, String path) {
}
