package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.infrastructure.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import jakarta.validation.Valid;

public interface AuthUseCase {
    boolean validateLogin(UserJpaEntity entity);

    void changePassword(@Valid ChangePasswordDTO changePasswordDTO);
}
