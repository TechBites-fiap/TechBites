package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class AuthDTOMapper {

    public UserJpaEntity toEntity(LoginRequestDTO loginRequest) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail(loginRequest.email());
        entity.setPassword(loginRequest.password());
        return entity;
    }
}
