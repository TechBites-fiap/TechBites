package br.com.techchallenge.techbites.controllers;


import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.entities.Type;
import br.com.techchallenge.techbites.services.TypeService;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TypeController.class)
public class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TypeService service;

    @Autowired
    private ObjectMapper objectMapper;

    private TypeRequestDTO typeRequestDTO;
    private Type typeEntity;
    private TypeResponseDTO typeResponseDTO;
    private Long typeId;

    @BeforeEach
    void setUp() {
        typeId = 1L; // Usando Long para IDs
        typeRequestDTO = new TypeRequestDTO(
                "Entrada"
        );

        typeEntity = new Type();
        typeEntity.setId(typeId);
        typeEntity.setType("Entrada");
        typeEntity.setCreatedAt(LocalDateTime.now());
        typeEntity.setLastUpdatedAt(LocalDateTime.now());
        typeEntity.setActive(true);

        typeResponseDTO = new TypeResponseDTO(
                typeId,
                "Entrada",
                true
        );
    }


    @Test
    @DisplayName("POST /types - Deve criar um tipo")
    void shouldCreateTypeAndReturnCreatedStatus() throws Exception {

        // GIVEN
        when(service.createType(any(TypeRequestDTO.class))).thenReturn(typeEntity);

        // WHEN AND THEN
        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(typeRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.id").value(1L))
                .andExpect((ResultMatcher) jsonPath("$.type").value("Entrada"));

        verify(service, times(1)).createType(any(TypeRequestDTO.class));

    }

    @Test
    @DisplayName("POST /types - Deve retornar status 400 BAD REQUEST ao criar type com dados inválidos")
    void shouldReturnBadRequestWhenCreatingTypeWithInvalidData() throws Exception {
        // GIVEN
        TypeRequestDTO invalidRequestDTO = new TypeRequestDTO(
                ""
        );

        // WHEN AND THEN
        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(service, never()).createType(any(TypeRequestDTO.class));
    }

    @Test
    @DisplayName("GET /types?active=null - Deve retornar todos os types (ativos e inativos) quando 'active' for nulo")
    void shouldReturnListOfTypes() throws Exception {
        // GIVEN
        List<TypeResponseDTO> dtos = List.of(new TypeResponseDTO(1L, "Bebida", true));

        // WHEN AND THEN
        when(service.findAllType(null)).thenReturn(dtos);

        mockMvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("Bebida"));

        verify(service, times(1)).findAllType(null);
    }

    @Test
    @DisplayName("GET /types?active=true - Deve retornar todos os types (ativos e inativos) quando 'active' for true")
    void shouldReturnAllActiveTypes() throws Exception {
        // GIVEN
        List<TypeResponseDTO> dtos = List.of(new TypeResponseDTO(1L, "Bebida", true));

        // WHEN AND THEN
        when(service.findAllType(true)).thenReturn(dtos);

        mockMvc.perform(get("/types?active=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("Bebida"));

        verify(service, times(1)).findAllType(true);
    }

    @Test
    @DisplayName("GET /types?active=false - Deve retornar todos os types (ativos e inativos) quando 'active' for false")
    void shouldReturnAllInactiveTypes() throws Exception {
        // GIVEN
        List<TypeResponseDTO> dtos = List.of(new TypeResponseDTO(1L, "Bebida", false));

        // WHEN AND THEN
        when(service.findAllType(false)).thenReturn(dtos);

        mockMvc.perform(get("/types?active=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("Bebida"));

        verify(service, times(1)).findAllType(false);
    }

    @Test
    @DisplayName("GET /types/{id} - Deve retornar tipo por ID")
    void shouldReturnTypeById() throws Exception {
        TypeResponseDTO dto = new TypeResponseDTO(1L, "Entrada", true);

        when(service.findTypeById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Entrada"));
    }

    @Test
    @DisplayName("GET /types/{id} - Deve retornar status 404 NOT FOUND ao buscar type inexistente por ID")
    void shouldReturnNotFoundWhenFindingNonExistentTypeById() throws Exception {
        // GIVEN
        Long nonExistentId = 99L;
        when(service.findTypeById(eq(nonExistentId))).thenThrow(new ResourceNotFoundException("Type" , "id" , nonExistentId.toString()));

        // WHEN & THEN
        mockMvc.perform(get("/types/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findTypeById(eq(nonExistentId));
    }

    @Test
    @DisplayName("DELETE /types/{id} - Deve deletar um type por ID")
    void shouldRDeleteTypeById() throws Exception {
        // GIVEN
        doNothing().when(service).deleteTypeById(eq(1L));

        // WHEN & THEN
        mockMvc.perform(delete("/types/{id}", typeId))
                .andExpect(status().isNoContent()); // Espera status 204

        verify(service, times(1)).deleteTypeById(eq(typeId));
    }

    @Test
    @DisplayName("DELETE /types/{id} - Deve retornar status 404 NOT FOUND ao deletar type inexistente por ID")
    void shouldReturnNotFoundWheDeleteTypeById() throws Exception {
        // GIVEN
        Long nonExistentId = 99L;
        doThrow(new ResourceNotFoundException("Type" , "id" , nonExistentId.toString()))
                .when(service).deleteTypeById(eq(nonExistentId));

        // WHEN & THEN
        mockMvc.perform(delete("/types/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        verify(service, times(1)).deleteTypeById(eq(nonExistentId));
    }

    @Test
    @DisplayName("PATCH /types/enable/{id} Deve habilitar um type e retornar status 204 NO CONTENT")
    void shouldEnableTypeAndReturnNoContentStatus() throws Exception {
        // GIVEN
        doNothing().when(service).enableTypeById(eq(typeId));

        // WHEN & THEN
        mockMvc.perform(patch("/types/enable/{id}", typeId))
                .andExpect(status().isNoContent());
        verify(service, times(1)).enableTypeById(eq(typeId));
    }

    @Test
    @DisplayName("PATCH /types/enable/{id} - Deve retornar status 404 NOT FOUND ao ativar type inexistente por ID")
    void shouldReturnNotFoundWheEnableTypeById() throws Exception {
        // GIVEN
        Long nonExistentId = 99L;
        doThrow(new ResourceNotFoundException("Type" , "id" , nonExistentId.toString()))
                .when(service).enableTypeById(eq(nonExistentId));

        // WHEN & THEN
        mockMvc.perform(patch("/types/enable/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        verify(service, times(1)).enableTypeById(eq(nonExistentId));
    }

    @Test
    @DisplayName("PUT /types/{id} - Deve atualizar um tipo e retornar status 200 OK")
    void shouldUpdateTypeAndReturnOkStatus() throws Exception {
        // GIVEN
        TypeRequestDTO updatedRequestDTO = new TypeRequestDTO(
                "Entrada"
        );
        TypeResponseDTO updatedResponseDTO = new TypeResponseDTO(
                typeId,
                "Entada",
                true
        );

        when(service.updateTypeById(eq(typeId), any(TypeRequestDTO.class))).thenReturn(updatedResponseDTO);

        // WHEN & THEN
        mockMvc.perform(put("/types/{id}", typeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(typeId))
                .andExpect(jsonPath("$.type").value(updatedResponseDTO.type()));

        verify(service, times(1)).updateTypeById(eq(typeId), any(TypeRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /types/{id} - Deve retornar status 404 NOT FOUND ao atualizar type inexistente por ID")
    void shouldReturnNotFoundWheUpdateType() throws Exception {
        // GIVEN
        Long nonExistentId = 99L;
        TypeRequestDTO updatedRequestDTO = new TypeRequestDTO(
                "Entrada"
        );


        doThrow(new ResourceNotFoundException("Type" , "id" , nonExistentId.toString()))
                .when(service).updateTypeById(eq(nonExistentId), any(TypeRequestDTO.class));

        // WHEN & THEN
        mockMvc.perform(put("/types/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDTO)))
                .andExpect(status().isNotFound());


        verify(service, times(1)).updateTypeById(eq(nonExistentId), any(TypeRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /types/{id} - Deve retornar status 400 BAD REQUEST ao atualizar type com dados inválidos")
    void shouldReturnBadRequestWheUpdateType() throws Exception {
        // GIVEN
        Long nonExistentId = 99L;
        TypeRequestDTO invalidRequestDTO = new TypeRequestDTO(
                ""
        );

        doThrow(new ResourceNotFoundException("Type" , "id" , nonExistentId.toString()))
                .when(service).updateTypeById(eq(nonExistentId), any(TypeRequestDTO.class));

        // WHEN & THEN
        mockMvc.perform(put("/types/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestDTO)))
                .andExpect(status().isBadRequest());


        verify(service, never()).updateTypeById(eq(nonExistentId), any(TypeRequestDTO.class));
    }

}
