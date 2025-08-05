package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.infrastructure.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.application.exceptions.HandleNewPasswordNotSameAsConfirmPassword;
import br.com.techchallenge.techbites.application.exceptions.HandleNewPasswordSameAsCurrent;
import br.com.techchallenge.techbites.application.exceptions.InvalidCurrentPasswordException;
import br.com.techchallenge.techbites.application.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthUseCaseImpTest {

    @Mock
    private UserGateway repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthUseCaseImp useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("GIVEN valid login WHEN validateLogin THEN return true")
    void testValidateLogin_Success() {
        // GIVEN
        UserJpaEntity loginAttempt = new UserJpaEntity();
        loginAttempt.setEmail("example@email.com");
        loginAttempt.setPassword("senha123");

        UserJpaEntity storedUser = new UserJpaEntity();
        storedUser.setEmail("example@email.com");
        storedUser.setPassword("senhaCriptografada");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.of(storedUser));
        when(passwordEncoder.matches("senha123", "senhaCriptografada")).thenReturn(true);

        // WHEN
        boolean result = useCase.validateLogin(loginAttempt);

        // THEN
        assertTrue(result);
        verify(repository).findByEmail("example@email.com");
    }

    @Test
    @DisplayName("GIVEN invalid email WHEN validateLogin THEN return false")
    void testValidateLogin_EmailNotFound() {
        // GIVEN
        UserJpaEntity login = new UserJpaEntity();
        login.setEmail("example@email.com");
        login.setPassword("123");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.empty());

        // WHEN
        boolean result = useCase.validateLogin(login);

        // THEN
        assertFalse(result);
    }

    @Test
    @DisplayName("GIVEN invalid password WHEN validateLogin THEN return false")
    void testValidateLogin_WrongPassword() {
        // GIVEN
        UserJpaEntity login = new UserJpaEntity();
        login.setEmail("example@email.com");
        login.setPassword("errada");

        UserJpaEntity stored = new UserJpaEntity();
        stored.setEmail("example@email.com");
        stored.setPassword("senhaCerta");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.of(stored));
        when(passwordEncoder.matches("errada", "senhaCerta")).thenReturn(false);

        // WHEN
        boolean result = useCase.validateLogin(login);

        // THEN
        assertFalse(result);
    }

    @Test
    @DisplayName("GIVEN valid ChangePasswordDTO WHEN changePassword THEN update user")
    void testChangePassword_Success() {
        // GIVEN
        ChangePasswordDTO dto = new ChangePasswordDTO("example@email.com", "senhaAntiga", "novaSenha", "novaSenha");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("example@email.com");
        entity.setPassword("criptAntiga");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("senhaAntiga", "criptAntiga")).thenReturn(true);
        when(passwordEncoder.matches("novaSenha", "criptAntiga")).thenReturn(false);
        when(passwordEncoder.encode("novaSenha")).thenReturn("criptNova");

        // WHEN
        useCase.changePassword(dto);

        // THEN
        assertEquals("criptNova", entity.getPassword());
        assertNotNull(entity.getLastUpdatedAt());
        verify(repository).updateUser(entity);
    }

    @Test
    @DisplayName("GIVEN non-existent email WHEN changePassword THEN throw UserNotFoundException")
    void testChangePassword_UserNotFound() {
        // GIVEN
        ChangePasswordDTO dto = new ChangePasswordDTO("naoexiste@email.com", "123", "nova", "nova");
        when(repository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> useCase.changePassword(dto));
    }

    @Test
    @DisplayName("GIVEN wrong current password WHEN changePassword THEN throw InvalidCurrentPasswordException")
    void testChangePassword_WrongCurrentPassword() {
        // GIVEN
        ChangePasswordDTO dto = new ChangePasswordDTO("example@email.com", "errada", "nova", "nova");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("example@email.com");
        entity.setPassword("criptAntiga");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("errada", "criptAntiga")).thenReturn(false);

        // WHEN & THEN
        assertThrows(InvalidCurrentPasswordException.class, () -> useCase.changePassword(dto));
    }

    @Test
    @DisplayName("GIVEN new password equals current WHEN changePassword THEN throw HandleNewPasswordSameAsCurrent")
    void testChangePassword_NewEqualsCurrent() {
        // GIVEN
        ChangePasswordDTO dto = new ChangePasswordDTO("example@email.com", "senhaAntiga", "senhaAntiga", "senhaAntiga");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("example@email.com");
        entity.setPassword("criptAntiga");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("senhaAntiga", "criptAntiga")).thenReturn(true);
        when(passwordEncoder.matches("senhaAntiga", "criptAntiga")).thenReturn(true); // nova = antiga

        // WHEN & THEN
        assertThrows(HandleNewPasswordSameAsCurrent.class, () -> useCase.changePassword(dto));
    }

    @Test
    @DisplayName("GIVEN new password != confirm WHEN changePassword THEN throw HandleNewPasswordNotSameAsConfirmPassword")
    void testChangePassword_NewAndConfirmMismatch() {
        // GIVEN
        ChangePasswordDTO dto = new ChangePasswordDTO("example@email.com", "senhaAntiga", "novaSenha", "outraSenha");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("example@email.com");
        entity.setPassword("criptAntiga");

        when(repository.findByEmail("example@email.com")).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("senhaAntiga", "criptAntiga")).thenReturn(true);
        when(passwordEncoder.matches("novaSenha", "criptAntiga")).thenReturn(false);

        // WHEN & THEN
        assertThrows(HandleNewPasswordNotSameAsConfirmPassword.class, () -> useCase.changePassword(dto));
    }
}

