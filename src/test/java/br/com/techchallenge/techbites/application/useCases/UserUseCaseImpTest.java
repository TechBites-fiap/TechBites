package br.com.techchallenge.techbites.application.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.domain.models.User;
import br.com.techchallenge.techbites.infrastructure.gateways.UserEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.application.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.techchallenge.techbites.application.exceptions.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImpTest {

    @Mock
    UserGateway repository;

    @Mock
    UserEntityMapper mapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserUseCaseImp useCase;

    private final Long userId = 42L;
    private final String userName = "João Silva";
    private final String userEmail = "joao.silva@email.com";
    private final String userRole = Role.USER.toString();
    private final String userPassword = "123456";
    private final String encodedPassword = "encoded_123456";

    @Test
    @DisplayName("GIVEN user exists and active WHEN findUserByID THEN return user")
    void testFindUserByID_UserActive() {
        UserJpaEntity entity = new UserJpaEntity();
        User domain = mock(User.class);

        when(repository.findUserById(userId)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(domain.isInactive()).thenReturn(false);

        Optional<UserJpaEntity> result = useCase.findUserByID(userId);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    @DisplayName("GIVEN user exists but inactive WHEN findUserByID THEN throw UserNotFoundException")
    void testFindUserByID_UserInactive() {
        UserJpaEntity entity = new UserJpaEntity();
        User domain = mock(User.class);

        when(repository.findUserById(userId)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(domain.isInactive()).thenReturn(true);

        assertThrows(UserNotFoundException.class, () -> useCase.findUserByID(userId));
    }

    @Test
    @DisplayName("GIVEN valid user WHEN createUser THEN save user with encoded password")
    void testCreateUser_Success() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail(userEmail);
        entity.setPassword(userPassword);

        User domain = mock(User.class);
        UserJpaEntity savedEntity = new UserJpaEntity();

        when(repository.findByEmail(userEmail)).thenReturn(Optional.empty());
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(passwordEncoder.encode(userPassword)).thenReturn(encodedPassword);
        when(mapper.toEntity(domain)).thenReturn(savedEntity);
        when(repository.createUser(savedEntity)).thenReturn(savedEntity);

        UserJpaEntity result = useCase.createUser(entity);

        verify(domain).setCreatedAt(any(LocalDateTime.class));
        verify(domain).setLastUpdatedAt(any(LocalDateTime.class));
        verify(domain).setPassword(encodedPassword);

        assertEquals(savedEntity, result);
    }

    @Test
    @DisplayName("GIVEN email already exists WHEN createUser THEN throw DuplicateKeyException")
    void testCreateUser_DuplicateEmail() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail(userEmail);

        when(repository.findByEmail(userEmail)).thenReturn(Optional.of(new UserJpaEntity()));

        assertThrows(DuplicateKeyException.class, () -> useCase.createUser(entity));
    }

    @Test
    @DisplayName("GIVEN valid update WHEN updateById THEN update user")
    void testUpdateById_Success() {
        UserJpaEntity foundEntity = new UserJpaEntity();
        foundEntity.setId(userId);

        UserJpaEntity updateEntity = new UserJpaEntity();
        updateEntity.setEmail(userEmail);
        updateEntity.setName(userName);
        updateEntity.setRole(Role.USER);

        User domainFound = mock(User.class);
        UserJpaEntity updatedEntity = new UserJpaEntity();

        when(repository.findUserById(userId)).thenReturn(Optional.of(foundEntity));
        when(mapper.toDomain(foundEntity)).thenReturn(domainFound);
        when(domainFound.isInactive()).thenReturn(false);
        when(repository.findByEmail(userEmail)).thenReturn(Optional.empty());
        when(mapper.toEntity(domainFound)).thenReturn(updatedEntity);
        when(repository.updateUser(updatedEntity)).thenReturn(updatedEntity);

        UserJpaEntity result = useCase.updateById(userId, updateEntity);

        verify(domainFound).setName(userName);
        verify(domainFound).setEmail(userEmail);
        verify(domainFound).setRole(Role.USER);
        verify(domainFound).setLastUpdatedAt(any(LocalDateTime.class));
        assertEquals(updatedEntity, result);
    }

    @Test
    @DisplayName("GIVEN update with duplicate email WHEN updateById THEN throw DuplicateKeyException")
    void testUpdateById_DuplicateEmail() {
        UserJpaEntity foundEntity = new UserJpaEntity();
        foundEntity.setId(userId);

        UserJpaEntity updateEntity = new UserJpaEntity();
        updateEntity.setEmail(userEmail);

        User domainFound = mock(User.class);

        UserJpaEntity otherUser = new UserJpaEntity();
        otherUser.setId(99L); // outro id, pra causar conflito no email

        when(repository.findUserById(userId)).thenReturn(Optional.of(foundEntity));
        when(mapper.toDomain(foundEntity)).thenReturn(domainFound);
        when(domainFound.isInactive()).thenReturn(false);
        when(repository.findByEmail(userEmail)).thenReturn(Optional.of(otherUser));

        assertThrows(DuplicateKeyException.class, () -> useCase.updateById(userId, updateEntity));
    }

    @Test
    @DisplayName("GIVEN user not found WHEN updateById THEN throw UserNotFoundException")
    void testUpdateById_UserNotFound() {
        UserJpaEntity updateEntity = new UserJpaEntity();
        when(repository.findUserById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> useCase.updateById(userId, updateEntity));
    }

    @Test
    @DisplayName("GIVEN inactive user WHEN updateById THEN throw UserNotFoundException")
    void testUpdateById_UserInactive() {
        UserJpaEntity foundEntity = new UserJpaEntity();
        User domainFound = mock(User.class);

        when(repository.findUserById(userId)).thenReturn(Optional.of(foundEntity));
        when(mapper.toDomain(foundEntity)).thenReturn(domainFound);
        when(domainFound.isInactive()).thenReturn(true);

        UserJpaEntity updateEntity = new UserJpaEntity();

        assertThrows(UserNotFoundException.class, () -> useCase.updateById(userId, updateEntity));
    }

    @Test
    @DisplayName("GIVEN active flag WHEN getAllUsers THEN return user list")
    void testGetAllUsers() {
        List<UserJpaEntity> users = Arrays.asList(new UserJpaEntity(), new UserJpaEntity());

        when(repository.getAllUsers(true)).thenReturn(users);

        List<UserJpaEntity> result = useCase.getAllUsers(true);

        assertEquals(users.size(), result.size());
    }

    @Test
    @DisplayName("GIVEN user id WHEN deleteUserById THEN call repository delete")
    void testDeleteUserById() {
        doNothing().when(repository).deleteTypeById(userId);

        useCase.deleteUserById(userId);

        verify(repository, times(1)).deleteTypeById(userId);
    }

    @Test
    @DisplayName("GIVEN user id WHEN enableUserById THEN call repository enable")
    void testEnableUserById() {
        doNothing().when(repository).enableUserById(userId);

        useCase.enableUserById(userId);

        verify(repository, times(1)).enableUserById(userId);
    }
}

