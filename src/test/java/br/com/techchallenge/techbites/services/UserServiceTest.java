package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.entities.enums.Role;
import br.com.techchallenge.techbites.mappers.UserMapper;
import br.com.techchallenge.techbites.repositories.UserRepository;
import br.com.techchallenge.techbites.services.exceptions.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Unit Tests")
public class UserServiceTest {


    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO; // Novo DTO para update
    private User userEntity;
    private UserResponseDTO userResponseDTO;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        userRequestDTO = new UserRequestDTO(
                "Test User",
                "test@example.com",
                "password123",
                Role.USER
        );
        userUpdateRequestDTO = new UserUpdateRequestDTO(
                "Updated Name",
                "updated@example.com",
                Role.ADMIN
        );

        userEntity = new User();
        userEntity.setId(userId);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("encryptedpassword");
        userEntity.setRole(Role.USER);
        userEntity.setCreatedAt(LocalDateTime.now().minusDays(1));
        userEntity.setLastUpdatedAt(LocalDateTime.now().minusHours(1));
        userEntity.setActive(true);

        userResponseDTO = new UserResponseDTO(
                userId,
                "Test User",
                "test@example.com",
                Role.ADMIN,
                userEntity.getCreatedAt(),
                userEntity.getLastUpdatedAt(),
                true
        );
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void shouldCreateUserSuccessfully() {

        //GIVEN
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(mapper.toEntity(any(UserRequestDTO.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(userRequestDTO.password())).thenReturn("encryptedpassword");
        when(repository.save(any(User.class))).thenReturn(userEntity);
        when(mapper.toDTO(any(User.class))).thenReturn(userResponseDTO);

        //WHEN
        UserResponseDTO result = userService.createUser(userRequestDTO);

        //THEN
        assertNotNull(result);
        assertEquals(userResponseDTO.name(), result.name());
        assertEquals(userResponseDTO.email(), result.email());
        assertEquals(userResponseDTO.role(), result.role());
        assertTrue(userResponseDTO.active());

        verify(repository, times(1)).findByEmail(userRequestDTO.email());
        verify(mapper, times(1)).toEntity(userRequestDTO);
        verify(passwordEncoder, times(1)).encode(userRequestDTO.password());
        verify(mapper, times(1)).toDTO(userEntity);
        verify(repository , times(1)).save(any(User.class));

    }

    @Test
    @DisplayName("Deve lançar DuplicateKeyException ao criar usuário com e-mail duplicado")
    void shouldThrowDuplicateKeyException() {
        //GIVEN
        when(repository.findByEmail(userRequestDTO.email())).thenReturn(Optional.of(userEntity));

        //WHEN AND THEN
        assertThrows(DuplicateKeyException.class, () -> userService.createUser(userRequestDTO));

        verify(repository, times(1)).findByEmail(userRequestDTO.email());
        verify(repository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(userRequestDTO.password());
        verify(mapper, never()).toEntity(userRequestDTO);
        verify(mapper, never()).toDTO(userEntity);

    }

}
