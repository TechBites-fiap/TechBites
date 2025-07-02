package br.com.techchallenge.techbites.mappers;


import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.entities.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserMapper - Unit Tests")
public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("Deve converter UserRequestDTO para User entity com sucesso")
    void shouldConvertToUserEntitySuccessfully() {
        //GIVE
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "Teste User",
                "teste@example.com",
                "password123",
                Role.USER
        );

        //WHEN
        User user = userMapper.toEntity(userRequestDTO);

        //THEN

        assertNotNull(user);
        assertEquals(userRequestDTO.name(), user.getName());
        assertEquals(userRequestDTO.email(), user.getEmail());
        assertEquals(userRequestDTO.password(), user.getPassword());
        assertEquals(userRequestDTO.role(), user.getRole());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getLastUpdatedAt());
        assertTrue(user.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(user.getLastUpdatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));

    }

    @Test
    @DisplayName("Deve atualizar User entity com dados de UserRequestDTO com sucesso")
    void shouldUpdateUserEntitySuccessfully() {
        //GIVE
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("old");
        existingUser.setEmail("old@email.com");
        existingUser.setPassword("password123");
        existingUser.setRole(Role.USER);
        existingUser.setCreatedAt(LocalDateTime.now());
        existingUser.setLastUpdatedAt(LocalDateTime.now());
        existingUser.setCreatedAt(LocalDateTime.now().minusDays(1));
        existingUser.setLastUpdatedAt(LocalDateTime.now().minusHours(1));
        existingUser.setActive(true);

        UserRequestDTO newData = new UserRequestDTO(
                "New Name",
                "new@example.com",
                "newpassword",
                Role.ADMIN
        );

        //WHEN
        userMapper.updateEntity(existingUser, newData);

        //THEN

        assertEquals(existingUser.getName(), newData.name());
        assertEquals(existingUser.getEmail(), newData.email());
        assertEquals(existingUser.getPassword(), newData.password());
        assertEquals(existingUser.getRole(), newData.role());

        assertNotNull(existingUser.getCreatedAt());
        assertTrue(existingUser.getCreatedAt().isBefore(LocalDateTime.now().minusHours(23)));

        assertNotNull(existingUser.getLastUpdatedAt());
        assertTrue(existingUser.getLastUpdatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertNotEquals(LocalDateTime.now().minusHours(1), existingUser.getLastUpdatedAt());

    }


    @Test
    @DisplayName("Deve converter User entity para UserResponseDTO com sucesso")
    void shouldConvertToUserResponseDTOSuccessfully() {
        //GIVE
        User user = new User();
        user.setId(1L);
        user.setName("Teste User");
        user.setEmail("teste@example.com");
        user.setPassword("password123");
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now().minusDays(2));
        user.setLastUpdatedAt(LocalDateTime.now().minusHours(5));
        user.setActive(true);

        //WHEN
        UserResponseDTO responseDTO = userMapper.toDTO(user);

        //THEN
        assertNotNull(responseDTO);
        assertEquals(user.getName(), responseDTO.name());
        assertEquals(user.getEmail(), responseDTO.email());
        assertEquals(user.getRole(), responseDTO.role());
        assertNotNull(responseDTO.createdAt());
        assertNotNull(responseDTO.lastUpdatedAt());
        assertTrue(responseDTO.lastUpdatedAt().isBefore(LocalDateTime.now().minusHours(2)));
        assertNotNull(responseDTO.createdAt());
        assertNotNull(responseDTO.lastUpdatedAt());
    }

    @Test
    @DisplayName("Deve converter uma lista de User entities para uma lista de UserResponseDTOs com sucesso")
    void shouldConvertListOfUserEntitiesToListOfDTOsSuccessfully() {

        // GIVEN
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User One");
        user1.setEmail("one@example.com");
        user1.setRole(Role.USER);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setLastUpdatedAt(LocalDateTime.now());
        user1.setActive(true);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User Two");
        user2.setEmail("two@example.com");
        user2.setRole(Role.USER.ADMIN);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setLastUpdatedAt(LocalDateTime.now());
        user2.setActive(false);

        List<User> users = Arrays.asList(user1, user2);

        //WHEN
        List<UserResponseDTO> responseDTOs = userMapper.toListDTO(users);

        UserResponseDTO responseDTO1 = responseDTOs.get(0);
        assertNotNull(responseDTO1);
        assertEquals(user1.getName(), responseDTO1.name());
        assertEquals(user1.getEmail(), responseDTO1.email());
        assertEquals(user1.getRole(), responseDTO1.role());
        assertNotNull(responseDTO1.createdAt());
        assertNotNull(responseDTO1.lastUpdatedAt());

        UserResponseDTO responseDTO2 = responseDTOs.get(1);
        assertNotNull(responseDTO2);
        assertEquals(user2.getName(), responseDTO2.name());
        assertEquals(user2.getEmail(), responseDTO2.email());
        assertEquals(user2.getRole(), responseDTO2.role());
        assertNotNull(responseDTO2.createdAt());
        assertNotNull(responseDTO2.lastUpdatedAt());

    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando toListDTO recebe uma lista vazia")
    void shouldReturnEmptyListWhenToListDTORecievesEmptyList() {
        // GIVEN
        List<User> users = List.of();

        // WHEN
        List<UserResponseDTO> responseDTOs = userMapper.toListDTO(users);

        // THEN
        assertNotNull(responseDTOs);
        assertTrue(responseDTOs.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando toListDTO recebe null")
    void shouldReturnEmptyListWhenToListDTORecievesNull() {
        // GIVEN
        List<User> users = null; // null

        // WHEN
        List<UserResponseDTO> responseDTOs = userMapper.toListDTO(users);

        // THEN
        assertNotNull(responseDTOs);
        assertTrue(responseDTOs.isEmpty());
    }

}
