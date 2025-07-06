package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.entities.enums.Role;
import br.com.techchallenge.techbites.mappers.UserMapper;
import br.com.techchallenge.techbites.repositories.UserRepository;
import br.com.techchallenge.techbites.services.exceptions.DuplicateKeyException;
import br.com.techchallenge.techbites.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
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
    private UserUpdateRequestDTO userUpdateRequestDTO;
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

    @Test
    @DisplayName("Deve retornar todos os usuários ativos")
    void shouldFindAllActiveUsers() {
        //GIVEN
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setRole(Role.USER);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setLastUpdatedAt(LocalDateTime.now());
        user1.setActive(true);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 1");
        user2.setEmail("test1@example.com");
        user2.setPassword("password123");
        user2.setRole(Role.ADMIN);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setLastUpdatedAt(LocalDateTime.now());
        user2.setActive(true);

        List<User> activeUserEntities = List.of(user1, user2);
        List<UserResponseDTO> activeUserDTOs = List.of(
                new UserResponseDTO(user1.getId() , user1.getName() , user1.getEmail() , user1.getRole() , user1.getCreatedAt() , user1.getLastUpdatedAt() , user1.getActive()),
                new UserResponseDTO(user2.getId() , user2.getName() , user2.getEmail() , user2.getRole() , user2.getCreatedAt() , user2.getLastUpdatedAt() , user2.getActive())
        );

        when(repository.findByActive(eq(true), any(Sort.class))).thenReturn(activeUserEntities);
        when(mapper.toListDTO(activeUserEntities)).thenReturn(activeUserDTOs);

        //WHEN
        List<UserResponseDTO> result = userService.findAllUsers(true);

        //THEN
        assertNotNull(result);
        assertEquals(activeUserDTOs.size(), result.size());
        assertEquals(activeUserDTOs.get(0).id(), result.get(0).id());
        assertEquals(activeUserDTOs.get(1).id(), result.get(1).id());
        assertEquals(true, activeUserDTOs.get(0).active());
        assertEquals(true, activeUserDTOs.get(1).active());
        verify(repository, times(1)).findByActive(eq(true), any(Sort.class));
        verify(repository, never()).findAll(any(Sort.class));
        verify(mapper, times(1)).toListDTO(activeUserEntities);

    }

    @Test
    @DisplayName("Deve retornar todos os usuários inativos")
    void shouldFindAllInactiveUsers() {
        //GIVEN
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setRole(Role.USER);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setLastUpdatedAt(LocalDateTime.now());
        user1.setActive(false);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 1");
        user2.setEmail("test1@example.com");
        user2.setPassword("password123");
        user2.setRole(Role.ADMIN);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setLastUpdatedAt(LocalDateTime.now());
        user2.setActive(false);

        List<User> activeUserEntities = List.of(user1, user2);
        List<UserResponseDTO> activeUserDTOs = List.of(
                new UserResponseDTO(user1.getId() , user1.getName() , user1.getEmail() , user1.getRole() , user1.getCreatedAt() , user1.getLastUpdatedAt() , user1.getActive()),
                new UserResponseDTO(user2.getId() , user2.getName() , user2.getEmail() , user2.getRole() , user2.getCreatedAt() , user2.getLastUpdatedAt() , user2.getActive())
        );

        when(repository.findByActive(eq(false), any(Sort.class))).thenReturn(activeUserEntities);
        when(mapper.toListDTO(activeUserEntities)).thenReturn(activeUserDTOs);

        //WHEN
        List<UserResponseDTO> result = userService.findAllUsers(false);

        //THEN
        assertNotNull(result);
        assertEquals(activeUserDTOs.size(), result.size());
        assertEquals(activeUserDTOs.get(0).id(), result.get(0).id());
        assertEquals(activeUserDTOs.get(1).id(), result.get(1).id());
        assertEquals(false, activeUserDTOs.get(0).active());
        assertEquals(false, activeUserDTOs.get(1).active());
        verify(repository, times(1)).findByActive(eq(false), any(Sort.class));
        verify(repository, never()).findAll(any(Sort.class));
        verify(mapper, times(1)).toListDTO(activeUserEntities);

    }

    @Test
    @DisplayName("Deve retornar todos os usuários (ativos e inativos) quando 'active' for nulo")
    void shouldFindAllUsersWhenActiveIsNull() {
        //GIVEN
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setRole(Role.USER);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setLastUpdatedAt(LocalDateTime.now());
        user1.setActive(true);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 1");
        user2.setEmail("test1@example.com");
        user2.setPassword("password123");
        user2.setRole(Role.ADMIN);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setLastUpdatedAt(LocalDateTime.now());
        user2.setActive(false);

        List<User> allUserEntities = List.of(user1, user2);
        List<UserResponseDTO> allUserDTOs = List.of(
                new UserResponseDTO(user1.getId() , user1.getName() , user1.getEmail() , user1.getRole() , user1.getCreatedAt() , user1.getLastUpdatedAt() , user1.getActive()),
                new UserResponseDTO(user2.getId() , user2.getName() , user2.getEmail() , user2.getRole() , user2.getCreatedAt() , user2.getLastUpdatedAt() , user2.getActive())
        );

        when(repository.findAll(any(Sort.class))).thenReturn(allUserEntities);
        when(mapper.toListDTO(allUserEntities)).thenReturn(allUserDTOs);

        // WHEN
        List<UserResponseDTO> result = userService.findAllUsers(null);

        // THEN
        assertNotNull(result);
        assertEquals(allUserDTOs.size(), result.size());
        verify(repository, times(1)).findAll(any(Sort.class));
        verify(repository, never()).findByActive(anyBoolean(), any(Sort.class));
        verify(mapper, times(1)).toListDTO(allUserEntities);

    }

    @Test
    @DisplayName("Deve encontrar usuário por ID com sucesso")
    void shouldFindUserByIdSuccessfully() {
        // GIVEN
        when(repository.findById(eq(userId))).thenReturn(Optional.of(userEntity));
        when(mapper.toDTO(any(User.class))).thenReturn(userResponseDTO);

        // WHEN
        Optional<UserResponseDTO> result = userService.findUserById(userId);

        // THEN
        assertNotNull(result);
        assertEquals(userResponseDTO, result.get());
        assertEquals(userResponseDTO.id(), userEntity.getId());
        assertEquals(userResponseDTO.name(), result.get().name());
        assertEquals(userResponseDTO.email(), result.get().email());
        assertEquals(userResponseDTO.role(), result.get().role());
        assertEquals(userResponseDTO.createdAt(), result.get().createdAt());
        assertEquals(userResponseDTO.lastUpdatedAt(), result.get().lastUpdatedAt());

        verify(repository, times(1)).findById(eq(userId));
        verify(mapper, times(1)).toDTO(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException ao buscar usuário por ID inexistente")
    void shouldThrowExceptionWhenUserNotFound() {
        // GIVEN
        when(repository.findById(eq(userId))).thenReturn(Optional.empty());

        // WHEN AND THEN
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));

        verify(repository, times(1)).findById(eq(userId));
        verify(mapper, never()).toDTO(any(User.class));
    }

    @Test
    @DisplayName("Deve atualizar usuário por ID com sucesso")
    void shouldUpdateUserByIdSuccessfully() {
        // GIVEN
        when(repository.findById(eq(userId))).thenReturn(Optional.of(userEntity));

        when(repository.findByEmail(userUpdateRequestDTO.email()))
                .thenReturn(Optional.empty());

        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    return savedUser;
                });

        UserResponseDTO expectedUpdatedResponseDTO = new UserResponseDTO(
                userId,
                userUpdateRequestDTO.name(),
                userUpdateRequestDTO.email(),
                userUpdateRequestDTO.role(),
                userEntity.getCreatedAt(),
                LocalDateTime.now(),
                true
        );
        when(mapper.toDTO(any(User.class))).thenReturn(expectedUpdatedResponseDTO);

        // WHEN
        UserResponseDTO result = userService.updateUserById(userId, userUpdateRequestDTO);

        // THEN
        assertNotNull(result);

        assertEquals(expectedUpdatedResponseDTO.id(), result.id());
        assertEquals(expectedUpdatedResponseDTO.name(), result.name());
        assertEquals(expectedUpdatedResponseDTO.email(), result.email());
        assertEquals(expectedUpdatedResponseDTO.role(), result.role());

        assertTrue(result.lastUpdatedAt().isAfter(LocalDateTime.now().minusSeconds(5)));
        assertTrue(result.lastUpdatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(result.active());

        assertEquals(userUpdateRequestDTO.name(), userEntity.getName());
        assertEquals(userUpdateRequestDTO.email(), userEntity.getEmail());
        assertEquals(userUpdateRequestDTO.role(), userEntity.getRole());
        assertTrue(userEntity.getLastUpdatedAt().isAfter(userEntity.getCreatedAt()));

        verify(repository, times(1)).findById(eq(userId));
        verify(repository, times(1)).findByEmail(userUpdateRequestDTO.email());
        verify(repository, times(1)).save(userEntity);
        verify(mapper, times(1)).toDTO(userEntity);

    }

    @Test
    @DisplayName("Deve lançar DuplicateKeyException ao atualizar usuário com e-mail duplicado para outro usuário")
    void shouldThrowDuplicateKeyExceptionWhenUpdatingUserWithDuplicateEmail() {
        // GIVEN
        User otherUserWithSameEmail = new User();
        otherUserWithSameEmail.setId(2L); // ID diferente
        otherUserWithSameEmail.setEmail(userUpdateRequestDTO.email());

        when(repository.findById(eq(userId))).thenReturn(Optional.of(userEntity));
        when(repository.findByEmail(userUpdateRequestDTO.email())).thenReturn(Optional.of(otherUserWithSameEmail));

        // WHEN & THEN
        assertThrows(DuplicateKeyException.class, () -> userService.updateUserById(userId, userUpdateRequestDTO));

        verify(repository, times(1)).findById(eq(userId));
        verify(repository, times(1)).findByEmail(userUpdateRequestDTO.email());
        verify(repository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve deletar usuário por ID com sucesso")
    void shouldDeleteUserByIdSuccessfully() {
        // GIVEN
        when(repository.findById(eq(userId))).thenReturn(Optional.of(userEntity));
        doNothing().when(repository).delete(any(User.class));

        // WHEN
        userService.deleteUserById(userId);

        // THEN
        verify(repository, times(1)).findById(eq(userId));
        verify(repository, times(1)).delete(userEntity);
    }

}
