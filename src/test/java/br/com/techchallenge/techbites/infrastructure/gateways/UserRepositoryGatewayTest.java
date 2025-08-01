package br.com.techchallenge.techbites.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserRepository;
import br.com.techchallenge.techbites.application.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class UserRepositoryGatewayTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserRepositoryGateway gateway;

    private final Long userId = 42L;
    private final String userEmail = "joao.silva@email.com";

    @Test
    @DisplayName("GIVEN user exists WHEN findUserById THEN return Optional with user")
    void testFindUserById_UserExists() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        when(repository.findById(userId)).thenReturn(Optional.of(entity));

        // WHEN
        Optional<UserJpaEntity> result = gateway.findUserById(userId);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    @DisplayName("GIVEN user not found WHEN findUserById THEN throw UserNotFoundException")
    void testFindUserById_UserNotFound() {
        // GIVEN
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(UserNotFoundException.class, () -> gateway.findUserById(userId));
    }

    @Test
    @DisplayName("GIVEN email exists WHEN findByEmail THEN return Optional with user")
    void testFindByEmail_UserExists() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        when(repository.findByEmail(userEmail)).thenReturn(Optional.of(entity));

        // WHEN
        Optional<UserJpaEntity> result = gateway.findByEmail(userEmail);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    @DisplayName("GIVEN email not found WHEN findByEmail THEN return empty Optional")
    void testFindByEmail_UserNotFound() {
        // GIVEN
        when(repository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // WHEN
        Optional<UserJpaEntity> result = gateway.findByEmail(userEmail);

        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("GIVEN user entity WHEN createUser THEN save and return entity")
    void testCreateUser() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        UserJpaEntity result = gateway.createUser(entity);

        // THEN
        assertEquals(entity, result);
        verify(repository, times(1)).save(entity);
    }

    @Test
    @DisplayName("GIVEN active flag null WHEN getAllUsers THEN return all users sorted")
    void testGetAllUsers_ActiveNull() {
        // GIVEN
        List<UserJpaEntity> entities = Arrays.asList(new UserJpaEntity(), new UserJpaEntity());
        when(repository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(entities);

        // WHEN
        List<UserJpaEntity> result = gateway.getAllUsers(null);

        // THEN
        assertEquals(entities.size(), result.size());
        verify(repository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    @DisplayName("GIVEN active flag true WHEN getAllUsers THEN return filtered users sorted")
    void testGetAllUsers_ActiveTrue() {
        // GIVEN
        List<UserJpaEntity> entities = Arrays.asList(new UserJpaEntity());
        when(repository.findByActive(true, Sort.by(Sort.Direction.ASC, "id"))).thenReturn(entities);

        // WHEN
        List<UserJpaEntity> result = gateway.getAllUsers(true);

        // THEN
        assertEquals(entities.size(), result.size());
        verify(repository, times(1)).findByActive(true, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    @DisplayName("GIVEN user exists WHEN deleteTypeById THEN delete user")
    void testDeleteTypeById_Success() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        when(repository.findById(userId)).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(entity);

        // WHEN
        gateway.deleteTypeById(userId);

        // THEN
        verify(repository, times(1)).delete(entity);
    }

    @Test
    @DisplayName("GIVEN user not found WHEN deleteTypeById THEN throw UserNotFoundException")
    void testDeleteTypeById_UserNotFound() {
        // GIVEN
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(UserNotFoundException.class, () -> gateway.deleteTypeById(userId));
    }

    @Test
    @DisplayName("GIVEN user exists WHEN enableUserById THEN set active true and save")
    void testEnableUserById_Success() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        when(repository.findById(userId)).thenReturn(Optional.of(entity));
        when(repository.save(any(UserJpaEntity.class))).thenReturn(entity);

        // WHEN
        gateway.enableUserById(userId);

        // THEN
        assertTrue(entity.getActive());
        verify(repository, times(1)).save(entity);
    }

    @Test
    @DisplayName("GIVEN user not found WHEN enableUserById THEN throw UserNotFoundException")
    void testEnableUserById_UserNotFound() {
        // GIVEN
        when(repository.findById(userId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(UserNotFoundException.class, () -> gateway.enableUserById(userId));
    }

    @Test
    @DisplayName("GIVEN user entity WHEN updateUser THEN save and return entity")
    void testUpdateUser() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        UserJpaEntity result = gateway.updateUser(entity);

        // THEN
        assertEquals(entity, result);
        verify(repository, times(1)).save(entity);
    }
}

