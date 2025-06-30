package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.controllers.docs.UserControllerDoc;
import br.com.techchallenge.techbites.dtos.*;
import br.com.techchallenge.techbites.services.UserService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerDoc {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @Override
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userRequest));
    }

    @Override
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(service.findAllUsers(active));
    }


    @Override
    public ResponseEntity<Optional<UserResponseDTO>> findUserById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findUserById(id));
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO userUpdateRequest) {

        return ResponseEntity.ok(service.updateUserById(id, userUpdateRequest));
    }

    @Override
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> enableUserById(
            @PathVariable Long id) {
        service.enableUserById(id);
        return ResponseEntity.noContent().build();
    }

}