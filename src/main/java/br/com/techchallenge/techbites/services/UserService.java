package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.mappers.UserMapper;
import br.com.techchallenge.techbites.repositories.UserRepository;
import br.com.techchallenge.techbites.services.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.mapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserRequestDTO userDto) {

        this.validateEmail(userDto.email());

        User entity = this.mapper.toEntity(userDto);
        String encryptedPassword = passwordEncoder.encode(userDto.password());
        entity.setPassword(encryptedPassword);
        entity.setActive(true);
        return this.mapper.toDTO(repository.save(entity));
    }

    public List<UserResponseDTO> findAllUsers(Boolean active) {
        List<User> users;

        if (active == null) {
            users = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } else {
            users = repository.findByActive(active, Sort.by(Sort.Direction.ASC, "id"));
        }

        return this.mapper.toListDTO(users);
    }

    public Optional<UserResponseDTO> findUserById(Long id) {
        return Optional.of(repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("id" , id.toString())))
                .map(this.mapper::toDTO);
    }

    public UserResponseDTO updateUserById(Long id, UserUpdateRequestDTO updateRequest) { // <-- 1. Recebe o DTO correto
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", id.toString()));

        // A validação de e-mail duplicado continua a mesma, mas usando o novo DTO
        if (updateRequest.email() != null) {
            Optional<User> userByEmail = repository.findByEmail(updateRequest.email());
            if (userByEmail.isPresent() && !userByEmail.get().getId().equals(id)) {
                throw new DuplicateKeyException("User", "email", updateRequest.email());
            }
            existingUser.setEmail(updateRequest.email());
        }

        if (updateRequest.name() != null) {
            existingUser.setName(updateRequest.name());
        }

        if (updateRequest.role() != null) {
            existingUser.setRole(updateRequest.role());
        }

        User updatedUser = this.repository.save(existingUser);

        return this.mapper.toDTO(updatedUser);
    }

    public void deleteUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id" , id.toString()));
        repository.delete(user);
    }

    public void enableUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id" , id.toString()));
        user.setActive(true);
        repository.save(user);
    }

    private void validateEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new DuplicateKeyException("User" , "email" , email);
        }
    }


}
