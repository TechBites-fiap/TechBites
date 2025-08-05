package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.infrastructure.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.application.exceptions.HandleNewPasswordNotSameAsConfirmPassword;
import br.com.techchallenge.techbites.application.exceptions.HandleNewPasswordSameAsCurrent;
import br.com.techchallenge.techbites.application.exceptions.InvalidCurrentPasswordException;
import br.com.techchallenge.techbites.application.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthUseCaseImp implements AuthUseCase  {

    private final UserGateway repository;
    private final PasswordEncoder passwordEncoder;

    public AuthUseCaseImp(UserGateway repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean validateLogin(UserJpaEntity entityLogin) {
        Optional<UserJpaEntity> found = repository.findByEmail(entityLogin.getEmail());
        return found.filter(entity -> passwordEncoder.matches(entityLogin.getPassword(), entity.getPassword())).isPresent();

    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        UserJpaEntity entity = this.repository.findByEmail(changePasswordDTO.email())
                .orElseThrow(() -> new UserNotFoundException("email" , changePasswordDTO.email()));

        if (!passwordEncoder.matches(changePasswordDTO.currentPassword(), entity.getPassword())) {
            throw new InvalidCurrentPasswordException();
        }

        if (passwordEncoder.matches(changePasswordDTO.newPassword(), entity.getPassword())) {
            throw new HandleNewPasswordSameAsCurrent();
        }

        if (!changePasswordDTO.newPassword().equals(changePasswordDTO.confirmNewPassword())) {
            throw new HandleNewPasswordNotSameAsConfirmPassword();
        }

        entity.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        entity.setLastUpdatedAt(LocalDateTime.now());
        this.repository.updateUser(entity);
    }
}
