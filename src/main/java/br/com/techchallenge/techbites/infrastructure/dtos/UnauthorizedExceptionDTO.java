package br.com.techchallenge.techbites.infrastructure.dtos;

import java.util.List;

public record UnauthorizedExceptionDTO(String message ,String error, int status, String method, String path) {
}
