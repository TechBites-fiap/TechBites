package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.UserUseCase;
import br.com.techchallenge.techbites.infrastructure.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.infrastructure.docs.UserControllerDoc;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerDoc {

    private final UserUseCase useCase;
    private final UserDTOMapper mapper;

    public UserController(UserUseCase useCase , UserDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userRequest) {
        UserJpaEntity created = useCase.createUser(mapper.toJpaEntity(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(created));
    }

    @Override
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(Boolean active) {
        return ResponseEntity.ok().body(mapper.toListResponseDTO(useCase.getAllUsers(active)));
    }

    @Override
    public ResponseEntity<Optional<UserResponseDTO>> findUserById(Long id) {
        Optional<UserJpaEntity> found = useCase.findUserByID(id);
        return ResponseEntity.ok().body(mapper.toOpResponseDTO(found));
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(Long id, UserUpdateRequestDTO userRequest) {
        UserJpaEntity entity = mapper.toJpaEntity(userRequest);
        return ResponseEntity.ok().body(mapper.toResponseDTO(useCase.updateById(id , entity)));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        useCase.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> enableUserById(Long id) {
        useCase.enableUserById(id);
        return ResponseEntity.noContent().build() ;
    }


}
