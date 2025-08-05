package br.com.techchallenge.techbites.infrastructure.dtos;

import java.util.List;

public record ValidationErrorDTO(List<String> erros, int status, String method , String path) {

}
