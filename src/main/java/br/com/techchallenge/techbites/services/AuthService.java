package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.entities.User;

import br.com.techchallenge.techbites.repositories.UserRepository;
import br.com.techchallenge.techbites.services.exceptions.HandleNewPasswordNotSameAsConfirmPassword;
import br.com.techchallenge.techbites.services.exceptions.HandleNewPasswordSameAsCurrent;
import br.com.techchallenge.techbites.services.exceptions.InvalidCurrentPasswordException;
import br.com.techchallenge.techbites.services.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User entity = this.repository.findByEmail(changePasswordDTO.email())
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
        this.repository.save(entity);

    }

    public boolean validateLogin(LoginRequestDTO loginRequest) {

        var userOptional = repository.findByEmail(loginRequest.email());

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        return passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }
}
