package br.com.techchallenge.techbites.dtos;

import java.util.List;

public record UnauthorizedExceptionDTO(String message ,String error, int status, String method, String path) {
}
