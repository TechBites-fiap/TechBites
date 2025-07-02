package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setCreatedAt(LocalDateTime.now());
        user.setLastUpdatedAt(LocalDateTime.now());
        return user;
    }

    public void updateEntity(User existingUser, UserRequestDTO newData) {
        existingUser.setName(newData.name());
        existingUser.setEmail(newData.email());
        existingUser.setPassword(newData.password());
        existingUser.setRole(newData.role());
        existingUser.setLastUpdatedAt(LocalDateTime.now());
    }

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getLastUpdatedAt(),
                user.getActive()
        );
    }

    public List<UserResponseDTO> toListDTO(List<User> users) {
        if (Objects.isNull(users)) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(this::toDTO)
                .toList();
    }


}
